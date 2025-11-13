/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import hilos.HiloCrear;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Genero;
import modelo.Usuario;

/**
 *
 * @author 2dam
 */
public class RegistroControlador implements Initializable {

    private static final Logger logger = Logger.getLogger(RegistroControlador.class.getName());

    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtApe;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtUser;
    @FXML
    private TextField Email;
    @FXML
    private TextField txtTarjeta;
    @FXML
    private PasswordField txtContrasena;
    @FXML
    private ComboBox<Genero> desplegableGenero;

    private Dao dao;

    public void setDao(Dao dao) {
        this.dao = dao;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        desplegableGenero.getItems().setAll(Genero.values());
        desplegableGenero.getSelectionModel().selectFirst();
        logger.info("RegistroControlador inicializado.");
    }

    /**
     * Maneja el evento de registrar un nuevo usuario.
     */
    @FXML
    private void registrarUsuario(ActionEvent event) {
        if (txtNom.getText().isEmpty() || txtApe.getText().isEmpty() || txtTelefono.getText().isEmpty()
                || txtUser.getText().isEmpty() || Email.getText().isEmpty()
                || txtContrasena.getText().isEmpty() || txtTarjeta.getText().isEmpty()
                || desplegableGenero.getValue() == null) {

            mostrarMensaje(Alert.AlertType.WARNING, "Campos vacíos", "Por favor, complete todos los campos.");
        }

        try {
            Usuario nuevo = new Usuario();
            nuevo.setNom(txtNom.getText());
            nuevo.setApe(txtApe.getText());
            nuevo.setTelefono(Integer.parseInt(txtTelefono.getText()));
            nuevo.setUser(txtUser.getText());
            nuevo.setEmail(Email.getText());
            nuevo.setContra(txtContrasena.getText());
            nuevo.setNumTarjeta(Integer.parseInt(txtTarjeta.getText()));
            nuevo.setGenero(desplegableGenero.getValue());

            hiloRegistrar(nuevo);

            // La transición se hace en el callback del hilo, pero mostramos éxito aquí
            mostrarMensaje(Alert.AlertType.INFORMATION, "Registro Exitoso", "Usuario registrado. Volviendo al login.");

        } catch (NumberFormatException e) {
            mostrarMensaje(Alert.AlertType.ERROR, "Error de Formato", "Teléfono o Tarjeta deben ser números válidos.");
        } catch (Exception e) {
            // Este catch es para otras excepciones (ej. si el hilo devuelve un error)
            mostrarMensaje(Alert.AlertType.ERROR, "Error de Registro", e.getMessage());
        }
    }

    /**
     * Hilo para registrar un nuevo usuario.
     */
    private void hiloRegistrar(Usuario nuevo) {
        HiloCrear h = new HiloCrear(dao, this, nuevo);
        Thread hilo = new Thread(h);
        hilo.start();
    }

    /**
     * Método de callback llamado por el hilo al terminar la operación.
     */
    public void volverLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaLogin.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) txtNom.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Inicio de sesión");
            stage.show();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error al volver al login", ex);
        }
    }

    /**
     * Utilidad para mostrar alertas.
     */
    public void mostrarMensaje(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
