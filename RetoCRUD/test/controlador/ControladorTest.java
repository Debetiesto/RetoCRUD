/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.Usuario;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.testfx.framework.junit.ApplicationTest;
/**
 *
 * @author 2dam
 */
public class ControladorTest extends ApplicationTest{

    public ControladorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
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

    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(getClass().getResource("/vista/VistaLogin.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
    }

    /**
     * Test of Login method, of class Controlador.
     */
    @Test
    public void testLoginUI() {
        // Ejemplo: escribe en los campos y pulsa el botón
        clickOn("#txtEmail").write("lucas@empresa.com");
        clickOn("#txtContra").write("adminLucas!");
        clickOn("#btnLogin");

        // Aquí podrías comprobar que se abre otra ventana,
        // o que aparece un texto en pantalla, etc.
        // assertEquals(...);
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

}
