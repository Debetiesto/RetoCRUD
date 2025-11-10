/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import hilos.HiloBorrar;
import hilos.HiloLeer;
import hilos.HiloModificar;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
    private Button btnModDatosTabla;
    @FXML
    private Button btnModDatosAdmin;
    @FXML
    private Button btnModificarDatosUsu;
    @FXML
    private Button btnBorrarCuenta;
    @FXML
    private Button btnBorrarUsuario;
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
    private TableColumn<Usuario, String> colContra;
    @FXML
    private ComboBox<Genero> comboGenero;
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

    private Perfil perLog;

    @FXML
    private void loginSQL(ActionEvent event) {
        dao = new DaoImplementacionMysql();
        login();
    }

    @FXML
    private void venRegistrar(ActionEvent event) {
        ventanaRegistro();
    }

    @FXML
    private void modificarDatosTabla(ActionEvent event) {
        modificarDatosTablaAdmin();
    }

    @FXML
    private void modificarFieldAdmin(ActionEvent event) {
        modificarDatosAdmin();
    }

    @FXML
    private void modificarFieldsUsuario(ActionEvent event) {
        modificarDatosVistaUsuario();
    }

    @FXML
    private void borrarEnVistaUsuario(ActionEvent event) {
        borrarCuenta();
    }

    @FXML
    private void borrarEnVistaAdmin(ActionEvent event) {
        borrarUsuario();
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

        perLog = dao.login(perf);
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
        colContra.setCellValueFactory(new PropertyValueFactory<>("contra"));
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaRegistro.fxml"));
            Parent root;
            root = loader.load();

            // Obtener el controlador de esta ventana
            Controlador controladorRegistro = loader.getController();

            // Llenar el ComboBox de la ventana de registro
            controladorRegistro.desplegableGenero.getItems().setAll(Genero.values());
            controladorRegistro.desplegableGenero.getSelectionModel().selectFirst();

            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void registrarUsuario(ActionEvent event) {

        // Validar campos vacíos
        if (txtNom.getText().isEmpty()
                || txtApe.getText().isEmpty()
                || txtTelefono.getText().isEmpty()
                || txtUser.getText().isEmpty()
                || txtEmail.getText().isEmpty()
                || txtContrasena.getText().isEmpty()
                || txtTarjeta.getText().isEmpty()
                || comboGenero.getValue() == null) {

            mostrarMensaje("Campos vacíos ,Por favor, complete todos los campos antes de continuar.");
            return;
        }

        try {
            // Inicializar DAO
            dao = new DaoImplementacionMysql();

            // Crear objeto Usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNom(txtNom.getText().trim());
            nuevoUsuario.setApe(txtApe.getText().trim());
            nuevoUsuario.setTelefono(Integer.parseInt(txtTelefono.getText().trim()));
            nuevoUsuario.setUser(txtUser.getText().trim());
            nuevoUsuario.setEmail(txtEmail.getText().trim());
            nuevoUsuario.setContra(txtContrasena.getText().trim());
            nuevoUsuario.setNumTarjeta(Integer.parseInt(txtTarjeta.getText().trim()));
            nuevoUsuario.setGenero(comboGenero.getValue());

            // Insertar en BD
            boolean insertado = dao.insertarUsuario(nuevoUsuario);

            if (insertado) {
                mostrarMensaje("Registro exitoso , El usuario se registró correctamente.");
                login();
            } else {
                mostrarMensaje("Error en el registro, No se pudo registrar el usuario. Intente nuevamente.");
            }

        } catch (NumberFormatException e) {
            mostrarMensaje("Error de formato, El teléfono y la tarjeta deben ser números válidos.");
        } catch (Exception e) {
            mostrarMensaje("Error inesperado");
        }
    }

    private void modificarDatosTablaAdmin() {
        tablaDatosUsu.setEditable(true);

        colUsername.setCellFactory(TextFieldTableCell.forTableColumn());
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        colApellidos.setCellFactory(TextFieldTableCell.forTableColumn());
        colContra.setCellFactory(TextFieldTableCell.forTableColumn());
        colTelefono.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colTarjeta.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        colContra.setOnEditCommit(event -> {
            Usuario u = event.getRowValue();
            u.setContra(event.getNewValue());
            actualizarUsuarioEnHilo(u);
        });

        colUsername.setOnEditCommit(event -> {
            Usuario u = event.getRowValue();
            u.setUser(event.getNewValue());
            actualizarUsuarioEnHilo(u);
        });

        colEmail.setOnEditCommit(event -> {
            Usuario u = event.getRowValue();
            u.setEmail(event.getNewValue());
            actualizarUsuarioEnHilo(u);
        });

        colNombre.setOnEditCommit(event -> {
            Usuario u = event.getRowValue();
            u.setNom(event.getNewValue());
            actualizarUsuarioEnHilo(u);
        });

        colApellidos.setOnEditCommit(event -> {
            Usuario u = event.getRowValue();
            u.setApe(event.getNewValue());
            actualizarUsuarioEnHilo(u);
        });

        colTelefono.setOnEditCommit(event -> {
            Usuario u = event.getRowValue();
            u.setTelefono(event.getNewValue());
            actualizarUsuarioEnHilo(u);
        });

        colTarjeta.setOnEditCommit(event -> {
            Usuario u = event.getRowValue();
            u.setNumTarjeta(event.getNewValue());
            actualizarUsuarioEnHilo(u);
        });

    }

    private void modificarDatosAdmin() {
        txtEmailAdmin.setEditable(true);
        txtNomAdmin.setEditable(true);

        txtEmailAdmin.requestFocus();
        txtEmailAdmin.setOnAction(event -> guardarCambiosAdmin());
        txtNomAdmin.setOnAction(event -> guardarCambiosAdmin());
    }

    private void actualizarUsuarioEnHilo(Usuario usuario) {
        HiloModificar h = new HiloModificar(dao, this, usuario);
        Thread hilo = new Thread(h);
        hilo.start();
    }

    private void actualizarAdminEnHilo(Administrador admin) {
        HiloModificar h = new HiloModificar(dao, this, admin);
        Thread hilo = new Thread(h);
        hilo.start();
    }

    public void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
        System.exit(0);
    }

    private void guardarCambiosAdmin() {
        if (!(perLog instanceof Administrador)) {
            System.out.println("❌ No hay administrador logueado.");
        }

        Administrador admin = (Administrador) perLog;

        admin.setEmail(txtEmailAdmin.getText());
        admin.setNom(txtNomAdmin.getText());

        txtEmailAdmin.setEditable(false);
        txtNomAdmin.setEditable(false);

        actualizarAdminEnHilo(admin);

        System.out.println("✅ Datos del administrador actualizados en BD.");
    }

    private void modificarDatosVistaUsuario() {
        txtEmailUsuario.setEditable(true);
        txtUsuario.setEditable(true);
        txtNomUsuario.setEditable(true);
        txtApellido.setEditable(true);

        txtEmailUsuario.setOnAction(e -> guardarCambiosVistaUsuario());
        txtUsuario.setOnAction(e -> guardarCambiosVistaUsuario());
        txtNomUsuario.setOnAction(e -> guardarCambiosVistaUsuario());
        txtApellido.setOnAction(e -> guardarCambiosVistaUsuario());

    }

    private void guardarCambiosVistaUsuario() {
        if (!(perLog instanceof Usuario)) {
            System.out.println("❌ No hay usuario logueado.");
        }

        Usuario user = (Usuario) perLog;

        user.setEmail(txtEmailUsuario.getText());
        user.setUser(txtUsuario.getText());
        user.setNom(txtNomUsuario.getText());
        user.setApe(txtApellido.getText());

        actualizarUsuarioEnHilo(user);

        System.out.println("✅ Datos del usuario actualizados en BD.");

        txtEmailUsuario.setEditable(false);
        txtUsuario.setEditable(false);
        txtNomUsuario.setEditable(false);
        txtApellido.setEditable(false);
    }

    private void borrarCuenta() {
        if (perLog instanceof Usuario) {
            Usuario usu = (Usuario) perLog;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Seguro que deseas eliminar tu cuenta?");
            alert.setContentText("Esta acción no se puede deshacer.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                hiloEliminarDatos(usu);
                //   mostrarMensaje("Tu cuenta ha sido eliminada correctamente.");
            }
        }
    }

    private void borrarUsuario() {
        Usuario seleccionado = (Usuario) tablaDatosUsu.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            hiloEliminarDatos(seleccionado);
            tablaDatosUsu.getItems().remove(seleccionado);
        } else {
            System.out.println("⚠️ No hay usuario seleccionado.");
        }
    }

    private void hiloEliminarDatos(Usuario usu) {
        HiloBorrar h = new HiloBorrar(dao, this, usu);
        Thread hilo = new Thread(h);
        hilo.start();
    }
}
