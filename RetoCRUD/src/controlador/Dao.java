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
     * @param per es el parámetro que necesita este método para pasarle a este
     * método y comprobar las credenciales que se introducen
     * @return Devuelve un objeto del perfil que se ha logeado
     */
    public Perfil login(Perfil per);
    
    /**
     * Creacion metodo de comprobacion de administrador para la clase DaoImplementacionMysql
     * @param codU es el parámetro que necesita este método para buscar el codigo que se
     * pasa a este método y encontrar el administrador
     * @return Devuelve un booleano de si el usuario encontrado es admin o no
     */
    public boolean esAdministrador(int codU);
    
    /**
     * Creacion metodo para cargar datos en tabla para la clase DaoImplementacionMysql
     * @return Devuelve una lista de todos los usuarios
     */
    public List<Usuario> cargarDatosTabla();
    
    /**
     * Creacion metodo para actualizar datos de usuario para la clase DaoImplementacionMysql
     * @param usu es el parámetro que necesita este método para modificar los datos del usuario
     * que recibe este método
     * @return Devuelve un booleano de si se modificó el usuario o no
     */
    public boolean updateUsuario(Usuario usu);
    
    /**
     * Creacion metodo para listar datos del usuario para la clase DaoImplementacionMysql
     * @param usu es el parámetro que necesita este método para leer todos los usuarios
     * @return Devuelve una lista con todos los usuarios
     */
    public List<Usuario> listaUsuarios(Usuario usu);
    
    /**
     * Creacion metodo para actualizar datos de administrador para la clase DaoImplementacionMysql
     * @param admin es el parámetro que necesita este método para modificar los datos del administrador
     * que recibe este método
     * @return Devuelve un booleano de si se modificó el admin o no
     */
    public boolean updateAdmin(Administrador admin);

    /**
     * Creacion metodo para borrar usuario para la clase DaoImplementacionMysql
     * @param codU es el parámetro que necesita este método para borrar un usuario por su
     * código, ya que al ser único no tiene pérdida
     * @return Devuelve un booleano de si se eliminó el usuario o no
     */
    public boolean borrarUsuario(int codU);
    
    /**
     * Creacion metodo para insertar datos para la clase DaoImplementacionMysql
     * @param usuario es el parámetro que necesita este método para recibir el nuevo
     * objeto de usuario que va a recibir
     * @return Devuelve un booleano de si se ha modificado algún dato o no
     * @throws UsuarioExisteException Lanza esta excepción si el usuario ya existe
     */
    public boolean insertarUsuario(Usuario usuario) throws UsuarioExisteException;
    

}
