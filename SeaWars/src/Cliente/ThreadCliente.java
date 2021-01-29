/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Ataques.FishTelephaty;
import Ataques.Kraken;
import Ataques.TheTrident;
import Ataques.Thunder;
import Ataques.Volcanoes;
import Ataques.WavesControl;
import java.awt.Color;
import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.OverlayLayout;
import servidor.FileManager;


public class ThreadCliente extends Thread implements Serializable{
    
    transient private Socket socketRef;
    transient public DataInputStream reader;
    transient public DataOutputStream writer;
    transient public ObjectOutputStream objWriter;
    private String nombre;
    private boolean running = true;
    transient public InterfazCliente refPantalla;
    private int numOrden = 1;

    private String nombreConsultar;
    private int contadorConsultar = 0;

    public ThreadCliente(Socket socketRef, InterfazCliente refPantalla) throws IOException {
        this.socketRef = socketRef;
        reader = new DataInputStream(socketRef.getInputStream());
        writer = new DataOutputStream(socketRef.getOutputStream());
        objWriter = new ObjectOutputStream(socketRef.getOutputStream());
        this.refPantalla = refPantalla;
        nombreConsultar = "";
    }
    
    public ThreadCliente(Socket socketRef, InterfazCliente refPantalla, String nombreCargado) throws IOException {
        this.socketRef = socketRef;
        reader = new DataInputStream(socketRef.getInputStream());
        writer = new DataOutputStream(socketRef.getOutputStream());
        objWriter = new ObjectOutputStream(socketRef.getOutputStream());
        this.refPantalla = refPantalla;
        nombre = nombreCargado;
    }

    public int getContadorConsultar() {
        return contadorConsultar;
    }

    public void setContadorConsultar(int contadorConsultar) {
        this.contadorConsultar = contadorConsultar;
    }
    
    

    public String getNombreConsultar() {
        return nombreConsultar;
    }

    public void setNombreConsultar(String nombreConsultar) {
        this.nombreConsultar = nombreConsultar;
    }
    
    

    public int getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(int numOrden) {
        this.numOrden = numOrden;
    }


    
    
    public void solicitarPropiedad(){ // Parametro debería en realidad ser un objeto tipo Propiedad pero la clase no existe todavía
        
    }
    
    public void solicitarCasa(){ // Parametro debería ser un objeto tipo Casa
        
    }
    
    public void solicitarHotel(){ // Parametro debería ser un objeto tipo Hotel
        
    }
    
