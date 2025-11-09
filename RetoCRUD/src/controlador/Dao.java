/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.List;
import modelo.Administrador;
import modelo.Perfil;
import modelo.Usuario;

/**
 *
 * @author 2dam
 */
public interface Dao {
    public Perfil login(Perfil per);
    
    public boolean esAdministrador(int codU);
    
    public List<Usuario> cargarDatosTabla();
    
    public boolean updateUsuario(Usuario usu);
    
    public List<Usuario> listaUsuarios(Usuario usu);
    
    public boolean updateAdmin(Administrador admin);
}
