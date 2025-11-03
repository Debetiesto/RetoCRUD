/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import static com.mysql.cj.conf.PropertyKey.logger;
import conexion.Conector;
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
    private Connection con;
    private PreparedStatement stmt;
    // Sentencias SQL
    final String LOGIN = "SELECT * FROM PERFIL WHERE email = ? AND contra = ?";
    final String BUSCARADMIN = "SELECT * FROM ADMINISTRADOR WHERE CODU = ?";
    final String BUSCARCUENTA = "SELECT cuenta_corriente FROM ADMINISTRADOR WHERE CODU = ?";
    final String BUSCARTARJETA = "SELECT num_tarjeta FROM USUARIO WHERE CODU = ?";
    final String CARGARDATOSTABLA = "SELECT p.CODU, p.EMAIL, p.USERNAME, p.TELEFONO, p.NOMBRE, p.APELLIDOS, "
            + "u.GENERO, u.NUM_TARJETA "
            + "FROM PERFIL p JOIN USUARIO u ON p.CODU = u.CODU";

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

                    PreparedStatement stmtUsuario = con.prepareStatement(BUSCARTARJETA);
                    stmtUsuario.setInt(1, codU);
                    ResultSet rsUsuario = stmtUsuario.executeQuery();
                    if (rsUsuario.next()) {
                        usu.setNumTarjeta(rsUsuario.getInt("num_tarjeta"));
                    }
                    rsUsuario.close();
                    stmtUsuario.close();

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
                u.setNom(rs.getString("NOMBRE"));
                u.setApe(rs.getString("APELLIDOS"));
                u.setGenero(Genero.valueOf(rs.getString("GENERO").toUpperCase()));
                u.setNumTarjeta(rs.getInt("NUM_TARJETA"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public boolean esAdministrador(int codU) {
        ResultSet rs = null;
        boolean esAdmin = false;

        try (Connection con = Conector.open()){
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

}
