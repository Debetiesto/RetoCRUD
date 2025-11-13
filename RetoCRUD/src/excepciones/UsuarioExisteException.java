/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excepciones;

/**
 *
 * @author Luis
 */
public class UsuarioExisteException extends Exception {

    /**
     * Excepción de que un usuario ya existe en la base de datos
     * @param mensaje parámetro que necesita este método para lanzar el
     * mensaje de la excepción
     */
    public UsuarioExisteException(String mensaje) {
        super(mensaje);
    }
}
