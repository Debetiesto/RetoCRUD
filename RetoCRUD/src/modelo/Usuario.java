/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author 2dam
 */
public class Usuario extends Perfil{
    private int codU;
    private Genero genero;
    private int numTarjeta;

    
    public Usuario(int codU){
      //  super(email, user, telefono, contra, nom, ape);
        this.codU = codU;
    }

    public Usuario() {

    }
    
    public int getCodU() {
        return codU;
    }

    public void setCodU(int codU) {
        this.codU = codU;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public int getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(int numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    @Override
    public String toString() {
        return "Usuario{" + "codU=" + codU + ", genero=" + genero + ", numTarjeta=" + numTarjeta + '}';
    }
    
}
