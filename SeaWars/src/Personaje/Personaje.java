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
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Personaje implements Serializable{
    String nombre;
    String imagen;
    int porcentaje;
    Ataques ataque;
    int poder;
    int resistencia;
    int sanidad;

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

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Ataques getAtaque() {
        return ataque;
    }

    public void setAtaque(String ataque) {
        if("FishTelephaty".equals(ataque)){
            this.ataque = new FishTelephaty("Fish Telephaty");
        }
        else if("Kraken".equals(ataque)){
            this.ataque = new Kraken("Kraken");
        }
        else if("TheTrident".equals(ataque)){
            this.ataque = new TheTrident("The Trident");
        }
        else if("Thunder".equals(ataque)){
            this.ataque = new Thunder("The Thunder");
        }
        else if("Volcanoes".equals(ataque)){
            this.ataque = new Volcanoes("Volcanoes");
        }
        else if("WavesControl".equals(ataque)){
            this.ataque = new WavesControl("Waves Control");
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
    
    
    
}
