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
public class HiloCrear implements Runnable {

    private Dao dao;
    private Controlador cont;
    private Usuario usu;

    public HiloCrear(Dao dao, Controlador cont, Usuario usu) {
        this.dao = dao;
        this.cont = cont;
        this.usu = usu;
    }

    @Override
    public void run() {
        boolean insertado = dao.insertarUsuario(usu);

        Platform.runLater(() -> {
            if (insertado) {
                cont.mostrarMensaje("Registro exitoso." + " Usuario registrado correctamente.");
                cont.volverLogin();
            } else {
                cont.mostrarMensaje("Error, " + "no se pudo registrar el usuario.");
            }
        });

    }

}
