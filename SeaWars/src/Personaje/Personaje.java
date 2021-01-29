/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Personaje;

import Ataques.Ataques;
import Ataques.FishTelephaty;
import Ataques.Kraken;
import Ataques.TheTrident;
import Ataques.Thunder;
import Ataques.Volcanoes;
import Ataques.WavesControl;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author Usuario
 */
public class Personaje implements Serializable{
    private String nombre = "";
    private String imagen = "";
    private double porcentaje = 0.0;
    public Ataques ataque;
    private int poder = 0;
    private int resistencia = 0;
    private int sanidad = 0;
    public ArrayList<JLabel> fichas = new ArrayList<>();
    public boolean vivo = true;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje/100;
    }

    public Ataques getAtaque() {
        return ataque;
    }

    public void setAtaque(String ataque) {
        if("FishTelephaty".equals(ataque)){
            this.ataque = new FishTelephaty("FishTelephaty");
        }
        else if("Kraken".equals(ataque)){
            this.ataque = new Kraken("Kraken");
        }
        else if("TheTrident".equals(ataque)){
            this.ataque = new TheTrident("TheTrident");
        }
        else if("Thunder".equals(ataque)){
            this.ataque = new Thunder("Thunder");
        }
        else if("Volcanoes".equals(ataque)){
            this.ataque = new Volcanoes("Volcanoes");
        }
        else if("WavesControl".equals(ataque)){
            this.ataque = new WavesControl("WavesControl");
        }
    }

    public int getPoder() {
        return poder;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    public int getResistencia() {
        return resistencia;
    }

    public void setResistencia(int resistencia) {
        this.resistencia = resistencia;
    }

    public int getSanidad() {
        return sanidad;
    }

    public void setSanidad(int sanidad) {
        this.sanidad = sanidad;
    }
    
    public void agregarFichas(JLabel lbl){
        fichas.add(lbl);
    }
    
    
    public void verificarVivo(){
        String msm = "";
        for(int i = 0;i <fichas.size();i++){  
            msm = fichas.get(i).getText();
            if("0".equals(msm) == false){ 
                return;
            }
        }
        this.vivo = false;
    }
    
}
