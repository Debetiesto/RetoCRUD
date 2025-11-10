/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import controlador.Controlador;
import controlador.Dao;
import javafx.application.Platform;
import modelo.Usuario;

/**
 *
 * @author 2dam
 */
public class HiloBorrar implements Runnable {

    private Dao dao;
    private Controlador cont;
    private Usuario usu;

    public HiloBorrar(Dao dao, Controlador cont, Usuario usu) {
        this.dao = dao;
        this.cont = cont;
        this.usu = usu;
    }

    @Override
    public void run() {
        boolean eliminado;
        if (usu != null) {
            eliminado = dao.borrarUsuario(usu.getCodU());
            Platform.runLater(() -> {
                if (eliminado) {
                    cont.mostrarMensaje("✅ Usuario actualizado correctamente");
                } else {
                    cont.mostrarMensaje("⚠️ No se ha podido actualizar el usuario.");
                }
            });
        } else {
            System.err.println("❌ Error en la eliminación de datos.");
        }
    }

}
