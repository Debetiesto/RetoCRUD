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
import modelo.Administrador;
import modelo.Usuario;

/**
 *
 * @author Luis
 */
public class HiloModificar implements Runnable {

    private Dao dao;
    private MostrarDatosControlador cont;
    private Object persona;

    /**
     * Constructor del hilo modificar
     * @param dao parámetro del dao que necesita recibir este constructor
     * @param cont parámetro del controlador de mostrar datos que necesita recibir este constructor
     * @param persona parámetro de un objeto que necesita recibir este constructor
     */
    public HiloModificar(Dao dao, MostrarDatosControlador cont, Object persona) {
        this.dao = dao;
        this.cont = cont;
        this.persona = persona;
    }

    /**
     * Método que ejecuta el hilo y llama
     * tanto al método del dao updateUsuario, 
     * como al updateAdmin (dependiendo del tipo de objeto) para hacer la modificación
     * en un hilo
     */
    @Override
    public void run() {
        boolean actualizado;
        if (persona instanceof Usuario) {
            actualizado = dao.updateUsuario((Usuario) persona);
            Platform.runLater(() -> {
                if (actualizado) {
                    cont.mostrarMensaje("✅ Usuario actualizado correctamente");
                } else {
                    cont.mostrarMensaje("⚠️ No se ha podido actualizar el usuario.");
                }
            });
        } else if (persona instanceof Administrador) {
            actualizado = dao.updateAdmin((Administrador) persona);
            Platform.runLater(() -> {
                if (actualizado) {
                    cont.mostrarMensaje("✅ Usuario actualizado correctamente");
                } else {
                    cont.mostrarMensaje("⚠️ No se ha podido actualizar el usuario.");
                }
            });
        } else {
            System.err.println("❌ Tipo de objeto no reconocido en HiloModificar");
        }
    }

}
