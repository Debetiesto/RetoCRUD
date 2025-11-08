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
public class HiloModificar implements Runnable{
    private Dao dao;
    private Controlador cont;
    private Usuario usuario;

    public HiloModificar(Dao dao, Controlador cont, Usuario usuario) {
        this.dao = dao;
        this.cont = cont;
        this.usuario = usuario;
    }

    @Override
    public void run() {
       
        boolean actualizado = dao.updateUsuario(usuario);
        
        Platform.runLater(() -> {
            if (actualizado) {
                cont.mostrarMensaje("✅ Usuario actualizado correctamente: " + usuario.getEmail());
            } else {
                cont.mostrarMensaje("⚠️ No se ha podido actualizar el usuario.");
            }
        });
        
    }
    
    
}
