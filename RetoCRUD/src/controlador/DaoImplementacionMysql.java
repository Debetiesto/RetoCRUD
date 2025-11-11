/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import static com.mysql.cj.conf.PropertyKey.logger;
import conexion.Conector;
import excepciones.UsuarioExisteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Administrador;
import modelo.Genero;
import modelo.Perfil;
import modelo.Usuario;

/**
 *
 * @author 2dam
 */
public class DaoImplementacionMysql implements Dao {

    private static final Logger logger = Logger.getLogger(DaoImplementacionMysql.class.getName());

    // Atributos para conexion
    private ResourceBundle configFile;
    private String urlBD, userBD, passwordBD;

    // Atributos
    private PreparedStatement stmt;
    // Sentencias SQL
    final String LOGIN = "SELECT * FROM PERFIL WHERE email = ? AND contra = ?";
    final String BUSCARADMIN = "SELECT * FROM ADMINISTRADOR WHERE CODU = ?";
    final String BUSCARCUENTA = "SELECT cuenta_corriente FROM ADMINISTRADOR WHERE CODU = ?";
    final String BUSCARUSUARIO = "SELECT num_tarjeta, genero FROM USUARIO WHERE CODU = ?";
    final String CARGARDATOSTABLA = "SELECT p.CODU, p.EMAIL, p.USERNAME, p.TELEFONO, p.CONTRA, p.NOMBRE, p.APELLIDOS, "
            + "u.GENERO, u.NUM_TARJETA "
            + "FROM PERFIL p JOIN USUARIO u ON p.CODU = u.CODU";
    final String MODIFICARDATOSUSUARIO = "UPDATE PERFIL p\n"
            + "JOIN USUARIO u ON p.CODU = u.CODU\n"
            + "SET p.EMAIL=?, p.USERNAME=?, p.TELEFONO=?, p.CONTRA=?, p.NOMBRE=?, p.APELLIDOS=?,\n"
            + "    u.GENERO=?, u.NUM_TARJETA=?\n"
            + "WHERE p.CODU=?";
    final String LISTARUSUARIOS = "SELECT p.CODU, p.EMAIL, p.USERNAME, p.TELEFONO, p.NOMBRE, p.APELLIDOS, "
            + "u.GENERO, u.NUM_TARJETA "
            + "FROM PERFIL p JOIN USUARIO u ON p.CODU = u.CODU WHERE p.CODU = ?";
    final String MODIFICARDATOSADMIN = "UPDATE PERFIL p "
            + "JOIN ADMINISTRADOR a ON p.CODU = a.CODU "
            + "SET p.EMAIL=?, p.USERNAME=?, p.TELEFONO=?, p.CONTRA=?, p.NOMBRE=?, p.APELLIDOS=?, "
            + "a.CUENTA_CORRIENTE=? "
            + "WHERE p.CODU=?";
    final String BORRARUSUARIO = "DELETE FROM PERFIL WHERE CODU = ?";

    final String ANIADIRPERFIL = "{CALL InsertarUsuarioCompleto2(?, ?, ?, ?, ?, ?, ?, ?)}";
    final String COMPROBARUSUARIO = "SELECT COUNT(*) FROM USUARIO u JOIN PERFIL p ON u.CODU = p.CODU WHERE p.EMAIL = ? OR p.USERNAME = ?";

