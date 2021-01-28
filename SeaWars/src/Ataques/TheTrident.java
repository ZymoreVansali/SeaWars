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
public class TheTrident extends Ataques{
    
    public TheTrident(String nombre) {
        super(nombre);
    }
    
    public void threeLines(ArrayList<JLabel> fichas, InterfazCliente refPantalla){
        Random random = new Random();
        Stack < Integer > posX = aleatorio(20);
        Stack < Integer > posY = aleatorio(30);
        int lado = random.nextInt(3);
        JLabel[][] labelMatrix = refPantalla.labelMatrix;
        Icon rojo = new ImageIcon(getClass().getResource("/Imagenes/rojo.png"));
        int cont = 0;
        int cantidad = random.nextInt(7)+1;
        for(int i =0; i < 3; i++){
            for(int j = 0;j < 20;j++){
                for(int c = 0+cont;c < 30;c++){
                    int x = posX.get(j);
                    int y = posY.get(c);
                    switch(lado){
                        case 0: 
                            for(int h = 0; h < cantidad; h++){
                                y -= 1;
                                if( x >= 0&& x < 20 && y >= 0 && y < 30){
                                 labelMatrix[x][y].setIcon(rojo);
                                 dannarFichas(fichas, labelMatrix[x][y], 100);
                                }
                            }
                            break;
                        case 1: 
                            for(int h = 0; h < cantidad; h++){
                                y += 1;
                                if( x >= 0&& x < 20 && y >= 0 && y < 30){
                                 labelMatrix[x][y].setIcon(rojo);
                                 dannarFichas(fichas, labelMatrix[x][y], 100);
                                }
                            }
                            break;
                        case 2:
                            for(int h = 0; h < cantidad; h++){
                                x += 1;
                                if( x >= 0&& x < 20 && y >= 0 && y < 30){
                                 labelMatrix[x][y].setIcon(rojo);
                                 dannarFichas(fichas, labelMatrix[x][y], 100);
                                }
                            }
                            break;
                        case 3:
                            for(int h = 0; h < cantidad; h++){
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
                        cont++;
                        c = 30;
                    }
                    
                }
            }
        }
    
    
    public void threeNumbers(ArrayList<JLabel> fichas, InterfazCliente refPantalla){
    }
    
    public void controlTheKraken(ArrayList<JLabel> fichas, InterfazCliente refPantalla){
            
    }
}
