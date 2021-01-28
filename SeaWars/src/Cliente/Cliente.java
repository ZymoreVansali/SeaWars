package Cliente;

import Ataques.FishTelephaty;
import Ataques.Kraken;
import Ataques.TheTrident;
import Ataques.Thunder;
import Ataques.Volcanoes;
import Ataques.WavesControl;
import Personaje.Personaje;
import java.awt.Color;
import java.awt.Point;
import servidor.FileManager;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class Cliente implements Serializable{
    
    transient Socket socketRef;
    InterfazCliente refPantalla;
    public ThreadCliente hiloCliente;
    ArrayList<Personaje> personajes;
    public ArrayList<JLabel> fichas = new ArrayList<>();
    public boolean vivo = true;
    public boolean controlKraken = false;
    public ArrayList<Point> volcanoes = new ArrayList<>();
    public ArrayList<Integer> cant = new ArrayList<>();

    public Socket getSocketRef() {
        return socketRef;
    }

    public void setSocketRef(Socket socketRef) {
        this.socketRef = socketRef;
    }

    public ArrayList<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(ArrayList<Personaje> personajes) {
        this.personajes = personajes;
    }

    public Cliente(InterfazCliente refPantalla) {
        this.refPantalla = refPantalla;
        refPantalla.setRefCliente(this);
        this.personajes = new ArrayList<Personaje>();
    }
    
    public void conectar(String nombre){     // Para conectarse al servidor
 
        try{
        
            socketRef = new Socket("localhost", 35577);
            hiloCliente = new ThreadCliente(socketRef, refPantalla);
            hiloCliente.start();
            refPantalla.setTitle("Sea war- Nombre del jugador: " + nombre);       // Se pone el titulo de la ventana del jugador
            refPantalla.setNombreJugador(nombre);    // Se pone el nombre del jugador
            hiloCliente.setNombre(nombre);
            Integer numJugador = (Integer)FileManager.readObject("src/Partida/numjugador.dat");
            numJugador = numJugador + 1;
            FileManager.writeObject(numJugador, "src/Partida/numjugador.dat");
            System.out.println("numjugador es " + numJugador);
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        
        
    }
    
    public void conectarLoad(ThreadCliente clienteCargado, String nombre){     // Para conectarse al servidor
 
        try{
        
            socketRef = new Socket("localhost", 35577);
            hiloCliente = new ThreadCliente(socketRef, refPantalla, nombre);
            hiloCliente.start();
            refPantalla.setTitle("Monopoly - Nombre del jugador: " + nombre);       // Se pone el titulo de la ventana del jugador
            refPantalla.setNombreJugador(nombre);    // Se pone el nombre del jugador
            hiloCliente.writer.writeInt(1); //instruccion para el switch del thraed servidor
            hiloCliente.writer.writeUTF(nombre); //instruccion para el switch del thraed servidor
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        
        
    }

    public InterfazCliente getRefPantalla() {
        return refPantalla;
    }

    public void setRefPantalla(InterfazCliente refPantalla) {
        this.refPantalla = refPantalla;
    }

    public ThreadCliente getHiloCliente() {
        return hiloCliente;
    }

    public void setHiloCliente(ThreadCliente hiloCliente) {
        this.hiloCliente = hiloCliente;
    }
    
    private boolean validarPoderes(String[] texto){
        int i = personajes.size() - 1;
        int mayor = 0;
        int medio = 0;
        int menor = 0;
        while(i != -1){
            if(personajes.get(i).getPoder() == 100){
                mayor++;
            }
            else if(personajes.get(i).getPoder()== 75){
                medio++;
            }
            else if(personajes.get(i).getPoder()== 50){
                menor++;
            }
            else{
                return false;
            }
            if(personajes.get(i).getResistencia() == 100){
                mayor++;
            }
            else if(personajes.get(i).getResistencia()== 75){
                medio++;
            }
            else if(personajes.get(i).getResistencia()== 50){
                menor++;
            }
            else{
                return false;
            }
            if(personajes.get(i).getSanidad() == 100){
                mayor++;
            }
            else if(personajes.get(i).getSanidad()== 75){
                medio++;
            }
            else if(personajes.get(i).getSanidad()== 50){
                menor++;
            }
            else{
                return false;
            }
            i--;
        }
        int valor = 0;
        if(null == texto[3]){
            return false;
        }
        else switch (texto[3]) {
            case "100":
                valor = 100;
                break;
            case "75":
                valor = 75;
                break;
            case "50":
                valor = 50;
                break;
            default:
                return false;
        }
        if(valor == 100){
            if(mayor == 3){
                return false;
            }
            else{
                mayor++;
            }
        }
        else if(valor == 75){
            if(medio == 3){
                return false;
            }
            else{
                medio++;
            }
        }
        else if(valor == 50){
            if(menor == 3){
                return false;
            }
            else{
                menor++;
            }
        }
        
        
        if(null == texto[4]){
            return false;
        }
        else switch (texto[4]) {
            case "100":
                valor = 100;
                break;
            case "75":
                valor = 75;
                break;
            case "50":
                valor = 50;
                break;
            default:
                return false;
        }
        if(valor == 100){
            if(mayor == 3){
                return false;
            }
            else{
                mayor++;
            }
        }
        else if(valor == 75){
            if(medio == 3){
                return false;
            }
            else{
                medio++;
            }
        }
        else if(valor == 50){
            if(menor == 3){
                return false;
            }
            else{
                menor++;
            }
        }
        
        if(null == texto[5]){
            return false;
        }
        else switch (texto[5]) {
            case "100":
                valor = 100;
                break;
            case "75":
                valor = 75;
                break;
            case "50":
                valor = 50;
                break;
            default:
                return false;
        }
        if(valor == 100){
            if(mayor == 3){
                return false;
            }
            else{
                mayor++;
            }
        }
        else if(valor == 75){
            if(medio == 3){
                return false;
            }
            else{
                medio++;
            }
        }
        else if(valor == 50){
            if(menor == 3){
                return false;
            }
            else{
                menor++;
            }
        }
        
        return true;
    }
    
    public boolean isStringInt(String s)
{
    try
    {
        Integer.parseInt(s);
        return true;
    } catch (NumberFormatException ex)
    {
        return false;
    }
}
    
    private boolean validarPorcentajes(String texto){
        int i = personajes.size() - 1;
        int porcentajes = 0;
        while(i != -1){
            porcentajes += personajes.get(i).getPorcentaje();
            i--;
        }
        if(!isStringInt(texto)){
            return false;
        }
        else if(Integer.parseInt(texto) > 98 && Integer.parseInt(texto) < 1){
            return false;
        }
        else if(porcentajes+Integer.parseInt(texto) > 100){
            return false;
        }
            
        return true;
    }
    
    private boolean validarAtaque(String texto){
        if(null == texto)return false;
        else switch (texto) {
            case "FishTelephaty":
                break;
            case "Kraken":
                break;
            case "TheTrident":
                break;
            case "Thunder":
                break;
            case "Volcanoes":
                break;
            case "WavesControl":
                break;
            default:
                return false;
        }
        return true;
    }
    
    private boolean validarCreacion(String[] texto){
        if(personajes.size() == 3){
            return false;
        }
        else if(texto.length < 8){
            return false;
        }     
        else if(validarPoderes(texto) != true){
            return false;
        }
        else if(validarPorcentajes(texto[7])!= true){
            return false;
        }
        else if(validarAtaque(texto[6])!= true){
            return false;
        }
        return true;
    }
    
    public String crearPersonajes(String[] separado){
        if(!validarCreacion(separado)){
            return "Ha ocurrido un error en la creacion del personaje";
        }
        Personaje nuevo = new Personaje(); 
        nuevo.setNombre(separado[1]);
        nuevo.setImagen(separado[2]);
        nuevo.setPoder(Integer.parseInt(separado[3]));
        nuevo.setResistencia(Integer.parseInt(separado[4]));
        nuevo.setSanidad(Integer.parseInt(separado[5]));
        nuevo.setAtaque(separado[6]);
        nuevo.setPorcentaje(Integer.parseInt(separado[7]));
        personajes.add(nuevo);


        return "Personaje creado correctamente";
    }
    
    public void verificarVivo(){
        for(int i = 0;i <personajes.size();i++){
            personajes.get(i).verificarVivo();
            if(personajes.get(i).vivo){ 
                return;
            }
        }
        
        this.vivo = false;
    }
    
    
    
    
    
}