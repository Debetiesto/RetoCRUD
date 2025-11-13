/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.testfx.framework.junit.ApplicationTest;

/**
 *
 * @author luisv
 */
public class RegistroControladorTest extends ApplicationTest{
    
    public RegistroControladorTest() {
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

    /**
     * Test of setDao method, of class RegistroControlador.
     */
    @Test
    public void testSetDao() {
        System.out.println("setDao");
        Dao dao = null;
        RegistroControlador instance = new RegistroControlador();
        instance.setDao(dao);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of initialize method, of class RegistroControlador.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL location = null;
        ResourceBundle resources = null;
        RegistroControlador instance = new RegistroControlador();
        instance.initialize(location, resources);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of volverLogin method, of class RegistroControlador.
     */
    @Test
    public void testVolverLogin() {
        System.out.println("volverLogin");
        RegistroControlador instance = new RegistroControlador();
        instance.volverLogin();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mostrarMensaje method, of class RegistroControlador.
     */
    @Test
    public void testMostrarMensaje() {
        System.out.println("mostrarMensaje");
        Alert.AlertType type = null;
        String title = "";
        String content = "";
        RegistroControlador instance = new RegistroControlador();
        instance.mostrarMensaje(type, title, content);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
