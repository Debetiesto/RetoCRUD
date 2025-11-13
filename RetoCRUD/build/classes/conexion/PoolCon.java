/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Luis
 */
public class PoolCon {
    
    private static BasicDataSource dataSource;
    private static long PAUSA_MS;

    /**
     * Recibir credenciales del ResourceBundle
     */
    static {
        try {
            ResourceBundle conf = ResourceBundle.getBundle("modelo.configClass");

            String url = conf.getString("Conn");
            String user = conf.getString("DBUser");
            String password = conf.getString("DBPass");
            String driver = conf.getString("Driver");

            dataSource = new BasicDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(user);
            dataSource.setPassword(password);
            dataSource.setDriverClassName(driver);

            dataSource.setInitialSize(Integer.parseInt(conf.getString("initialSize")));
            dataSource.setMaxTotal(Integer.parseInt(conf.getString("maxTotal")));
            dataSource.setMaxIdle(Integer.parseInt(conf.getString("maxIdle")));
            dataSource.setMinIdle(Integer.parseInt(conf.getString("minIdle")));
            dataSource.setMaxWaitMillis(Long.parseLong(conf.getString("maxWaitMillis")));

            PAUSA_MS = Long.parseLong(conf.getString("holdTimeSecond")) * 1000;

            System.out.println("Pool de conexiones inicializado.");
        } catch (Exception e) {
            System.out.println("⚠️ Error al inicializar pool: " + e.getMessage());
        }
    }

    /**
     * Método que inicia la conexión con la base de datos
     * @return devuelve la conexión con el archivo de configuración
     * @throws SQLException es una excepción genérica de sql
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Método que cierra el Pool de conexiones
     */
    public static void closePool() {
        try {
            if (dataSource != null) {
                dataSource.close();
                System.out.println("✅ Pool de conexiones cerrado correctamente.");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error al cerrar el pool: " + e.getMessage());
        }
    }

    /**
     * Método que duerme el hilo durante unos segundos
     * @param con parámetro que necesita el método para
     * dormir el hilo
     */
    public static void pausarConexion(Connection con) {
        if (con == null) {
            return;
        }
        try {
            Thread.sleep(PAUSA_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