    @Override
    public Perfil login(Perfil per) {
        ResultSet rs = null;
        Perfil perfil = null;

        try (Connection con = Conector.open()) {
            stmt = con.prepareStatement(LOGIN);
            stmt.setString(1, per.getEmail());
            stmt.setString(2, per.getContra());
            rs = stmt.executeQuery();

            if (rs.next()) {
                int codU = rs.getInt("CODU");

                if (esAdministrador(codU)) {
                    Administrador admin = new Administrador();
                    admin.setCodU(codU);
                    admin.setEmail(rs.getString("EMAIL"));
                    admin.setUser(rs.getString("USERNAME"));
                    admin.setTelefono(rs.getInt("TELEFONO"));
                    admin.setContra(rs.getString("CONTRA"));
                    admin.setNom(rs.getString("NOMBRE"));
                    admin.setApe(rs.getString("APELLIDOS"));

                    PreparedStatement stmtAdmin = con.prepareStatement(BUSCARCUENTA);
                    stmtAdmin.setInt(1, codU);
                    ResultSet rsAdmin = stmtAdmin.executeQuery();
                    if (rsAdmin.next()) {
                        admin.setCuentaCorriente(rsAdmin.getString("cuenta_corriente"));
                    }
                    rsAdmin.close();
                    stmtAdmin.close();

                    perfil = admin;

                } else {
                    Usuario usu = new Usuario();
                    usu.setCodU(codU);
                    usu.setEmail(rs.getString("EMAIL"));
                    usu.setUser(rs.getString("USERNAME"));
                    usu.setTelefono(rs.getInt("TELEFONO"));
                    usu.setContra(rs.getString("CONTRA"));
                    usu.setNom(rs.getString("NOMBRE"));
                    usu.setApe(rs.getString("APELLIDOS"));

                    PreparedStatement stmtUsu = con.prepareStatement(BUSCARUSUARIO);
                    stmtUsu.setInt(1, codU);
                    ResultSet rsUsu = stmtUsu.executeQuery();
                    if (rsUsu.next()) {
                        String gen = rsUsu.getString("GENERO");
                        if (gen != null && !gen.isEmpty()) {
                            usu.setGenero(Genero.valueOf(gen.toUpperCase()));
                        }
                        usu.setNumTarjeta(rsUsu.getInt("NUM_TARJETA"));
                    }
                    perfil = usu;
                }
            } else {
                logger.warning("Login fallido (SQL) para usuario: " + per.getEmail());
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error SQL durante login: {0}", e.getMessage());
        }

        return perfil;
    }

    @Override
    public List<Usuario> cargarDatosTabla() {
        List<Usuario> lista = new ArrayList<>();
        ResultSet rs = null;

        try (Connection con = Conector.open()) {
            PreparedStatement st = con.prepareStatement(CARGARDATOSTABLA);
            rs = st.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setCodU(rs.getInt("CODU"));
                u.setEmail(rs.getString("EMAIL"));
                u.setUser(rs.getString("USERNAME"));
                u.setTelefono(rs.getInt("TELEFONO"));
                u.setContra(rs.getString("CONTRA"));
                u.setNom(rs.getString("NOMBRE"));
                u.setApe(rs.getString("APELLIDOS"));
                u.setGenero(Genero.valueOf(rs.getString("GENERO").toUpperCase()));
                u.setNumTarjeta(rs.getInt("NUM_TARJETA"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Conector.holdConnection();
        return lista;
    }

    @Override
    public boolean esAdministrador(int codU) {
        ResultSet rs = null;
        boolean esAdmin = false;

        try (Connection con = Conector.open()) {
            stmt = con.prepareStatement(BUSCARADMIN);

            stmt.setInt(1, codU);
            rs = stmt.executeQuery();
            if (rs.next()) {
                esAdmin = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoImplementacionMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return esAdmin;
    }

    @Override
    public boolean updateUsuario(Usuario usu) {
        boolean actualizado = false;

        try (Connection con = Conector.open()) {
            stmt = con.prepareStatement(MODIFICARDATOSUSUARIO);

            stmt.setString(1, usu.getEmail());
            stmt.setString(2, usu.getUser());
            stmt.setInt(3, usu.getTelefono());
            stmt.setString(4, usu.getContra());
            stmt.setString(5, usu.getNom());
            stmt.setString(6, usu.getApe());
            stmt.setString(7, usu.getGenero().toString());
            stmt.setInt(8, usu.getNumTarjeta());
            stmt.setInt(9, usu.getCodU());

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                System.out.println("✅ Usuario modificado correctamente: " + usu.getEmail());
                actualizado = true;
            } else {
                System.out.println("⚠️ No se encontró el usuario con correo: " + usu.getEmail());
                actualizado = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoImplementacionMysql.class.getName()).log(Level.SEVERE, null, ex);
        }

        return actualizado;
    }

    @Override
    public List<Usuario> listaUsuarios(Usuario usu) {
        List<Usuario> usuarios = new ArrayList<>();
        ResultSet rs = null;

        try (Connection con = Conector.open()) {
            stmt = con.prepareStatement(LISTARUSUARIOS);

            stmt.setInt(1, usu.getCodU());
            rs = stmt.executeQuery();

            while (rs.next()) {
                usu.setEmail(rs.getString("EMAIL"));
                usu.setUser(rs.getString("USERNAME"));
                usu.setTelefono(rs.getInt("TELEFONO"));
                usu.setNom(rs.getString("NOMBRE"));
                usu.setApe(rs.getString("APELLIDOS"));

                PreparedStatement stmtUsuario = con.prepareStatement(BUSCARUSUARIO);
                stmtUsuario.setInt(1, usu.getCodU());
                ResultSet rsUsuario = stmtUsuario.executeQuery();
                if (rsUsuario.next()) {
                    String gen = rs.getString("GENERO");
                    if (gen != null && !gen.isEmpty()) {
                        usu.setGenero(Genero.valueOf(gen.toUpperCase()));
                    }
                }
                usu.setNumTarjeta(rs.getInt("NUM_TARJETA"));
                usuarios.add(usu);

            }

        } catch (SQLException ex) {
            Logger.getLogger(DaoImplementacionMysql.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usuarios;
    }

    @Override
    public boolean updateAdmin(Administrador admin) {
        boolean actualizado = false;
        PreparedStatement stmt;
        try (Connection con = Conector.open()) {
            stmt = con.prepareStatement(MODIFICARDATOSADMIN);

            stmt.setString(1, admin.getEmail());
            stmt.setString(2, admin.getUser());
            stmt.setInt(3, admin.getTelefono());
            stmt.setString(4, admin.getContra());
            stmt.setString(5, admin.getNom());
            stmt.setString(6, admin.getApe());
            stmt.setString(7, admin.getCuentaCorriente());
            stmt.setInt(8, admin.getCodU());

            actualizado = stmt.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(DaoImplementacionMysql.class.getName()).log(Level.SEVERE, null, ex);
        }

        return actualizado;
    }

    @Override
    public boolean borrarUsuario(int codU) {
        boolean eliminado = false;
        PreparedStatement stmt;

        try (Connection con = Conector.open()) {
            stmt = con.prepareStatement(BORRARUSUARIO);

            stmt.setInt(1, codU);
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("✅ Usuario eliminado correctamente: " + codU);
            } else {
                System.out.println("⚠️ No se encontró usuario con código: " + codU);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DaoImplementacionMysql.class.getName()).log(Level.SEVERE, null, ex);
        }

        return eliminado;
    }

    /**
     *
     * @param u
     * @return
     * @throws UsuarioExisteException
     */
    @Override
    public boolean insertarUsuario(Usuario u) throws UsuarioExisteException {
        //rezamos a dios
        try (Connection con = Conector.open();
                CallableStatement stmt = con.prepareCall(ANIADIRPERFIL)) {

            try (PreparedStatement psCheck = con.prepareStatement(COMPROBARUSUARIO)) {
                psCheck.setString(1, u.getEmail());
                psCheck.setString(2, u.getUser());
                ResultSet rs = psCheck.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    throw new UsuarioExisteException("El usuario o email ya está registrado.");
                }
            }

            stmt.setString(1, u.getEmail());
            stmt.setString(2, u.getUser());
            stmt.setInt(3, u.getTelefono());
            stmt.setString(4, u.getContra());
            stmt.setString(5, u.getNom());
            stmt.setString(6, u.getApe());
            stmt.setString(7, u.getGenero().name().toUpperCase());
            stmt.setInt(8, u.getNumTarjeta());

            int filas = stmt.executeUpdate();

            System.out.println("✅ Usuario insertado correctamente con procedimiento almacenado.");
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }

}
