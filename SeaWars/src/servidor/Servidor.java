/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import Cliente.InterfazCliente;
import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;


/**
 *
 * @author Usuario
 */
public class Servidor extends Thread implements Serializable{
    InterfazCliente refPantalla;
    public ArrayList<ThreadServidor> conexiones;            // Las conexiones de los jugadores de la partida al servidor
    private boolean running = true;
    transient private ServerSocket srv;
    private int turno = 0;                                  // Numero de turno de la partida
    private int limiteMax;                                  // Limite maximo de jugadores
    private boolean partidaIniciada = false;
    private boolean maximoAlcanzado = false;                // Si se ha o no alcanzado el limite maximo de jugadores de la partida
    public boolean flagCargado = false;
    public Servidor partidaGuardada;
    private int contFichas = 0;
    private boolean turnosDecididos = false;
    private ArrayList<String> nombreOrder;
    private ArrayList<String> listaPerdedores;
    int cantidadJugadores;
    

    public Servidor(InterfazCliente refPantalla, int _cantidadJugadores) {
        this.refPantalla = refPantalla;
        conexiones = new ArrayList<ThreadServidor>();
        this.refPantalla.setSrv(this);
        this.nombreOrder = new ArrayList<String>();
        this.listaPerdedores = new ArrayList<String>();
        this.cantidadJugadores = _cantidadJugadores;
    }
    
    public Servidor(InterfazCliente refPantalla, int turnoCargado, int limiteMaxCargado, Servidor servidorCargado) {
        this.refPantalla = refPantalla;
        conexiones = new ArrayList<ThreadServidor>();
        this.refPantalla.setSrv(this);
        turno = turnoCargado;
        limiteMax = limiteMaxCargado;
        flagCargado = true;
        partidaGuardada = servidorCargado;
    }

    public ArrayList<String> getListaPerdedores() {
        return listaPerdedores;
    }

    public void setListaPerdedores(ArrayList<String> listaPerdedores) {
        this.listaPerdedores = listaPerdedores;
    }
    
    

    public int getContFichas() {
        return contFichas;
    }

    public void setContFichas(int contFichas) {
        this.contFichas = contFichas;
    }

    public boolean isTurnosDecididos() {
        return turnosDecididos;
    }

    public void setTurnosDecididos(boolean turnosDecididos) {
        this.turnosDecididos = turnosDecididos;
    }


    public ArrayList<String> getNombreOrder() {
        return nombreOrder;
    }

    public void setNombreOrder(ArrayList<String> nombreOrder) {
        this.nombreOrder = nombreOrder;
    }

    
    
    public void iniciarPartida() {          // Se empieza la partida
        this.partidaIniciada = true;
        refPantalla.addMensaje("-Partida iniciada.");
    }
    
    public void guardarPartida() throws IOException {          // Para guardar la partida actual con serializable
        
        for (int i = 0; i < conexiones.size(); i++) {
            ThreadServidor current = conexiones.get(i);
            current.writer.writeInt(5);
        }
    }
    
    public void signalIniciarPartida() throws IOException{
        this.partidaIniciada = true;
        refPantalla.addMensaje("-Partida iniciada.");
        
        for (int i = 0; i < conexiones.size(); i++) {
            ThreadServidor current = conexiones.get(i);
            current.writer.writeInt(4);
        }
        
     
    }
    
    
    public void signalTerminarPartida() throws IOException{
        for (int i = 0; i < conexiones.size(); i++) {
            ThreadServidor current = conexiones.get(i);
            current.writer.writeInt(13);
        }
    }
    
    public void enviarTurnoInicial() throws IOException{            // Manda el turno inicial

        for (int i = 0; i < conexiones.size(); i++){
            ThreadServidor current = conexiones.get(i);
            current.writer.writeInt(11);
            current.writer.writeUTF(nombreOrder.get(0));
        }
        
        return;
    }
    
    public void proximoTurno(String turnoActual) throws IOException{
        String nombreTurno = "";
        System.out.println("El turnoActual de proximoTurno al comienzo es " + turnoActual);
        if (this.nombreOrder.size() == 1){
            ArrayList<String> logLeida = (ArrayList<String>)FileManager.readObject("src/Archivos/log.dat");
            this.enviarMensaje("El jugador " + nombreOrder.get(0) + " ha ganado la partida!");
            logLeida.add("El jugador " + nombreOrder.get(0) + " ha ganado la partida!\n");
            FileManager.writeObject(logLeida, "src/Archivos/log.dat");
            this.signalTerminarPartida();
            return;
        }
        
        for (int i = 0; i < nombreOrder.size(); i++) {
            if (turnoActual.contains(nombreOrder.get(i))){
                if (i + 1 >= nombreOrder.size()){
                    nombreTurno = nombreOrder.get(0);
                    break;
                }
                else
                    nombreTurno = nombreOrder.get(i+1);
            }
        }
        
        System.out.println("El nuevo turno de proximoTurno es " + nombreTurno);
        System.out.println("La lista de perdedores del servidor es " + this.getListaPerdedores());
        if (this.getListaPerdedores().contains(nombreTurno)){
            for (int i = 0; i < nombreOrder.size(); i++) {
            if (nombreTurno.equals(nombreOrder.get(i))){
                nombreOrder.remove(nombreOrder.get(i));
                if (i + 1 >= nombreOrder.size()){
                    nombreTurno = nombreOrder.get(0);
                    break;
                }
                else
                    nombreTurno = nombreOrder.get(i+1);
            }
        }
            System.out.println(nombreTurno);
        }
        
        for (int i = 0; i < conexiones.size(); i++) {
            ThreadServidor current = conexiones.get(i);
            current.writer.writeInt(11);
            current.writer.writeUTF(nombreTurno);
        }
    }
    
