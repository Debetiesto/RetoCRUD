/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

//import controlador.Controlador;
import controlador.Dao;
import controlador.RegistroControlador;
import excepciones.UsuarioExisteException;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import modelo.Usuario;

/**
 *
 * @author 2dam
 */
public class HiloCrear implements Runnable {

    private Dao dao;
    private RegistroControlador cont;
    private Usuario usu;

    /**
     * Constructor del hilo crear
     * @param dao parámetro del dao que necesita recibir este constructor
     * @param cont parámetro del controlador de registrar que necesita recibir este constructor
     * @param usu parámetro de un objeto de usuario que necesita recibir este constructor
     */
    public HiloCrear(Dao dao, RegistroControlador cont, Usuario usu) {
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
                    cont.mostrarMensaje(Alert.AlertType.INFORMATION, "Registro Exitoso", "Usuario registrado correctamente.");
                    cont.volverLogin();
                }
            });
        } catch (UsuarioExisteException e) {
            Platform.runLater(() -> {
                cont.mostrarMensaje(Alert.AlertType.WARNING, "Advertencia", "⚠️ " + e.getMessage());
            });
        } catch (Exception e) {
        Platform.runLater(() -> {
            cont.mostrarMensaje(Alert.AlertType.ERROR, "Error", "❌ Error al registrar: " + e.getMessage());
        });
    }
        /* if (dao.existeUsuario(usu.getEmail(), usu.getUser())) {
            Platform.runLater(() -> {
                cont.mostrarMensaje("⚠️ El usuario o email ya está registrado.");
            });
        }*/

    }

}
