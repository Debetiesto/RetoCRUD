/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import conexion.PoolCon;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Unai, Luis
 */
public class RetoCRUD extends Application {

    /**
     * Método que abre la ventana principal de la aplicación
     * @param stage 
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/VistaLogin.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("Iniciar Sesión - RetoCRUD");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método que cierra el pool de conexiones.
     */
    @Override
    public void stop() {
        PoolCon.closePool();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
