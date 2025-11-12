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
     * @param codU 
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
     * Getter de enum de genero
     * @return 
     */
    public Genero getGenero() {
        return genero;
    }

    /**
     * Setter de enum de genero
     * @param genero 
     */
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    /**
     * Getter de numero de tarjeta
     * @return 
     */
    public int getNumTarjeta() {
        return numTarjeta;
    }

    /**
     * Setter de num de tarjeta
     * @param numTarjeta 
     */
    public void setNumTarjeta(int numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    /**
     * ToString para mostrar los datos del Usuario
     * @return 
     */
    @Override
    public String toString() {
        return "Usuario{" + "codU=" + codU + ", genero=" + genero + ", numTarjeta=" + numTarjeta + '}';
    }
    
}
