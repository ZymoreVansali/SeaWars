/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ataques;

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
    
    
    
    public int dannarFichas(ArrayList<JLabel> fichas, JLabel lbl, int danno){
        int vida = 100;
        System.out.println(fichas.size());
        for(int i = 0; i < fichas.size(); i++){
            if(fichas.get(i) == lbl){
                if(Integer.parseInt(fichas.get(i).getText()) != 0){
                    if(Integer.parseInt(fichas.get(i).getText())  - danno <= 0){
                        fichas.get(i).setText("0");
                    }
                    else{
                        danno = Integer.parseInt(fichas.get(i).getText()) - danno;
                        fichas.get(i).setText(danno+"");
                    }
                    //System.out.println(fichas.get(i).vida);
                }
                return Integer.parseInt(fichas.get(i).getText());
            }
        }
        return vida;
    }
    
}
