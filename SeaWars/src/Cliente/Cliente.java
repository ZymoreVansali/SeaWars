package Cliente;

import Ataques.FishTelephaty;
import Ataques.Kraken;
import Ataques.TheTrident;
import Ataques.Thunder;
import Ataques.Volcanoes;
import Ataques.WavesControl;
import Personaje.Personaje;
import servidor.FileManager;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class Cliente implements Serializable{
    
    transient Socket socketRef;
    InterfazCliente refPantalla;
    public ThreadCliente hiloCliente;
    ArrayList<Personaje> personajes;

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
    }
    
    public void conectar(String nombre){     // Para conectarse al servidor
 
        try{
        
            socketRef = new Socket("localhost", 35577);
            hiloCliente = new ThreadCliente(socketRef, refPantalla);
            hiloCliente.start();
            refPantalla.setTitle("Monopoly - Nombre del jugador: " + nombre);       // Se pone el titulo de la ventana del jugador
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
        if(null == texto[2]){
            return false;
        }
        else switch (texto[2]) {
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
        boolean flag = true;
        if(texto.length < 7){
            return false;
        }     
        else if(!validarPoderes(texto)){
            return false;
        }
        else if(!validarPorcentajes(texto[6])){
            return false;
        }
        else if(!validarAtaque(texto[5])){
            return false;
        }
        
        return true;
    }
    
    public String crearPersonajes(String texto){
        String[] separado = texto.split("-");
        if(!validarCreacion(separado)){
            return "Ha ocurrido un error en la creacion del personaje";
        }
        Personaje nuevo = new Personaje(); 
        nuevo.setNombre(separado[0]);
        nuevo.setImagen(separado[1]);
        nuevo.setPoder(Integer.parseInt(separado[2]));
        nuevo.setResistencia(Integer.parseInt(separado[3]));
        nuevo.setSanidad(Integer.parseInt(separado[4]));
        nuevo.setAtaque(separado[5]);
        nuevo.setPorcentaje(Integer.parseInt(separado[6]));


        return "Personaje creado correctamente";
    }
    
    
}