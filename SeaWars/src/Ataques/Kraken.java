/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ataques;

import Cliente.InterfazCliente;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Usuario
 */
public class Kraken extends Ataques{
    
    public Kraken(String nombre) {
        super(nombre);
    }

    
    
    public void tentacles(ArrayList<JLabel> fichas, InterfazCliente refPantalla){
        
        Stack < Integer > posX = aleatorio(20);
        Stack < Integer > posY = aleatorio(30);
        JLabel[][] labelMatrix = refPantalla.labelMatrix;
        Icon rojo = new ImageIcon(getClass().getResource("/Imagenes/rojo.png"));
        int cont = 0;
        for(int i =0; i < 3; i++){
            for(int j = 0;j < 20;j++){
                for(int c = 0+cont;c < 30;c++){
                    if(labelMatrix[posX.get(j)][posY.get(c)].getIcon() != rojo && cont < 3){
                        int x = posX.get(j);
                        int y = posY.get(c);
                        System.out.println("x: " + x);
                        System.out.println("y: " + y);
                        if( x >= 0&& x+1 < 20 && y >= 0 && y+1 < 30){
                            labelMatrix[x+1][y+1].setIcon(rojo);
                            dannarFichas(fichas, labelMatrix[x+1][y+1], 100);
                        }
                        if( x >= 0&& x < 20 && y >= 0 && y+1 < 30){
                            labelMatrix[x][y+1].setIcon(rojo);
                            dannarFichas(fichas, labelMatrix[x][y+1], 100);
                        }
                        if( x-1 >= 0&& x < 20 && y >= 0 && y+1 < 30){
                            labelMatrix[x-1][y+1].setIcon(rojo);
                            dannarFichas(fichas, labelMatrix[x-1][y+1], 100);
                        }
                        if( x-1 >= 0&& x < 20 && y >= 0 && y < 30){
                            labelMatrix[x-1][y].setIcon(rojo);
                            dannarFichas(fichas, labelMatrix[x-1][y], 100);
                        }
                        if( x-1 >= 0&& x < 20 && y-1 >= 0 && y < 30){
                            labelMatrix[x-1][y-1].setIcon(rojo);
                            dannarFichas(fichas, labelMatrix[x-1][y-1], 100);
                        }
                        if( x >= 0&& x < 20 && y-1 >= 0 && y < 30){
                            labelMatrix[x][y-1].setIcon(rojo);
                            dannarFichas(fichas, labelMatrix[x][y-1], 100);
                        }
                        if( x >= 0&& x+1 < 20 && y-1 >= 0 && y < 30){
                            labelMatrix[x+1][y-1].setIcon(rojo);
                            dannarFichas(fichas, labelMatrix[x+1][y-1], 100);
                        }
                        if( x >= 0&& x+1 < 20 && y >= 0 && y < 30){
                            labelMatrix[x+1][y].setIcon(rojo);
                            dannarFichas(fichas, labelMatrix[x+1][y], 100);
                        }
                        if( x >= 0&& x < 20 && y >= 0 && y < 30){
                            labelMatrix[x][y].setIcon(rojo);
                            dannarFichas(fichas, labelMatrix[x][y], 100);
                        }
                        cont++;
                        
                    }
                    c = 30;
                }
            }
        }
    }
        
    
    
    
    public void krakenBreath(ArrayList<JLabel> fichas, InterfazCliente refPantalla){
        Random random = new Random();
       int lado = random.nextInt(3);
       int cantidad = random.nextInt(7)+1;
       int x = random.nextInt(20)-1,y = random.nextInt(30)-1;
       JLabel[][] labelMatrix = refPantalla.labelMatrix;
       Icon rojo = new ImageIcon(getClass().getResource("/Imagenes/rojo.png"));
       switch(lado){
           case 0: 
               for(int i = 0; i < cantidad; i++){
                   y -= 1;
                   if( x >= 0&& x < 20 && y >= 0 && y < 30){
                    labelMatrix[x][y].setIcon(rojo);
                    dannarFichas(fichas, labelMatrix[x][y], 100);
                   }
               }
               break;
           case 1: 
               for(int i = 0; i < cantidad; i++){
                   y += 1;
                   if( x >= 0&& x < 20 && y >= 0 && y < 30){
                    labelMatrix[x][y].setIcon(rojo);
                    dannarFichas(fichas, labelMatrix[x][y], 100);
                   }
               }
               break;
           case 2:
               for(int i = 0; i < cantidad; i++){
                   x += 1;
                   if( x >= 0&& x < 20 && y >= 0 && y < 30){
                    labelMatrix[x][y].setIcon(rojo);
                    dannarFichas(fichas, labelMatrix[x][y], 100);
                   }
               }
               break;
           case 3:
               for(int i = 0; i < cantidad; i++){
                   x -= 1;
                   if( x >= 0&& x < 20 && y >=0 && y < 30){
                    labelMatrix[x][y].setIcon(rojo);
                    dannarFichas(fichas, labelMatrix[x][y], 100);
                   }
               }
               break;
           default:
               
               break;
       }
    }
    
