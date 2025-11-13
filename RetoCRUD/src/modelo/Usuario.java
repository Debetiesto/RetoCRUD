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
public class Usuario extends Perfil{
    private int codU;
    private Genero genero;
    private int numTarjeta;

    /**
     * Constructor de Usuario
     * @param codU parámetro que requiere ser rellenado en la
     * instancia de este objeto para identificar al usuario
     */
    public Usuario(int codU){
      //  super(email, user, telefono, contra, nom, ape);
        this.codU = codU;
    }

    /**
     * Constructor vacío de Usuario
     */
    public Usuario() {

    }
    
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
     * Getter de enum de genero
     * @return Devuelve un enum con el valor que tiene
     * el atributo genero
     */
    public Genero getGenero() {
        return genero;
    }

    /**
     * Setter de enum de genero
     * @param genero parámetro que necesita este método
     * para darle un valor al atributo de esta clase
     */
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    /**
     * Getter de numero de tarjeta
     * @return Devuelve un int con el valor que tiene
     * el atributo numTarjeta
     */
    public int getNumTarjeta() {
        return numTarjeta;
    }

    /**
     * Setter de num de tarjeta
     * @param numTarjeta parámetro que necesita este método
     * para darle un valor al atributo de esta clase
     */
    public void setNumTarjeta(int numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    /**
     * ToString para mostrar los datos del Usuario
     * @return devuelve un String con los valores que reciben los atributos
     */
    @Override
    public String toString() {
        return "Usuario{" + "codU=" + codU + ", genero=" + genero + ", numTarjeta=" + numTarjeta + '}';
    }
    
}
