/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import hilos.HiloBorrar;
import hilos.HiloCrear;
import hilos.HiloLeer;
import hilos.HiloModificar;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import main.RetoCRUD;
import modelo.Administrador;
import modelo.Genero;
import modelo.Perfil;
import modelo.Usuario;

/**
 *
 * @author Unai, Luis
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
    private Button btnRegistrate;
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

    private Stage popupCarga;

    /**
     * Método que inicia el dao y llama al método de login cuando se le da al
     * botón de login
     *
     * @param event
     */
    @FXML
    private void loginSQL(ActionEvent event) {
        dao = new DaoImplementacionMysql();
        login();
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
     * Método que llama al método de modificar datos de la tabla cuando se da al
     * botón de "modificar datos usuario"
     *
     * @param event
     */
    @FXML
    private void modificarDatosTabla(ActionEvent event) {
        modificarDatosTablaAdmin();
    }

    /**
     * Método que llama al método de modificar datos de administrador cuando se
     * da al botón de "modificar datos"
     *
     * @param event
     */
    @FXML
    private void modificarFieldAdmin(ActionEvent event) {
        modificarDatosAdmin();
    }

    /**
     * Método que llama al método de modificar datos de usuario cuando se da al
     * botón de "modificar datos"
     *
     * @param event
     */
    @FXML
    private void modificarFieldsUsuario(ActionEvent event) {
        modificarDatosVistaUsuario();
    }

    /**
     * Método que llama al método de borrar cuenta de usuario en el panel de
     * usuario cuando se da al botón "borrar cuenta"
     *
     * @param event
     */
    @FXML
    private void borrarEnVistaUsuario(ActionEvent event) {
        borrarCuenta();
    }

    /**
     * Método que borra el usuario de la tabla en el panel de admin cuando se da
     * al botón de "borrar usuario"
     *
     * @param event
     */
    @FXML
    private void borrarEnVistaAdmin(ActionEvent event) {
        borrarUsuario();
    }

    /**
     * Método que inicia la aplicación
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logger.info("Aplicación inicializada, DAO configurado a MySQL.");
    }

    /**
     * Método de login, diferenciando el usuario que ha entrado es decir, si es
     * admin o si no.
     */
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

    /**
     * Método que muestra la ventana con los datos del usuario logeado
     *
     * @param perLog
     * @param esAdmin
     */
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

    /**
     * Método que rellena los campos necesarios con los datos del usuario que se
     * ha logeado. Si es admin se cargan los datos del admin y los datos de la
     * tabla Si es usuario, solo sus datos.
     *
     * @param perLog
     * @param esAdmin
     */
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

    /**
     * Hilo de leer los datos del usuario
     *
     * @param per
     */
    private void cargarDatos(Perfil per) {
        mostrarPopupCarga();

        HiloLeer h1 = new HiloLeer(dao, this, (Usuario) per);
        Thread hilo = new Thread(h1);
        hilo.start();
    }

    /**
     * Método que carga los datos de los usuarios en la tabla del administrador
     *
     * @param usuarios
     */
    public void agregarDatosTabla(List<Usuario> usuarios) {
        ObservableList<Usuario> obsList = FXCollections.observableArrayList(usuarios);
        tablaDatosUsu.setItems(obsList);

    }

    /**
     * Método que abre la ventana de registro
     */
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

    /**
     * Método para registrar un nuevo usuario
     *
     * @param event
     */
    @FXML
    private void registrarUsuario(ActionEvent event) {

        if (txtNom.getText().isEmpty() || txtApe.getText().isEmpty() || txtTelefono.getText().isEmpty()
                || txtUser.getText().isEmpty() || Email.getText().isEmpty()
                || txtContrasena.getText().isEmpty() || txtTarjeta.getText().isEmpty()
                || desplegableGenero.getValue() == null) {

            mostrarMensaje("Campos vacíos." + " Por favor, complete todos los campos.");
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
            nuevo.setGenero((Genero) desplegableGenero.getValue());

            hiloRegistrar(nuevo);

        } catch (Exception e) {
            mostrarMensaje(e.getMessage());
        }
    }

    /**
     * Método que habilita las celdas de la tabla de la vista admin para poder
     * modificar los datos
     */
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

    /**
     * Método para modificar datos del admin
     */
    private void modificarDatosAdmin() {
        txtEmailAdmin.setEditable(true);
        txtNomAdmin.setEditable(true);

        txtEmailAdmin.requestFocus();
        txtEmailAdmin.setOnAction(event -> guardarCambiosAdmin());
        txtNomAdmin.setOnAction(event -> guardarCambiosAdmin());
    }

    /**
     * Hilo de modificar el usuario
     *
     * @param usuario
     */
    private void actualizarUsuarioEnHilo(Usuario usuario) {
        HiloModificar h = new HiloModificar(dao, this, usuario);
        Thread hilo = new Thread(h);
        hilo.start();
    }

    /**
     * Hilo de modificar el administrador
     *
     * @param admin
     */
    private void actualizarAdminEnHilo(Administrador admin) {
        HiloModificar h = new HiloModificar(dao, this, admin);
        Thread hilo = new Thread(h);
        hilo.start();
    }

    /**
     * Método para mostrar un popup con la información necesaria del evento
     *
     * @param mensaje
     */
    public void mostrarMensaje(String mensaje) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText(mensaje);
            alert.show();
    }

    /**
     * Método para guardar los datos del administrador
     */
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

    /**
     * Método para modificar datos del usuario en su vista
     */
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

    /**
     * Método para guardar los cambios modificados
     */
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

    /**
     * Método para borrar un usuario de la base de datos en la vista de usuario
     */
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

    /**
     * Método para borrar un usuario que ha sido seleccionado en la tabla de la
     * vista de administrador
     */
    private void borrarUsuario() {
        Usuario seleccionado = (Usuario) tablaDatosUsu.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            hiloEliminarDatos(seleccionado);
            tablaDatosUsu.getItems().remove(seleccionado);
        } else {
            System.out.println("⚠️ No hay usuario seleccionado.");
        }
    }

    /**
     * Hilo de eliminar datos del usuario
     *
     * @param usu
     */
    private void hiloEliminarDatos(Usuario usu) {
        HiloBorrar h = new HiloBorrar(dao, this, usu);
        Thread hilo = new Thread(h);
        hilo.start();
    }

    /**
     * Método para volver al login al terminar de registrar un usuario nuevo
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
     * Hilo de registrar un nuevo usuario
     *
     * @param nuevo
     */
    private void hiloRegistrar(Usuario nuevo) {
        HiloCrear h = new HiloCrear(dao, this, nuevo);
        Thread hilo = new Thread(h);
        hilo.start();
    }

    /**
     * Método para mostar un popup de carga de datos
     */
    public void mostrarPopupCarga() {
        Platform.runLater(() -> {
            // Evitar abrir dos popups a la vez
            if (popupCarga != null && popupCarga.isShowing()) {
                return;
            }

            popupCarga = new Stage();
            popupCarga.setTitle("Cargando datos...");

            ProgressIndicator progress = new ProgressIndicator();
            Label lbl = new Label("Por favor, espere. Cargando información...");
            lbl.setStyle("-fx-font-size: 14px;");

            VBox vbox = new VBox(15, progress, lbl);
            vbox.setAlignment(Pos.CENTER);
            vbox.setStyle("-fx-padding: 20;");

            Scene scene = new Scene(vbox, 300, 150);
            popupCarga.setScene(scene);
            popupCarga.setResizable(false);

            // Hacerlo modal (bloquea la ventana principal)
            Stage mainStage = (Stage) txtEmail.getScene().getWindow();
            popupCarga.initOwner(mainStage);
            popupCarga.initModality(Modality.WINDOW_MODAL);

            popupCarga.show();
        });
    }

    /**
     * Método que cierra el popup al cabo de unos segundos
     */
    public void cerrarPopupCarga() {
        Platform.runLater(() -> {
            if (popupCarga != null && popupCarga.isShowing()) {
                popupCarga.close();
            }
        });
    }

}
