/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import hilos.HiloLeer;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import main.RetoCRUD;
import modelo.Administrador;
import modelo.Genero;
import modelo.Perfil;
import modelo.Usuario;

/**
 *
 * @author 2dam
 */
public class Controlador implements Initializable {

    private static final Logger logger = Logger.getLogger(Controlador.class.getName());
    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtContra;

    @FXML
    private Button btnLogin;

    @FXML
    private Pane PaneAdmin;
    @FXML
    private Pane PaneUsuario;
    @FXML
    private TextField txtEmailUsuario;
    @FXML
    private TextField txtNomUsuario;
    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtEmailAdmin;
    @FXML
    private TextField txtNomAdmin;
    @FXML
    private TextField txtCuenta;
    @FXML
    private TextField txtTel;
    @FXML
    private TextField txtNTarjeta;
    @FXML
    private TableView tablaDatosUsu;
    @FXML
    private TableColumn<Usuario, String> colEmail;
    @FXML
    private TableColumn<Usuario, String> colUsername;
    @FXML
    private TableColumn<Usuario, Integer> colTelefono;
    @FXML
    private TableColumn<Usuario, String> colNombre;
    @FXML
    private TableColumn<Usuario, String> colApellidos;
    @FXML
    private TableColumn<Usuario, String> colGenero;
    @FXML
    private TableColumn<Usuario, Integer> colTarjeta;
    @FXML
    private ComboBox<Genero> comboGenero;

    private Dao dao;

    private Perfil per;

    @FXML
    private void loginSQL(ActionEvent event) {
        dao = new DaoImplementacionMysql();
        login();
    }

    @FXML
    private void venRegistrar(ActionEvent event) {
        dao = new DaoImplementacionMysql();
        ventanaRegistro();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.info("Aplicación inicializada, DAO configurado a MySQL.");
    }

    private void login() {
        String email = txtEmail.getText();
        String contra = txtContra.getText();
        Alert alert;
        boolean esAdmin;

        Perfil perf = new Perfil();
        perf.setEmail(email);
        perf.setContra(contra);

        Perfil perLog = dao.login(perf);
        esAdmin = dao.esAdministrador(perLog.getCodU());
        if (perLog == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("CAMPOS VACÍOS");
        } else {
            mostrarVentana(perLog, esAdmin);
        }

        logger.info("Usuario autenticado: " + perLog.getUser());

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acceso Concedido");
        alert.setHeaderText("Inicio de sesión exitoso");
        alert.setContentText("¡Bienvenido, " + perLog.getUser() + "!");
        alert.showAndWait();
    }

    private void mostrarVentana(Perfil perLog, boolean esAdmin) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaMostrarDatos.fxml"));
            loader.setController(this);
            Parent root;
            root = loader.load();

            setDatos(perLog, esAdmin);

            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setDatos(Perfil perLog, boolean esAdmin) {
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("user"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("ape"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colTarjeta.setCellValueFactory(new PropertyValueFactory<>("numTarjeta"));
        comboGenero.setItems(FXCollections.observableArrayList(Genero.values()));
        if (esAdmin) {
            if (perLog instanceof Administrador) {
                PaneAdmin.setVisible(true);
                PaneUsuario.setVisible(false);

                Administrador admin = (Administrador) perLog;
                txtEmailAdmin.setText(admin.getEmail());
                txtNomAdmin.setText(admin.getNom());
                txtCuenta.setText(admin.getCuentaCorriente());
                
                cargarDatos(null);
            }
        } else {
            if (perLog instanceof Usuario) {
                PaneAdmin.setVisible(false);
                PaneUsuario.setVisible(true);

                Usuario usu = (Usuario) perLog;
                txtEmailUsuario.setText(usu.getEmail());
                txtNomUsuario.setText(usu.getNom());
                txtUsuario.setText(usu.getUser());
                comboGenero.getSelectionModel().select(usu.getGenero());
                txtApellido.setText(usu.getApe());
                txtTel.setText(String.valueOf(usu.getTelefono()));
                txtNTarjeta.setText(String.valueOf(usu.getNumTarjeta()));

                cargarDatos(usu);
            }

        }

        //txtEmailUsuario.setText(perLog.getEmail());
        //txtNomUsuario.setText(perLog.getNom());
        //txtApellido.setText(perLog.getApe());
        //txtUsuario.setText(perLog.getUser());
    }

    private void cargarDatos(Perfil per) {
        HiloLeer h1 = new HiloLeer(dao, this, (Usuario) per);
        Thread hilo = new Thread(h1);
        hilo.start();
    }

    public void agregarDatosTabla(List<Usuario> usuarios) {
        ObservableList<Usuario> obsList = FXCollections.observableArrayList(usuarios);
        tablaDatosUsu.setItems(obsList);
    }

    private void ventanaRegistro() {

    }

    private void modificarDatosTabla() {
        colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        colApellidos.setCellFactory(TextFieldTableCell.forTableColumn());
        colTelefono.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colTarjeta.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

    }

}
