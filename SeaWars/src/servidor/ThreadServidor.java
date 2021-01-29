/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class ThreadServidor extends Thread implements Serializable {
    transient private Socket socketRef;
    protected DataInputStream reader;
    protected DataOutputStream writer;
    protected ObjectOutputStream objWriter;
    public String nombre;
    private boolean running = true;
    Servidor server;

    public ThreadServidor(Socket socketRef, Servidor server) throws IOException {
        this.socketRef = socketRef;
        reader = new DataInputStream(socketRef.getInputStream());
        writer = new DataOutputStream(socketRef.getOutputStream());
        objWriter = new ObjectOutputStream(socketRef.getOutputStream());
        this.server = server;
    }
    
    public void enviarTurnoInicial() throws IOException{            // Manda el turno inicial
        writer.writeInt(1);
        writer.writeUTF(server.getTurno());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Servidor getServer() {
        return server;
    }

    public void setServer(Servidor server) {
        this.server = server;
    }
    
    
    
    public void run (){
        
        int instruccionId = 1;
        while (running){
            try {
                instruccionId = reader.readInt(); // esperar hasta que reciba un entero
                
                switch (instruccionId){
                    case 1: // verificarVivo
                           String vivo = reader.readUTF();
                           String muerto = reader.readUTF();
                            for (int i = 0; i < server.conexiones.size(); i++) {
                                 ThreadServidor current = server.conexiones.get(i);
                                 current.writer.writeInt(1);
                                 current.writer.writeUTF(vivo);
                                 current.writer.writeUTF(muerto);
                             }
                                      
                    break;
                    case 2: // pasan un mensaje por el chat
                        String usuario = reader.readUTF();
                        String mensaje = reader.readUTF();
                        
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(2);
                            current.writer.writeUTF(usuario);
                            current.writer.writeUTF(mensaje);
                        }
                    break;
                    case 3:
                        usuario = reader.readUTF();
                        String receptor = reader.readUTF();
                        mensaje = reader.readUTF();
                        for (int i = 0; i < server.conexiones.size(); i++) {
                                ThreadServidor current = server.conexiones.get(i);
                                current.writer.writeInt(3);
                                current.writer.writeUTF(usuario);
                                current.writer.writeUTF(receptor);
                                current.writer.writeUTF(mensaje);
                        }
                       
                    break;
                    case 4: // iniciar partida
                        // al iniciar la partida se deberían tirar los dados para determinar el orden
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(4);
                        }
                        
                        
                    break;
                    case 5:
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(5);
                        }
                        break;
                    case 6:// ATAQUE DE KRAKEN  
                        String atacado = reader.readUTF();
                        String atacante = reader.readUTF();
                        mensaje = reader.readUTF();
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(6);
                            current.writer.writeUTF(atacante);
                            current.writer.writeUTF(atacado);
                            current.writer.writeUTF(mensaje);
                        }
                    break;
                    case 7:// ATAQUE DE POSEIDON
                        atacado = reader.readUTF();
                        atacante = reader.readUTF();
                        mensaje = reader.readUTF();
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(7);
                            current.writer.writeUTF(atacante);
                            current.writer.writeUTF(atacado);
                            current.writer.writeUTF(mensaje);
                        }
                    break;
                    case 8: // ATAQUE DE Fish Telepathy
                        atacado = reader.readUTF();
                        atacante = reader.readUTF();
                        mensaje = reader.readUTF();
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(8);
                            current.writer.writeUTF(atacante);
                            current.writer.writeUTF(atacado);
                            current.writer.writeUTF(mensaje);
                        }
                    break;
                    case 9: // ATAQUE DE Undersea Fire
                        atacado = reader.readUTF();
                        atacante = reader.readUTF();
                        mensaje = reader.readUTF();
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(9);
                            current.writer.writeUTF(atacante);
                            current.writer.writeUTF(atacado);
                            current.writer.writeUTF(mensaje);
                        }
                    break;
                    case 10: // ATAQUE DE Thunders under the sea
                        atacado = reader.readUTF();
                        atacante = reader.readUTF();
                        mensaje = reader.readUTF();
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(10);
                            current.writer.writeUTF(atacante);
                            current.writer.writeUTF(atacado);
                            current.writer.writeUTF(mensaje);
                        }
                    break;
                    case 11: // ATAQUE DE Waves control
                        atacado = reader.readUTF();
                        atacante = reader.readUTF();
                        mensaje = reader.readUTF();
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(11);
                            current.writer.writeUTF(atacante);
                            current.writer.writeUTF(atacado);
                            current.writer.writeUTF(mensaje);
                        }
                    break;
                    case 12: // Verificar estado de jugador
                        atacante = reader.readUTF();
                        atacado = reader.readUTF();
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(12);
                            current.writer.writeUTF(atacante);
                            current.writer.writeUTF(atacado);
                        }
                    break;
                    case 13: // Verificar estado de jugador
                        atacante = reader.readUTF();
                        atacado = reader.readUTF();
                        mensaje = reader.readUTF();
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(13);
                            current.writer.writeUTF(atacante);
                            current.writer.writeUTF(atacado);
                            current.writer.writeUTF(mensaje);
                        }
                    break;
                    case 14: // Verificar estado de jugador
                        String turnoActual = reader.readUTF();  // Para cambiar de turno
                        this.server.proximoTurno(turnoActual);
                        
                    break;
                }
                
                
                       
                
            } catch (IOException ex) {
                
            }
        }
    }
}
