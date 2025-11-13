/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Genero;
import modelo.Perfil;

/**
 *
 * @author 2dam
 */
public class LoginControlador implements Initializable {

    private static final Logger logger = Logger.getLogger(LoginControlador.class.getName());

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtContra;

    @FXML
    private Button btnLogin;

    private Dao dao;

    private Perfil perLog;

    /**
     * Método qee inicia el dao
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = new DaoImplementacionMysql();
        logger.info("LoginControlador inicializado.");
    }

    /**
     * Método que inicia el dao y llama al método de login cuando se le da al
     * botón de login
     *
     * @param event
     */
    @FXML
    private void loginSQL(ActionEvent event) {
        String email = txtEmail.getText();
        String contra = txtContra.getText();

        if (email.isEmpty() || contra.isEmpty()) {
            mostrarMensaje(Alert.AlertType.ERROR, "CAMPOS VACÍOS", "Por favor, introduzca Email y Contraseña.");
        }

        login(email, contra);
    }

    /**
     * Método que llama a la ventana de registro cuando se da al label de
     * registrar
     *
     * @param event
     */
    @FXML
    private void venRegistrar(ActionEvent event) {
        ventanaRegistro();
    }

    /**
     * Método de login, diferenciando el usuario que ha entrado es decir, si es
     * admin o si no.
     */
    private void login(String email, String contra) {
        try {
            Perfil perf = new Perfil();
            perf.setEmail(email);
            perf.setContra(contra);

            Perfil perLog = dao.login(perf);

            if (perLog == null) {
                mostrarMensaje(Alert.AlertType.ERROR, "Acceso Denegado", "Email o contraseña incorrectos.");
            } else {
                boolean esAdmin = dao.esAdministrador(perLog.getCodU());
                mostrarMensaje(Alert.AlertType.INFORMATION, "Acceso Concedido", "¡Bienvenido, " + perLog.getUser() + "!");
                mostrarVentanaDatos(perLog, esAdmin);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error en el proceso de login.", e);
            mostrarMensaje(Alert.AlertType.ERROR, "Error de Sistema", "Ocurrió un error al intentar iniciar sesión.");
        }
    }
    /**
     * Método que muestra la ventana con los datos del usuario logeado
     *
     * @param perLog
     * @param esAdmin
     */
    private void mostrarVentanaDatos(Perfil perLog, boolean esAdmin) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaMostrarDatos.fxml"));
            Parent root = loader.load();

            // **IMPORTANTE:** Obtener el controlador inmediatamente después de la carga
            MostrarDatosControlador controladorDatos = loader.getController();

            // Llamar al método de inicialización con los datos necesarios
            controladorDatos.setDatos(perLog, esAdmin, dao);

            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error al cargar VistaMostrarDatos.fxml", ex);
        }
    }

    /**
     * Método que abre la ventana de registro
     */
    private void ventanaRegistro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaRegistro.fxml"));
            Parent root = loader.load();

            // Opcional: pasar el DAO al controlador de registro si es necesario
            RegistroControlador controladorRegistro = loader.getController();
            controladorRegistro.setDao(dao);

            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Registro de Usuario");
            stage.show();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error al cargar VistaRegistro.fxml", ex);
        }
    }

    /**
     * Método para mostrar un popup con la información necesaria del evento
     * @param type
     * @param title
     * @param content 
     */
    private void mostrarMensaje(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
