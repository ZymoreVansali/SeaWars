/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ataques;

import Cliente.InterfazCliente;
import java.awt.Point;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Usuario
 */
public class Volcanoes extends Ataques{
    
    
    public Volcanoes(String nombre) {
        super(nombre);
    }
    
    private void explosionDePiedra(InterfazCliente refPantalla,int pos){
        Random random = new Random();
        int cant =  pos*10;
        JLabel[][] labelMatrix = refPantalla.labelMatrix;
        Icon rojo = new ImageIcon(getClass().getResource("/Imagenes/rojo.png"));
        int x,y;
        for(int i = 0; i <=cant;i++){
            x = random.nextInt(20); 
            y = random.nextInt(30);
            dannarFichas(refPantalla, labelMatrix[x][y], 20);
            if("0".equals(labelMatrix[x][y].getText())){
                labelMatrix[x][y].setIcon(rojo);
            }
        }
    }
    
    
    public void volcanoRaising(ArrayList<JLabel> fichas, InterfazCliente refPantalla){
        Random random = new Random();
        int x = random.nextInt(20), y = random.nextInt(30), cant = random.nextInt(9)+1;
        int x1 = x,x2 = x,y1,y2;
        JLabel[][] labelMatrix = refPantalla.labelMatrix;
        Icon rojo = new ImageIcon(getClass().getResource("/Imagenes/amarillo.png"));
        for(int i = 1; i <=cant;i++){
            x1 += 1;
            x2 -= 1;
            y1 = y;
            y2 = y;
            labelMatrix[x][y].setIcon(rojo);
                    dannarFichas(refPantalla, labelMatrix[x][y], 100);
            if( x1 > 0&& x1 < 20 && y > 0 && y < 30){
                labelMatrix[x1][y].setIcon(rojo);
                    dannarFichas(refPantalla, labelMatrix[x1][y], 100);
                }
            if( x2 > 0&& x2 < 20 && y > 0 && y < 30){
                labelMatrix[x2][y].setIcon(rojo);
                    dannarFichas(refPantalla, labelMatrix[x2][y], 100);
                }
            for(int j = 1; j <= cant;j++){
                y1 -= 1;
                if( x > 0&& x < 20 && y1 > 0 && y1 < 30){
                        labelMatrix[x][y1].setIcon(rojo);
                    dannarFichas(refPantalla, labelMatrix[x][y1], 100);
                }
                if( x1 > 0&& x1 < 20 && y1 > 0&& y1 < 30){
                    labelMatrix[x1][y1].setIcon(rojo);
                    dannarFichas(refPantalla, labelMatrix[x1][y1], 100);
                }
                if( x2 > 0&& x2 < 20 && y1 > 0 && y1 < 30){
                    labelMatrix[x2][y1].setIcon(rojo);
                    dannarFichas(refPantalla, labelMatrix[x2][y1], 100);
                }
                y2 += 1;
                if( x1 > 0&& x1 < 20 && y2 > 0 && y2 < 30){
                    labelMatrix[x1][y2].setIcon(rojo);
                    dannarFichas(refPantalla, labelMatrix[x1][2], 100);
                }
                if( x1 > 0&& x1 < 20 && y2 > 0 && y2 < 30){
                    labelMatrix[x][y2].setIcon(rojo);
                    dannarFichas(refPantalla, labelMatrix[x][y2], 100);
                }
                if( x2 > 0&& x2 < 20 && y2 > 0 && y2 < 30){
                    labelMatrix[x2][y2].setIcon(rojo);
                    dannarFichas(refPantalla, labelMatrix[x2][y2], 100);
                }
            }
        }
        refPantalla.refCliente.volcanoes.add(new Point(x,y));
        refPantalla.refCliente.cant.add(cant);
    }
    public void volcanoExplotion(ArrayList<JLabel> fichas, InterfazCliente refPantalla){
        Random random = new Random();
        int h = random.nextInt(refPantalla.refCliente.volcanoes.size())-1;
        if(h != -1){
            explosionDePiedra(refPantalla, refPantalla.refCliente.cant.get(h));
        }
        else{
            explosionDePiedra(refPantalla, refPantalla.refCliente.cant.get(h+1));
        }
        }
    
