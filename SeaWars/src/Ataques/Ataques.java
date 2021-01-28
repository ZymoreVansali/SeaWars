/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ataques;

import Cliente.InterfazCliente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JLabel;

/**
 *
 * @author Usuario
 */
public abstract class Ataques implements Serializable{
    String nombre;

    public Ataques(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Stack < Integer > aleatorio(int cant){
        int pos;
            Stack < Integer > pCartas = new Stack < Integer > ();
            for (int i = 0; i < cant ; i++) {
              pos = (int) Math.floor(Math.random() * cant );
              while (pCartas.contains(pos)) {
                pos = (int) Math.floor(Math.random() * cant );
              }
              pCartas.push(pos);
            }
        return pCartas;
    }
    
    
    
    public int dannarFichas(InterfazCliente refPantalla, JLabel lbl, int danno){
        int vida = 100;
        
        for(int j = 0;j < 20;j++){
                for(int c = 0;c < 30;c++){
                    if(refPantalla.labelMatrix[j][c] == lbl){ 
                        if(Integer.parseInt(refPantalla.labelMatrix[j][c].getText()) != 0){
                            if(Integer.parseInt(refPantalla.labelMatrix[j][c].getText())  - danno <= 0){
                                refPantalla.labelMatrix[j][c].setText("0");
                            }
                            else{
                                danno = Integer.parseInt(refPantalla.labelMatrix[j][c].getText()) - danno;
                                refPantalla.labelMatrix[j][c].setText( String.valueOf(danno));
                            }
                        //System.out.println(fichas.get(i).vida);
                    }
                        return Integer.parseInt(refPantalla.labelMatrix[j][c].getText());
                    }
                }
        }
        return vida;
    }
    
}