    public void releaseTheKraken(ArrayList<JLabel> fichas, InterfazCliente refPantalla){
       Random random = new Random();
        int x = random.nextInt(20),y = random.nextInt(30);
        if(x == 30)
            x--;
        if(y==30)
            y--;
        int x1 = x,x2 = x,y1,y2;
        int cant = random.nextInt(7)+1;
        JLabel[][] labelMatrix = refPantalla.labelMatrix;
        Icon rojo = new ImageIcon(getClass().getResource("/Imagenes/rojo.png"));
        for(int i = 1; i <=cant;i++){
            x1 += 1;
            x2 -= 1;
            y1 = y;
            y2 = y;
            labelMatrix[x][y].setIcon(rojo);
            dannarFichas(fichas, labelMatrix[x][y], 100);
            if( x1 >= 0&& x1 < 20 && y >= 0 && y < 30){
                labelMatrix[x1][y].setIcon(rojo);
                dannarFichas(fichas, labelMatrix[x1][y], 100);
               }
            if( x2 >= 0&& x2 < 20 && y >=0 && y < 30){
                labelMatrix[x2][y].setIcon(rojo);
                dannarFichas(fichas, labelMatrix[x2][y], 100);
               }
            for(int j = 1; j <= cant;j++){
                y1 -= 1;
                if( x >= 0&& x < 20 && y1 >= 0 && y1 < 30){
                    labelMatrix[x][y1].setIcon(rojo);
                    dannarFichas(fichas, labelMatrix[x][y1], 100);
                }
                if( x1 >= 0&& x1 < 20 && y1 >= 0 && y1 < 30){
                    labelMatrix[x1][y1].setIcon(rojo);
                    dannarFichas(fichas, labelMatrix[x1][y1], 100);
                }
                if( x2 >= 0&& x2 < 20 && y1 >= 0 && y1 < 30){
                    labelMatrix[x2][y1].setIcon(rojo);
                    dannarFichas(fichas, labelMatrix[x2][y1], 100);
                }
                y2 += 1;
                if( x1 >= 0&& x1 < 20 && y2 >= 0 && y2 < 30){
                    labelMatrix[x1][y2].setIcon(rojo);
                    dannarFichas(fichas, labelMatrix[x1][y2], 100);
                }
                if( x1 >= 0&& x1 < 20 && y2 >= 0 && y2 < 30){
                    labelMatrix[x][y2].setIcon(rojo);
                    dannarFichas(fichas, labelMatrix[x][y2], 100);
                }
                if( x2 >= 0&& x2 < 20 && y2 >= 0 && y2 < 30){
                    labelMatrix[x2][y2].setIcon(rojo);
                    dannarFichas(fichas, labelMatrix[x2][y2], 100);
                }
            }
        }
    }
}