    public void stopserver(){
        running = false;
    }
    
    public String getNextTurno(){               // Retorna el nombre del jugador que va siguiente
        if ( ++turno >= conexiones.size())
            turno = 0;
        
        return conexiones.get(turno).nombre;
    }
    
    public String getTurno(){                   // Retorna el nombre del jugador cuyo turno es actualmente
        return conexiones.get(turno).nombre;
    }
    
    public void enviarMensaje(String msg) throws IOException{
        for (int i = 0; i < conexiones.size(); i++) {
            ThreadServidor current = conexiones.get(i);
            current.writer.writeInt(7);
            current.writer.writeUTF(msg);
        }
    }
    
    public void run(){
        int contadorDeConexiones = 0;
        
          // Primero se pide la cantidad maxima de jugadores que van a participar, de 2 minimo a 6 maximo
        
        

        this.setLimiteMax(cantidadJugadores);               // Este será el limite maximo de la cantidad de jugadores de la partida
        
        try{
            srv = new ServerSocket(35577);
            while (running){
                if (contadorDeConexiones <= this.getLimiteMax() && this.isMaximoAlcanzado() == false){          // Mientras no se ha llegado al límmite maximo, se aceptan conexiones nuevas
                    refPantalla.addMensaje("-Esperando más jugadores...");
                    refPantalla.addMensaje("-El límite máximo de jugadores para esta partida es " + cantidadJugadores + ". Cantidad actual de jugadores: " + contadorDeConexiones);
                    
                }
                    
                Socket nuevaConexion = srv.accept();
                if (!partidaIniciada){              // Mientras no se empezado la partida, se aceptan conexiones nuevas
                    contadorDeConexiones++;

                    if (contadorDeConexiones > this.getLimiteMax()){
                        refPantalla.addMensaje("-Conexión denegada: Límite máximo de jugadores alcanzado.");
                        
                        
                    }
                    
                    else if (contadorDeConexiones <= this.getLimiteMax()){
                        
                        refPantalla.addMensaje("-Conexión " + contadorDeConexiones + " aceptada.");
                        
                        // nuevo thread
                        ThreadServidor newThread = new ThreadServidor(nuevaConexion, this);
                        conexiones.add(newThread);
                        newThread.start();

                    }
                        
                        if (contadorDeConexiones == this.getLimiteMax()){           // Al llegar al límite máximo de jugadores, se para de aceptar nuevas conexiones y se empiza la partida
                            
                            refPantalla.addMensaje("-El límite máximo de jugadores para esta partida es " + limiteMax + ". Cantidad actual de jugadores: " + contadorDeConexiones);
                            refPantalla.addMensaje("-Cantidad máxima de jugadores alcanzada. No se permitirán más conexiones.");
                            refPantalla.addMensaje("-Iniciando partida...");
                            this.setMaximoAlcanzado(true);
                            srv.close();
                            this.signalIniciarPartida();

                        }
                    
                }
                else{
                    // OutputStream socket para poder hacer un writer
                    refPantalla.addMensaje("-Conexión denegada: partida iniciada");
                    
                }
                
                if (partidaIniciada){
                    
                }
                
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public InterfazCliente getRefPantalla() {
        return refPantalla;
    }

    public void setRefPantalla(InterfazCliente refPantalla) {
        this.refPantalla = refPantalla;
    }

    public boolean isFlagCargado() {
        return flagCargado;
    }

    public void setFlagCargado(boolean flagCargado) {
        this.flagCargado = flagCargado;
    }

    public ArrayList<ThreadServidor> getConexiones() {
        return conexiones;
    }

    public void setConexiones(ArrayList<ThreadServidor> conexiones) {
        this.conexiones = conexiones;
    }
    
    public int getLimiteMax() {
        return limiteMax;
    }

    public void setLimiteMax(int limiteMax) {
        this.limiteMax = limiteMax;
    }

    public boolean isMaximoAlcanzado() {
        return maximoAlcanzado;
    }

    public void setMaximoAlcanzado(boolean maximoAlcanzado) {
        this.maximoAlcanzado = maximoAlcanzado;
    }

    public boolean isPartidaIniciada() {
        return partidaIniciada;
    }

    public void setPartidaIniciada(boolean partidaIniciada) {
        this.partidaIniciada = partidaIniciada;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setTurno(int turno) {
        this.turno = turno;
    }
    
    public int getNumTurno(){
        return turno;
    }
    
}