    public void actuarCarta(){      // Para reaccionar ante las diferentes cartas del juego
        
    }
    
    
    public void run (){
        
        int instruccionId = 1;
        while (running){
            try {
                String usuario = "";
                instruccionId = reader.readInt(); // esperar hasta que reciba un entero
                
                switch (instruccionId){
                    case 1:// verificarVivo
                           usuario = reader.readUTF();
                           String atacante = reader.readUTF();
                           refPantalla.addMensaje("El jugador " + usuario + " ha sido eliminado por "+atacante);
                    break;
                    case 2: // pasan un mensaje por el chat
                        usuario = reader.readUTF();
                        String mensaje = reader.readUTF();
                        //System.out.println("CLIENTE Recibido mensaje: " + mensaje);
                        refPantalla.addMensaje(usuario+ ":   " + mensaje);
                    break;
                    case 3: 
                        
                        usuario = reader.readUTF();
                        String receptor = reader.readUTF();
                        mensaje = reader.readUTF();
                        if(nombre.equals(receptor)){
                            //System.out.println("CLIENTE Recibido mensaje: " + mensaje);
                            refPantalla.addMensaje(usuario + "(mensaje privado)" + ":   " + mensaje);
                        }
                    break;                    
                    case 4: // Se inicia la partida
                        refPantalla.setInicioPartida();
                        
                        //refPantalla.seleccionFicha();
                    break;
                    case 5: // se guardan los datos de cada jugador
                        String nombreJugador = this.getRefPantalla().getNombreJugador();
                        FileManager.writeObject(this,"src/Partida/partida" + nombreJugador + ".dat");
                    break;
                    case 6: // Ataque kraken
                        usuario = reader.readUTF();
                        receptor = reader.readUTF();
                        mensaje = reader.readUTF();
                        if(refPantalla.refCliente.controlKraken == false){
                            if(refPantalla.refCliente.vivo==true){
                                refPantalla.addMensaje(usuario + " esta atacando a " + receptor + " con : " + mensaje);
                                if(nombre.equals(receptor)){
                                    ArrayList<JLabel> fichas = refPantalla.refCliente.fichas;
                                        Kraken k = new Kraken("");
                                    if(null != mensaje)switch (mensaje) {
                                        case "tentacles":
                                            k.tentacles(fichas, refPantalla);
                                            break;
                                        case "krakenBreath":
                                            k.krakenBreath(fichas, refPantalla);
                                            break;
                                        case "releaseTheKraken":
                                            k.releaseTheKraken(fichas, refPantalla);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                refPantalla.refCliente.verificarVivo();
                                if(refPantalla.refCliente.vivo == false){
                                    refPantalla.perder();
                                    refPantalla.refCliente.hiloCliente.writer.writeInt(1);
                                    refPantalla.refCliente.hiloCliente.writer.writeUTF(usuario);
                                    refPantalla.refCliente.hiloCliente.writer.writeUTF(receptor);
                                }
                            }
                            else{
                                refPantalla.refCliente.hiloCliente.writer.writeInt(2);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
                                refPantalla.refCliente.hiloCliente.writer.writeUTF(receptor);
                                refPantalla.refCliente.hiloCliente.writer.writeUTF("El jugador "+usuario+" pide dejar de ser atacado por "+ receptor+" ya que ya esta muerto, asi que por abuson pierdes  tu turno :3");
                            }
                        }
                        else{
                            refPantalla.refCliente.hiloCliente.writer.writeInt(6);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
                            refPantalla.refCliente.hiloCliente.writer.writeUTF(usuario);
                            refPantalla.refCliente.hiloCliente.writer.writeUTF(receptor);
                            refPantalla.refCliente.hiloCliente.writer.writeUTF(mensaje);
                            refPantalla.addMensaje("El control de kraken ha surtido efecto, se esta contraatacando a: " + usuario );
                            refPantalla.refCliente.controlKraken = false;
                        }
                        break;
                        case 7: // ATAQUE DE POSEIDON
                        usuario = reader.readUTF();
                        receptor = reader.readUTF();
                        mensaje = reader.readUTF();
                        if(refPantalla.refCliente.vivo==true){
                            refPantalla.addMensaje(usuario + " esta atacando a " + receptor + " con : " + mensaje);
                            if(nombre.equals(receptor)){
                                ArrayList<JLabel> fichas = refPantalla.refCliente.fichas;
                                    TheTrident t = new TheTrident("");
                                if(null != mensaje)switch (mensaje) {
                                    case "threeLines":
                                        t.threeLines(fichas, refPantalla);
                                        break;
                                    case "threeNumbers":
                                        
                                        t.threeNumbers(fichas, refPantalla, 0);
                                        break;
                                    case "controlTheKraken":
                                        refPantalla.refCliente.controlKraken = true;
                                        break;
                                    default:
                                        break;
                                }
                            }
                            refPantalla.refCliente.verificarVivo();
                            if(refPantalla.refCliente.vivo == false){
                                refPantalla.perder();
                                refPantalla.refCliente.hiloCliente.writer.writeInt(1);
                                refPantalla.refCliente.hiloCliente.writer.writeUTF(usuario);
                                refPantalla.refCliente.hiloCliente.writer.writeUTF(receptor);
                            }
                        }
                        else{
                            refPantalla.refCliente.hiloCliente.writer.writeInt(2);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
                            refPantalla.refCliente.hiloCliente.writer.writeUTF(receptor);
                            refPantalla.refCliente.hiloCliente.writer.writeUTF("El jugador "+usuario+" pide dejar de ser atacado por "+ receptor+" ya que ya esta muerto, asi que por abuson pierdes  tu turno :3");
                        }
                        break;
                        case 8: // ATAQUE DE Fish Telepathy
                        usuario = reader.readUTF();
                        receptor = reader.readUTF();
                        mensaje = reader.readUTF();
                        if(refPantalla.refCliente.vivo==true){
                            refPantalla.addMensaje(usuario + " esta atacando a " + receptor + " con : " + mensaje);
                            if(nombre.equals(receptor)){
                                ArrayList<JLabel> fichas = refPantalla.refCliente.fichas;
                                    FishTelephaty f = new FishTelephaty("");
                                if(null != mensaje)switch (mensaje) {
                                    case "cardumen":
                                        f.cardumen(fichas, refPantalla);
                                        break;
                                    case "sharkAttack":
                                        f.sharkAttack(fichas, refPantalla);
                                        break;
                                    case "pulp":
                                        f.pulp(fichas, refPantalla);
                                        break;
                                    default:
                                        break;
                                }
                            }
                            refPantalla.refCliente.verificarVivo();
                            if(refPantalla.refCliente.vivo == false){
                                refPantalla.perder();
                                refPantalla.refCliente.hiloCliente.writer.writeInt(1);
                                refPantalla.refCliente.hiloCliente.writer.writeUTF(usuario);
                                refPantalla.refCliente.hiloCliente.writer.writeUTF(receptor);
                            }
                        }
                        else{
                            refPantalla.refCliente.hiloCliente.writer.writeInt(2);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
                            refPantalla.refCliente.hiloCliente.writer.writeUTF(receptor);
                            refPantalla.refCliente.hiloCliente.writer.writeUTF("El jugador "+usuario+" pide dejar de ser atacado por "+ receptor+" ya que ya esta muerto, asi que por abuson pierdes  tu turno :3");
                        }
                        break;
                        case 9: // ATAQUE DE Undersea Fire
                        usuario = reader.readUTF();
                        receptor = reader.readUTF();
                        mensaje = reader.readUTF();
                        if(refPantalla.refCliente.vivo==true){
                            refPantalla.addMensaje(usuario + " esta atacando a " + receptor + " con : " + mensaje);
                            if(nombre.equals(receptor)){
                                ArrayList<JLabel> fichas = refPantalla.refCliente.fichas;
                                    Volcanoes v = new Volcanoes("");
                                if(null != mensaje)switch (mensaje) {
                                    case "volcanoRaising":
                                        v.volcanoRaising(fichas, refPantalla);
                                        break;
                                    case "volcanoExplotion":
                                        v.volcanoExplotion(fichas, refPantalla);
                                        break;
                                    case "thermalRush":
                                        v.thermalRush(fichas, refPantalla);
                                        break;
                                    default:
                                        break;
                                }
                            }
                            refPantalla.refCliente.verificarVivo();
                            if(refPantalla.refCliente.vivo == false){
                                refPantalla.perder();
                                refPantalla.refCliente.hiloCliente.writer.writeInt(1);
                                refPantalla.refCliente.hiloCliente.writer.writeUTF(usuario);
                                refPantalla.refCliente.hiloCliente.writer.writeUTF(receptor);
                            }
                        }
                        else{
                            refPantalla.refCliente.hiloCliente.writer.writeInt(2);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
                            refPantalla.refCliente.hiloCliente.writer.writeUTF(receptor);
                            refPantalla.refCliente.hiloCliente.writer.writeUTF("El jugador "+usuario+" pide dejar de ser atacado por "+ receptor+" ya que ya esta muerto, asi que por abuson pierdes  tu turno :3");
                        }
                        break;
                        case 10: // ATAQUE DE Thunders under the sea
                        usuario = reader.readUTF();
                        receptor = reader.readUTF();
                        mensaje = reader.readUTF();
                        if(refPantalla.refCliente.vivo==true){
                            refPantalla.addMensaje(usuario + " esta atacando a " + receptor + " con : " + mensaje);
                            if(nombre.equals(receptor)){
                                ArrayList<JLabel> fichas = refPantalla.refCliente.fichas;
                                    Thunder t = new Thunder("");
                                if(null != mensaje)switch (mensaje) {
                                    case "thunderRain":
                                        t.thunderRain(fichas, refPantalla);
                                        break;
                                    case "poseidonThunder":
                                        t.poseidonThunder(fichas, refPantalla);
                                        break;
                                    case "eelAtack":
                                        t.eelAtack(fichas, refPantalla);
                                        break;
                                    default:
                                        break;
                                }
                            }
                            refPantalla.refCliente.verificarVivo();
                            if(refPantalla.refCliente.vivo == false){
                                refPantalla.perder();
                                refPantalla.refCliente.hiloCliente.writer.writeInt(1);
                                refPantalla.refCliente.hiloCliente.writer.writeUTF(usuario);
                                refPantalla.refCliente.hiloCliente.writer.writeUTF(receptor);
                            }
                        }
                        else{
                            refPantalla.refCliente.hiloCliente.writer.writeInt(2);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
                            refPantalla.refCliente.hiloCliente.writer.writeUTF(receptor);
                            refPantalla.refCliente.hiloCliente.writer.writeUTF("El jugador "+usuario+" pide dejar de ser atacado por "+ receptor+" ya que ya esta muerto, asi que por abuson pierdes  tu turno :3");
                        }
                        break;
                        case 11: // ATAQUE DE Waves control
                        usuario = reader.readUTF();
                        receptor = reader.readUTF();
                        mensaje = reader.readUTF();
                        if(refPantalla.refCliente.vivo==true){
                            refPantalla.addMensaje(usuario + " esta atacando a " + receptor + " con : " + mensaje);
                            if(nombre.equals(receptor)){
                                ArrayList<JLabel> fichas = refPantalla.refCliente.fichas;
                                    WavesControl w = new WavesControl("");
                                if(null != mensaje)switch (mensaje) {
                                    case "swirlRaising":
                                        w.swirlRaising(fichas, refPantalla);
                                        break;
                                    case "sendHumanGarbage":
                                        w.sendHumanGarbage(fichas, refPantalla);
                                        break;
                                    case "radioactiveRush":
                                        w.radioactiveRush(fichas, refPantalla);
                                        break;
                                    default:
                                        break;
                                }
                            }
                            refPantalla.refCliente.verificarVivo();
                            if(refPantalla.refCliente.vivo == false){
                                refPantalla.perder();
                                refPantalla.refCliente.hiloCliente.writer.writeInt(1);
                                refPantalla.refCliente.hiloCliente.writer.writeUTF(usuario);
                                refPantalla.refCliente.hiloCliente.writer.writeUTF(receptor);
                            }
                        }
                        else{
                            refPantalla.refCliente.hiloCliente.writer.writeInt(2);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
                            refPantalla.refCliente.hiloCliente.writer.writeUTF(receptor);
                            refPantalla.refCliente.hiloCliente.writer.writeUTF("El jugador "+usuario+" pide dejar de ser atacado por "+ receptor+" ya que ya esta muerto, asi que por abuson pierdes  tu turno :3");
                        }
                        break;
                }
            } catch (IOException ex) {
               
        }   catch (InterruptedException ex) {
                Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
    }
    }
    
   
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public DataInputStream getReader() {
        return reader;
    }

    public void setReader(DataInputStream reader) {
        this.reader = reader;
    }

    public DataOutputStream getWriter() {
        return writer;
    }

    public void setWriter(DataOutputStream writer) {
        this.writer = writer;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public InterfazCliente getRefPantalla() {
        return refPantalla;
    }

    public void setRefPantalla(InterfazCliente refPantalla) {
        this.refPantalla = refPantalla;
    }
    
    
}
