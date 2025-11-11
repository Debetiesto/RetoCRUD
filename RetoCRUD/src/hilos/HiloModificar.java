/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import controlador.Controlador;
import controlador.Dao;
import javafx.application.Platform;
import modelo.Administrador;
import modelo.Usuario;

/**
 *
 * @author Luis
 */
public class HiloModificar implements Runnable {

    private Dao dao;
    private Controlador cont;
    private Object persona;

    public HiloModificar(Dao dao, Controlador cont, Object persona) {
        this.dao = dao;
        this.cont = cont;
        this.persona = persona;
    }

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
