/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;
import main.RetoCRUD;
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
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

/**
 *
 * @author luisv
 */
public class LoginControladorTest extends ApplicationTest {

    public LoginControladorTest() {
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
     * Test of Login Admin, of class LoginControlador.
     */
    @Test
    public void testLoginAdmin() {
        // Escribe en los campos y pulsa el botón
        clickOn("#txtEmail").write("lucas@empresa.com");
        clickOn("#txtContra").write("adminLucas!");
        clickOn("#btnLogin");

        // Esperamos un breve momento para asegurar que el Alert aparezca en el thread de FX.
        waitForFxEvents();

        // TestFX intentará hacer clic en el botón con el texto "Aceptar".
        clickOn("Aceptar");

        // Comprueba que se abre la vista de admin verificando que hay un elemento
        // exclusívo de ese panel
        verifyThat("#btnModDatosTabla", isVisible());
    }

    /**
     * Test of Login Usuario, of class LoginControlador.
     */
    @Test
    public void testLoginUsuario() {
        clickOn("#txtEmail").write("usuario@prueba.com");
        clickOn("#txtContra").write("passwordUsuario!");
        clickOn("#btnLogin");
    }

    /**
     * Test of initialize method, of class LoginControlador.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL location = null;
        ResourceBundle resources = null;
        LoginControlador instance = new LoginControlador();
        instance.initialize(location, resources);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}
