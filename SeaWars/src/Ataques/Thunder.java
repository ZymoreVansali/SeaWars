/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ataques;

import Cliente.InterfazCliente;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Usuario
 */
public class Thunder extends Ataques{
    
    
    public Thunder(String nombre) {
        super(nombre);
    }
    
    public void thunderRain(ArrayList<JLabel> fichas, InterfazCliente refPantalla){
        Random random = new Random();
        int x, y, danno;
        JLabel[][] labelMatrix = refPantalla.labelMatrix;
       Icon rojo = new ImageIcon(getClass().getResource("/Imagenes/rojo.png"));
        for(int i = 0; i < 100; i++){
            x = random.nextInt(20);
            y = random.nextInt(30);
            danno = random.nextInt(10)+10;
            if( x >= 0&& x < 20 && y >= 0 && y < 30){
                dannarFichas(refPantalla, labelMatrix[x][y], danno);
                if( "0".equals(labelMatrix[x][y].getText())){
                    labelMatrix[x][y].setIcon(rojo);
                }
            }
        }
    }
    public void poseidonThunder(ArrayList<JLabel> fichas, InterfazCliente refPantalla){
        Random random = new Random();
        int x = random.nextInt(20), y = random.nextInt(30);
        int x1 = x,x2 = x,y1,y2;
        int rayos = random.nextInt(5)+5;
        int cant = random.nextInt(9)+2;
        JLabel[][] labelMatrix = refPantalla.labelMatrix;
        Icon rojo = new ImageIcon(getClass().getResource("/Imagenes/rojo.png"));
        for(int k = 0; k < rayos; k ++){
            for(int i = 1; i <=cant;i++){
                x1 += 1;
                x2 -= 1;
                y1 = y;
                y2 = y;
                labelMatrix[x][y].setIcon(rojo);
                dannarFichas(refPantalla, labelMatrix[x][y], 100);
                if( x1 >= 0&& x1 < 20 && y >= 0 && y < 30){
                    labelMatrix[x1][y].setIcon(rojo);
                    dannarFichas(refPantalla, labelMatrix[x1][y], 100);
                   }
                if( x2 >= 0&& x2 < 20 && y >=0 && y < 30){
                    labelMatrix[x2][y].setIcon(rojo);
                    dannarFichas(refPantalla, labelMatrix[x2][y], 100);
                   }
                for(int j = 1; j <= cant;j++){
                    y1 -= 1;
                    if( x >= 0&& x < 20 && y1 >= 0 && y1 < 30){
                        labelMatrix[x][y1].setIcon(rojo);
                        dannarFichas(refPantalla, labelMatrix[x][y1], 100);
                    }
                    if( x1 >= 0&& x1 < 20 && y1 >= 0 && y1 < 30){
                        labelMatrix[x1][y1].setIcon(rojo);
                        dannarFichas(refPantalla, labelMatrix[x1][y1], 100);
                    }
                    if( x2 >= 0&& x2 < 20 && y1 >= 0 && y1 < 30){
                        labelMatrix[x2][y1].setIcon(rojo);
                        dannarFichas(refPantalla, labelMatrix[x2][y1], 100);
                    }
                    y2 += 1;
                    if( x1 >= 0&& x1 < 20 && y2 >= 0 && y2 < 30){
                        labelMatrix[x1][y2].setIcon(rojo);
                        dannarFichas(refPantalla, labelMatrix[x1][y2], 100);
                    }
                    if( x1 >= 0&& x1 < 20 && y2 >= 0 && y2 < 30){
                        labelMatrix[x][y2].setIcon(rojo);
                        dannarFichas(refPantalla, labelMatrix[x][y2], 100);
                    }
                    if( x2 >= 0&& x2 < 20 && y2 >= 0 && y2 < 30){
                        labelMatrix[x2][y2].setIcon(rojo);
                        dannarFichas(refPantalla, labelMatrix[x2][y2], 100);
                    }
                }
            }
        }
    }
    public void eelAtack(ArrayList<JLabel> fichas, InterfazCliente refPantalla){
        Random random = new Random();
        int cant = random.nextInt(75)+25;
        int x,y,danno;
        JLabel[][] labelMatrix = refPantalla.labelMatrix;
        Icon rojo = new ImageIcon(getClass().getResource("/Imagenes/rojo.png"));
        for(int i = 0; i < cant;i++){
            x = random.nextInt(20);
            y = random.nextInt(30);
            danno = (random.nextInt(9)+1)*10;
            if( x >= 0&& x < 20 && y >= 0 && y < 30){
                dannarFichas(refPantalla, labelMatrix[x][y], danno);
                if( "0".equals(labelMatrix[x][y].getText())){
                    labelMatrix[x][y].setIcon(rojo);
                }
            }
        }
    }
    
}
