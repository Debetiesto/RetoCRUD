/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import modelo.Perfil;
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
 * @author luisv
 */
public class MostrarDatosControladorTest extends ApplicationTest{
    
    public MostrarDatosControladorTest() {
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
     * Test of initialize method, of class MostrarDatosControlador.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL location = null;
        ResourceBundle resources = null;
        MostrarDatosControlador instance = new MostrarDatosControlador();
        instance.initialize(location, resources);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDatos method, of class MostrarDatosControlador.
     */
    @Test
    public void testSetDatos() {
        System.out.println("setDatos");
        Perfil perLog = null;
        boolean esAdmin = false;
        Dao dao = null;
        MostrarDatosControlador instance = new MostrarDatosControlador();
        instance.setDatos(perLog, esAdmin, dao);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of agregarDatosTabla method, of class MostrarDatosControlador.
     */
    @Test
    public void testAgregarDatosTabla() {
        System.out.println("agregarDatosTabla");
        List<Usuario> usuarios = null;
        MostrarDatosControlador instance = new MostrarDatosControlador();
        instance.agregarDatosTabla(usuarios);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mostrarMensaje method, of class MostrarDatosControlador.
     */
    @Test
    public void testMostrarMensaje() {
        System.out.println("mostrarMensaje");
        String mensaje = "";
        MostrarDatosControlador instance = new MostrarDatosControlador();
        instance.mostrarMensaje(mensaje);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mostrarPopupCarga method, of class MostrarDatosControlador.
     */
    @Test
    public void testMostrarPopupCarga() {
        System.out.println("mostrarPopupCarga");
        MostrarDatosControlador instance = new MostrarDatosControlador();
        instance.mostrarPopupCarga();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cerrarPopupCarga method, of class MostrarDatosControlador.
     */
    @Test
    public void testCerrarPopupCarga() {
        System.out.println("cerrarPopupCarga");
        MostrarDatosControlador instance = new MostrarDatosControlador();
        instance.cerrarPopupCarga();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
