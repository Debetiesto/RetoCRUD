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
public class Administrador extends Perfil{
    //Atributos
    private int codU;
    private String cuentaCorriente;

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
     * Getter de cuenta corriente
     * @return devuelve la cuenta corriente del administrador
     */
    public String getCuentaCorriente() {
        return cuentaCorriente;
    }

    /**
     * Setter de cuenta corriente
     * @param cuentaCorriente parámetro que necesita este método
     * para darle un valor al atributo de esta clase
     */
    public void setCuentaCorriente(String cuentaCorriente) {
        this.cuentaCorriente = cuentaCorriente;
    }

    /**
     * ToString que muestra los datos del administrador
     * @return devuelve un String con los valores que reciben los atributos
     */
    @Override
    public String toString() {
        return "Administrador{" + "codU=" + codU + ", cuentaCorriente=" + cuentaCorriente + '}';
    }
    
}
