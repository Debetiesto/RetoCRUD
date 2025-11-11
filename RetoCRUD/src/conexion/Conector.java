/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luis
 */
public class Conector {

    private static boolean holdEnabled = true;
    private static int holdTimeSeconds = 5;

    /**
     * Método que inicia el pool de conexiones
     * @return
     * @throws SQLException 
     */
    public static Connection open() throws SQLException {
        return PoolCon.getConnection();
    }

    /**
     * Método que cierra la conexión con la base de datos
     * @param con 
     */
    public static void close(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método que retiene la conexión con la base de datos
     * durmiendo el hilo
     */
    public static void holdConnection() {
        if (holdEnabled) {
            try {
                System.err.println("⏳ Reteniendo conexión durante " + holdTimeSeconds + " segundos...");
                Thread.sleep(holdTimeSeconds * 1000L);
                System.err.println("✅ Continuando con la conexión a BD.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
