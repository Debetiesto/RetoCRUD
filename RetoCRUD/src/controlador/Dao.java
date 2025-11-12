/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import excepciones.UsuarioExisteException;
import java.util.List;
import modelo.Administrador;
import modelo.Perfil;
import modelo.Usuario;

/**
 *
 * @author Unai, Luis
 */
public interface Dao {
    /**
     * Creacion metodo de login para la clase DaoImplementacionMysql
     * @param per
     * @return 
     */
    public Perfil login(Perfil per);
    
    /**
     * Creacion metodo de comprobacion de administrador para la clase DaoImplementacionMysql
     * @param codU
     * @return 
     */
    public boolean esAdministrador(int codU);
    
    /**
     * Creacion metodo para cargar datos en tabla para la clase DaoImplementacionMysql
     * @return 
     */
    public List<Usuario> cargarDatosTabla();
    
    /**
     * Creacion metodo para actualizar datos de usuario para la clase DaoImplementacionMysql
     * @param usu
     * @return 
     */
    public boolean updateUsuario(Usuario usu);
    
    /**
     * Creacion metodo para listar datos del usuario para la clase DaoImplementacionMysql
     * @param usu
     * @return 
     */
    public List<Usuario> listaUsuarios(Usuario usu);
    
    /**
     * Creacion metodo para actualizar datos de administrador para la clase DaoImplementacionMysql
     * @param admin
     * @return 
     */
    public boolean updateAdmin(Administrador admin);

    /**
     * Creacion metodo para borrar usuario para la clase DaoImplementacionMysql
     * @param codU
     * @return 
     */
    public boolean borrarUsuario(int codU);
    
    /**
     * Creacion metodo para insertar datos para la clase DaoImplementacionMysql
     * @param usuario
     * @return
     * @throws UsuarioExisteException 
     */
    public boolean insertarUsuario(Usuario usuario) throws UsuarioExisteException;
    

}
