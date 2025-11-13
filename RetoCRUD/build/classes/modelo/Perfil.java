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
     * @return devuelve el código del usuario
     */
    public int getCodU() {
        return codU;
    }

    /**
     * Setter de codigo usuario
     * @param codU parámetro que necesita este método
     * para darle un valor al atributo de esta clase
     */
    public void setCodU(int codU) {
        this.codU = codU;
    }

    /**
     * Getter de email
     * @return Devuelve un string con el valor que tiene
     * el atributo email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter de email
     * @param email parámetro que necesita este método
     * para darle un valor al atributo de esta clase
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter de nombre de usuario
     * @return Devuelve un string con el valor que tiene
     * el atributo user
     */
    public String getUser() {
        return user;
    }

    /**
     * Setter de nombre de usuario
     * @param user parámetro que necesita este método
     * para darle un valor al atributo de esta clase
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Getter de telefono
     * @return Devuelve un int con el valor que tiene
     * el atributo telefono
     */
    public int getTelefono() {
        return telefono;
    }

    /**
     * Srter de telefono
     * @param telefono parámetro que necesita este método
     * para darle un valor al atributo de esta clase
     */
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    /**
     * Getter de contraseña
     * @return Devuelve un string con el valor que tiene
     * el atributo contra
     */
    public String getContra() {
        return contra;
    }

    /**
     * Setter de contraseña
     * @param contra parámetro que necesita este método
     * para darle un valor al atributo de esta clase
     */
    public void setContra(String contra) {
        this.contra = contra;
    }

    /**
     * Getter de nombre
     * @return Devuelve un string con el valor que tiene
     * el atributo nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter de nombre
     * @param nom parámetro que necesita este método
     * para darle un valor al atributo de esta clase
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter de apellidos
     * @return Devuelve un string con el valor que tiene
     * el atributo ape
     */
    public String getApe() {
        return ape;
    }

    /**
     * Setter de apellidos
     * @param ape parámetro que necesita este método
     * para darle un valor al atributo de esta clase
     */
    public void setApe(String ape) {
        this.ape = ape;
    }

    /**
     * ToString para mostrar los datos de Perfil
     * @return devuelve un String con los valores que reciben los atributos
     */
    @Override
    public String toString() {
        return "Perfil{" + "codU=" + codU + ", email=" + email + ", user=" + user + ", telefono=" + telefono + ", contra=" + contra + ", nom=" + nom + ", ape=" + ape + '}';
    }
    
}
