/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import controlador.Controlador;
import controlador.Dao;
import excepciones.UsuarioExisteException;
import javafx.application.Platform;
import modelo.Usuario;

/**
 *
 * @author 2dam
 */
public class HiloCrear implements Runnable {

    private Dao dao;
    private Controlador cont;
    private Usuario usu;

    /**
     * Constructor del hilo crear
     * @param dao
     * @param cont
     * @param usu 
     */
    public HiloCrear(Dao dao, Controlador cont, Usuario usu) {
        this.dao = dao;
        this.cont = cont;
        this.usu = usu;
    }

    /**
     * Método que ejecuta el hilo que llama al método
     * del dao para insertar un nuevo usuario
     */
    @Override
    public void run() {
        try {
            boolean insertado = dao.insertarUsuario(usu);

            Platform.runLater(() -> {
                if (insertado) {
                    cont.mostrarMensaje("Registro exitoso." + " Usuario registrado correctamente.");
                    cont.volverLogin();
                }
            });
        } catch (UsuarioExisteException e) {
            Platform.runLater(() -> {
                cont.mostrarMensaje("⚠️ " + e.getMessage());
            });
        } catch (Exception e) {
        Platform.runLater(() -> {
            cont.mostrarMensaje("❌ Error al registrar: " + e.getMessage());
        });
    }
        /* if (dao.existeUsuario(usu.getEmail(), usu.getUser())) {
            Platform.runLater(() -> {
                cont.mostrarMensaje("⚠️ El usuario o email ya está registrado.");
            });
        }*/

    }

}
