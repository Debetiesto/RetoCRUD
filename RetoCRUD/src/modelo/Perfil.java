/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Unai, Luis 
 */
public class Perfil {
    //Atributos
    private int codU;
    private String email;
    private String user;
    private int telefono;
    private String contra;
    private String nom;
    private String ape;

    /**
     * Getter de codigo usuario
     * @return 
     */
    public int getCodU() {
        return codU;
    }

    /**
     * Setter de codigo usuario
     * @param codU 
     */
    public void setCodU(int codU) {
        this.codU = codU;
    }

    /**
     * Getter de email
     * @return 
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter de email
     * @param email 
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter de nombre de usuario
     * @return 
     */
    public String getUser() {
        return user;
    }

    /**
     * Setter de nombre de usuario
     * @param user 
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Getter de telefono
     * @return 
     */
    public int getTelefono() {
        return telefono;
    }

    /**
     * Srter de telefono
     * @param telefono 
     */
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    /**
     * Getter de contraseña
     * @return 
     */
    public String getContra() {
        return contra;
    }

    /**
     * Setter de contraseña
     * @param contra 
     */
    public void setContra(String contra) {
        this.contra = contra;
    }

    /**
     * Getter de nombre
     * @return 
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter de nombre
     * @param nom 
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter de apellidos
     * @return 
     */
    public String getApe() {
        return ape;
    }

    /**
     * Setter de apellidos
     * @param ape 
     */
    public void setApe(String ape) {
        this.ape = ape;
    }

    @Override
    public String toString() {
        return "Perfil{" + "codU=" + codU + ", email=" + email + ", user=" + user + ", telefono=" + telefono + ", contra=" + contra + ", nom=" + nom + ", ape=" + ape + '}';
    }
    
}
