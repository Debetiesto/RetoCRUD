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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import modelo.Administrador;
import modelo.Genero;
import modelo.Perfil;
import modelo.Usuario;

/**
 *
 * @author 2dam
 */
public class MostrarDatosControlador implements Initializable {

    private static final Logger logger = Logger.getLogger(MostrarDatosControlador.class.getName());

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
    private Button btnCerrarSesionAdmin;
    @FXML
    private Button btnCerrarSesionUsuario;
    @FXML
    private Pane PaneAdmin;
    @FXML
    private TextField txtEmailAdmin;
    @FXML
    private TextField txtNomAdmin;
    @FXML
    private TextField txtCuenta;
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
    private TextField txtTel;
    @FXML
    private TextField txtNTarjeta;
    @FXML
    private ComboBox<Genero> comboGenero;
    @FXML
    private TableView<Usuario> tablaDatosUsu;
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
    private Dao dao;
    private Perfil perLog;
    private Stage popupCarga;

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
     * Método que llama al método de cerrar sesión cuando se
     * da al botón de "cerrar sesisón" en el panel de admin
     * @param event 
     */
    @FXML
    private void cerrarSesionAdmin(ActionEvent event) {
        cerrarSesionGeneral(event);
    }
    
        /**
     * Método que llama al método de cerrar sesión cuando se
     * da al botón de "cerrar sesisón" en el panel de usuario
     * @param event 
     */
    @FXML
    private void cerrarSesionUsu(ActionEvent event){
        cerrarSesionGeneral(event);
    }

    /**
     * Método que inicializa la ventana
     * @param location
     * @param resources 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("MostrarDatosControlador inicializado (esperando datos de login).");
    }

    /**
     * Método que se encarga de rellenar los datos en los fields
     * y demás, con varias comprobaciones
     * @param perLog
     * @param esAdmin
     * @param dao 
     */
    public void setDatos(Perfil perLog, boolean esAdmin, Dao dao) {
        this.perLog = perLog;
        this.dao = dao;

        // Configurar las celdas de la tabla (igual que antes)
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("user"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colContra.setCellValueFactory(new PropertyValueFactory<>("contra"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("ape"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colTarjeta.setCellValueFactory(new PropertyValueFactory<>("numTarjeta"));
        comboGenero.setItems(FXCollections.observableArrayList(Genero.values()));

        // Mostrar la interfaz correcta y rellenar los datos
        if (esAdmin) {
            setupVistaAdmin((Administrador) perLog);
            cargarDatos(null);
        } else {
            setupVistaUsuario((Usuario) perLog);
            cargarDatos((Usuario) perLog);
        }
    }

    /**
     * Método que inserta los datos del admin logueado que encuentra en la base de datos
     * en los fields.
     * @param admin 
     */
    private void setupVistaAdmin(Administrador admin) {
        PaneAdmin.setVisible(true);
        PaneUsuario.setVisible(false);
        txtEmailAdmin.setText(admin.getEmail());
        txtNomAdmin.setText(admin.getNom());
        txtCuenta.setText(admin.getCuentaCorriente());
    }

    /**
     * Método que inserta los datos del usuario logeado que encuentra en la base de datos
     * en los fields.
     * @param usu 
     */
    private void setupVistaUsuario(Usuario usu) {
        PaneAdmin.setVisible(false);
        PaneUsuario.setVisible(true);
        txtEmailUsuario.setText(usu.getEmail());
        txtNomUsuario.setText(usu.getNom());
        txtUsuario.setText(usu.getUser());
        comboGenero.getSelectionModel().select(usu.getGenero());
        txtApellido.setText(usu.getApe());
        txtTel.setText(String.valueOf(usu.getTelefono()));
        txtNTarjeta.setText(String.valueOf(usu.getNumTarjeta()));
    }
    
    /**
     * Método que inicia el hilo de cargar datos del usuario logeado
     * @param per 
     */
    private void cargarDatos(Usuario per) {
        mostrarPopupCarga();
        HiloLeer h1 = new HiloLeer(dao, this, per);
        Thread hilo = new Thread(h1);
        hilo.start();
    }

    /**
     * Método que inserta los datos de los usuarios en la
     * tabla de el panel de admin
     * @param usuarios 
     */
    public void agregarDatosTabla(List<Usuario> usuarios) {
        Platform.runLater(() -> {
            ObservableList<Usuario> obsList = FXCollections.observableArrayList(usuarios);
            tablaDatosUsu.setItems(obsList);
            cerrarPopupCarga();
        });
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
        alert.showAndWait();
    }

    /**
     * Método que inicia el hilo de eliminar datos de un usuario
     * @param usu 
     */
    private void hiloEliminarDatos(Usuario usu) {
        HiloBorrar h = new HiloBorrar(dao, this, usu);
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
            Stage mainStage = (Stage) txtEmailAdmin.getScene().getWindow();
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

    private void cerrarSesionGeneral(ActionEvent event) {
        try {
            // 1. Cargar la vista de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaLogin.fxml"));
            Parent root = loader.load();

            // 2. Obtener la Stage actual
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 3. Configurar la nueva Scene y mostrar
            stage.setScene(new Scene(root));
            stage.setTitle("Inicio de Sesión");
            stage.show();

            // 4. (Opcional) Limpiar variables de sesión si existieran (e.g., this.perLog = null;)
            this.perLog = null;
            this.dao = null;

        } catch (IOException ex) {
            Logger.getLogger(MostrarDatosControlador.class.getName()).log(Level.SEVERE, "Error al volver a la VistaLogin.fxml", ex);
            mostrarMensaje("No se pudo cargar la pantalla de login.");
        }
    }
}
