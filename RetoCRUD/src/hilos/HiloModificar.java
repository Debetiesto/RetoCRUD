/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import controlador.Controlador;
import controlador.Dao;

/**
 *
 * @author 2dam
 */
public class HiloModificar implements Runnable{
    private Dao dao;
    private Controlador cont;

    public HiloModificar(Dao dao, Controlador cont) {
        this.dao = dao;
        this.cont = cont;
    }

    @Override
    public void run() {
       
    }
    
    
}