    public void thermalRush(ArrayList<JLabel> fichas, InterfazCliente refPantalla) throws InterruptedException{
        Random random = new Random();
        int h = random.nextInt(2) + 4;
        int x = 0, y = 0; 
        JLabel[][] labelMatrix = refPantalla.labelMatrix;
        Icon rojo = new ImageIcon(getClass().getResource("/Imagenes/rojo.png"));
        System.out.println("Se estan calentando los volcanes");
        sleep(h*1000);
        for(int k = 0; k < refPantalla.refCliente.volcanoes.size(); k++){
            x =refPantalla.refCliente.volcanoes.get(k).x;
            y = refPantalla.refCliente.volcanoes.get(k).y;
            int x1 = x, x2 = x,y1, y2;
            int cant = refPantalla.refCliente.cant.get(k)+5;
            for(int i = 1; i <=cant;i++){
                x1 += 1;
                x2 -= 1;
                y1 = y;
                y2 = y;
                labelMatrix[x][y].setIcon(rojo);
                dannarFichas(refPantalla, labelMatrix[x][y], refPantalla.refCliente.cant.get(k)*h);
                if( x1 > 0&& x1 < 20 && y > 0 && y < 30){
                        dannarFichas(refPantalla, labelMatrix[x1][y], refPantalla.refCliente.cant.get(k)*h);
                        if("0".equals(labelMatrix[x1][y].getText())){
                            labelMatrix[x1][y].setIcon(rojo);
                        }
                    }
                if( x2 > 0&& x2 < 20 && y > 0 && y < 30){
                    
                        dannarFichas(refPantalla, labelMatrix[x2][y], refPantalla.refCliente.cant.get(k)*h);
                        if("0".equals(labelMatrix[x2][y].getText())){
                            labelMatrix[x2][y].setIcon(rojo);
                        }
                    }
                for(int j = 1; j <= cant;j++){
                    y1 -= 1;
                    if( x > 0&& x < 20 && y1 > 0 && y1 < 30){
                            
                        dannarFichas(refPantalla, labelMatrix[x][y1], refPantalla.refCliente.cant.get(k)*h);
                        if("0".equals(labelMatrix[x][y1].getText())){
                            labelMatrix[x][y1].setIcon(rojo);
                        }
                    }
                    if( x1 > 0&& x1 < 20 && y1 > 0&& y1 < 30){
                        
                        dannarFichas(refPantalla, labelMatrix[x1][y1], refPantalla.refCliente.cant.get(k)*h);
                        if("0".equals(labelMatrix[x1][y1].getText())){
                            labelMatrix[x1][y1].setIcon(rojo);
                        }
                    }
                    if( x2 > 0&& x2 < 20 && y1 > 0 && y1 < 30){
                        
                        dannarFichas(refPantalla, labelMatrix[x2][y1], refPantalla.refCliente.cant.get(k)*h);
                        if("0".equals(labelMatrix[x2][y1].getText())){
                            labelMatrix[x2][y1].setIcon(rojo);
                        }
                    }
                    y2 += 1;
                    if( x1 > 0&& x1 < 20 && y2 > 0 && y2 < 30){
                        
                        dannarFichas(refPantalla, labelMatrix[x1][2], refPantalla.refCliente.cant.get(k)*h);
                        if("0".equals(labelMatrix[x1][y2].getText())){
                            labelMatrix[x1][y2].setIcon(rojo);
                        }
                    }
                    if( x1 > 0&& x1 < 20 && y2 > 0 && y2 < 30){
                        
                        dannarFichas(refPantalla, labelMatrix[x][y2], refPantalla.refCliente.cant.get(k)*h);
                        if("0".equals(labelMatrix[x][y2].getText())){
                            labelMatrix[x][y2].setIcon(rojo);
                        }
                    }
                    if( x2 > 0&& x2 < 20 && y2 > 0 && y2 < 30){
                        
                        dannarFichas(refPantalla, labelMatrix[x2][y2], refPantalla.refCliente.cant.get(k)*h);
                        if("0".equals(labelMatrix[x2][y2].getText())){
                            labelMatrix[x2][y2].setIcon(rojo);
                        }
                    }
                }
            }
        }
    }
}

