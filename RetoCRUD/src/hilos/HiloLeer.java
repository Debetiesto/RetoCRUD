/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

//import controlador.Controlador;
import controlador.Dao;
import controlador.MostrarDatosControlador;
import java.util.List;
import javafx.application.Platform;
import modelo.Usuario;

/**
 *
 * @author Luis
 */
public class HiloLeer implements Runnable {

    private Dao dao;
    private MostrarDatosControlador cont;
    private Usuario usu;

    /**
     * Constructor del hilo leer
     * @param dao parámetro del dao que necesita recibir este constructor
     * @param cont parámetro del controlador de mostrar datos que necesita recibir este constructor
     * @param usu parámetro de un objeto de usuario que necesita recibir este constructor
     */
    public HiloLeer(Dao dao, MostrarDatosControlador cont, Usuario usu) {
        this.dao = dao;
        this.cont = cont;
        this.usu = usu;
    }

    /**
     * Método que inicia el hilo que llama a los métodos
     * del dao para cargar los datos de los usuarios en la tabla
     * o cargar los datos de los usuarios normales 
     */
    @Override
    public void run() {
        List<Usuario> usuarios;
        System.out.println("DEBUG hilo -> dao: " + dao + " | usu: " + usu);
        if (usu == null) {
            // cargar todos los usuarios en tabla admin
            usuarios = dao.cargarDatosTabla();
        } else {
            // cargar solo su información o sus datos relacionados
            usuarios = dao.listaUsuarios(usu);
        }

        Platform.runLater(() -> {
            cont.agregarDatosTabla(usuarios);
            cont.cerrarPopupCarga();
        });

    }

}
