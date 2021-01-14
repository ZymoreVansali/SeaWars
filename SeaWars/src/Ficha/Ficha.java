/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ficha;

import javax.swing.JLabel;

/**
 *
 * @author Usuario
 */
public class Ficha {
    public JLabel label;
    public int vida = 100;

    public Ficha(JLabel label) {
        this.label = label;
    }

    

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }
    
}
