/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.RetoCRUD;
import modelo.Usuario;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author 2dam
 */
public class ControladorTest extends ApplicationTest {

    public ControladorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(RetoCRUD.class);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Stops application to be tested: it does nothing.
     */
    @Override
    public void stop() {
    }

    /**
     * Test of Login method, of class Controlador.
     */
    @Test
    public void testLoginUI() {
        // escribe en los campos y pulsa el botón
        clickOn("#txtEmail").write("lucas@empresa.com");
        clickOn("#txtContra").write("adminLucas!");
        clickOn("#btnLogin");
        // comprueba que se abre la vista de admin
        verifyThat("#PaneAdmin", isVisible());

    }

    @Test
    public void testModifyDataTable() {
        clickOn("#btnModDatosTabla");

        TableView<?> tabla = lookup("#tablaDatosUsu").queryTableView();
        clickOn(tabla.lookup(".table-row-cell"));
        List<Node> celdas = new ArrayList<>(lookup(".table-cell").queryAll());
        Node segundaCelda = celdas.get(1); // índice 1 = segunda columna
        doubleClickOn(segundaCelda);
        write("anita_102");
        push(KeyCode.ENTER);
        Object value = tabla.getItems().get(0);
        assertTrue(value.toString().contains("anita_102"));
    }

    /**
     * Test of initialize method, of class Controlador.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL url = null;
        ResourceBundle rb = null;
        Controlador instance = new Controlador();
        instance.initialize(url, rb);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of agregarDatosTabla method, of class Controlador.
     */
    @Test
    public void testAgregarDatosTabla() {
        System.out.println("agregarDatosTabla");
        List<Usuario> usuarios = null;
        Controlador instance = new Controlador();
        instance.agregarDatosTabla(usuarios);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mostrarMensaje method, of class Controlador.
     */
    @Test
    public void testMostrarMensaje() {
        System.out.println("mostrarMensaje");
        String mensaje = "";
        Controlador instance = new Controlador();
        instance.mostrarMensaje(mensaje);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of volverLogin method, of class Controlador.
     */
    @Test
    public void testVolverLogin() {
        System.out.println("volverLogin");
        Controlador instance = new Controlador();
        instance.volverLogin();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mostrarPopupCarga method, of class Controlador.
     */
    @Test
    public void testMostrarPopupCarga() {
        System.out.println("mostrarPopupCarga");
        Controlador instance = new Controlador();
        instance.mostrarPopupCarga();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cerrarPopupCarga method, of class Controlador.
     */
    @Test
    public void testCerrarPopupCarga() {
        System.out.println("cerrarPopupCarga");
        Controlador instance = new Controlador();
        instance.cerrarPopupCarga();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(getClass().getResource("/vista/VistaLogin.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
    }
}
