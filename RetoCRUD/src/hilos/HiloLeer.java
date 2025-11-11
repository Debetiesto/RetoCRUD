/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import controlador.Controlador;
import controlador.Dao;
import java.util.List;
import javafx.application.Platform;
import modelo.Usuario;

/**
 *
 * @author 2dam
 */
public class HiloLeer implements Runnable {

    private Dao dao;
    private Controlador cont;
    private Usuario usu;

    public HiloLeer(Dao dao, Controlador cont, Usuario usu) {
        this.dao = dao;
        this.cont = cont;
        this.usu = usu;
    }

    @Override
    public void run() {
        List<Usuario> usuarios;
        System.out.println("DEBUG hilo -> dao: " + dao + " | usu: " + usu);
        if (usu == null) {
            // 🟢 Caso admin: cargar todos los usuarios
            usuarios = dao.cargarDatosTabla();
        } else {
            // 🟢 Caso usuario: cargar solo su información o sus datos relacionados
            usuarios = dao.listaUsuarios(usu);
        }

        Platform.runLater(() -> {
            cont.agregarDatosTabla(usuarios);
            cont.cerrarPopupCarga();
        });

    }

}
