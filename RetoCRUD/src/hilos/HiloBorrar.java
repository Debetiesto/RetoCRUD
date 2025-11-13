/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

//import controlador.Controlador;
import controlador.Dao;
import controlador.MostrarDatosControlador;
import javafx.application.Platform;
import modelo.Usuario;

/**
 *
 * @author Luis
 */
public class HiloBorrar implements Runnable {

    private Dao dao;
    private MostrarDatosControlador cont;
    private Usuario usu;

    /**
     * Constructor del hilo
     * @param dao parámetro del dao que necesita recibir este constructor
     * @param cont parámetro del controlador de mostrar datos que necesita recibir este constructor
     * @param usu parámetro de un objeto de usuario que necesita recibir este constructor
     */
    public HiloBorrar(Dao dao, MostrarDatosControlador cont, Usuario usu) {
        this.dao = dao;
        this.cont = cont;
        this.usu = usu;
    }

    /**
     * Método que ejecuta el hilo y llama al método
     * de borrarUsuario del dao para hacer el borrado en un hilo
     */
    @Override
    public void run() {
        boolean eliminado;
        if (usu != null) {
            eliminado = dao.borrarUsuario(usu.getCodU());
            Platform.runLater(() -> {
                if (eliminado) {
                    cont.mostrarMensaje("✅ Usuario eliminado correctamente");
                } else {
                    cont.mostrarMensaje("⚠️ No se ha podido eliminar el usuario.");
                }
            });
        } else {
            System.err.println("❌ Error en la eliminación de datos.");
        }
    }

}
