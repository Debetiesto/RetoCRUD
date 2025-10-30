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
public class Administrador extends Perfil{
    private int codU;
    private String cuentaCorriente;

    public int getCodU() {
        return codU;
    }

    public void setCodU(int codU) {
        this.codU = codU;
    }

    public String getCuentaCorriente() {
        return cuentaCorriente;
    }

    public void setCuentaCorriente(String cuentaCorriente) {
        this.cuentaCorriente = cuentaCorriente;
    }

    @Override
    public String toString() {
        return "Administrador{" + "codU=" + codU + ", cuentaCorriente=" + cuentaCorriente + '}';
    }
    
}
