/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Personaje.Personaje;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.IconUIResource;
import servidor.Servidor;

/**
 *
 * @author Usuario
 */
public class InterfazCliente extends javax.swing.JFrame {
    
    private Servidor srv;
    private ArrayList<JLabel> labels = new ArrayList();
    public JLabel[][] labelMatrix = new JLabel[20][30];
    public boolean vivo = true;
    
    public InterfazCliente() {
        initComponents();
        agregarLbl();
        
    }
    public Cliente refCliente;           // Nombre del jugador cuyo turno es actualmente
    private String nombreJugador = "";
    
    
    public String newServer(String stringCantidad){
        int cantidadJugadores = 0;
        do{
           
            
        
        try{
            cantidadJugadores = Integer.parseInt(stringCantidad);
            
        } catch(NumberFormatException e){
            return "La cantidad debe ser un entero.";
        }
        
        if (cantidadJugadores < 2)
            return "La cantidad mínima de jugadores es 2.";
        
        else if (cantidadJugadores > 6)
            return "La cantidad máxima de jugadores es 6.";
            
        } while (cantidadJugadores < 2 || cantidadJugadores > 6);
        Servidor srv = new Servidor(this, cantidadJugadores);
        setTitle("Servidor Sea War");
        srv.start();
        return "Servidor creado correctamente";
    }
    
    public void setSrv(Servidor srv) {
        this.srv = srv;
    }
    
    
    public void newPlayer(String nombre){
        try{
        Cliente c = new Cliente(this);
        this.pack();
        this.setVisible(true); 
        c.conectar(nombre);
               
        }
        catch(Exception e){
            
        }
    }
    
    public void loadPlayer(ThreadCliente jugadorCargado, String nombre){
        try{
        Cliente c = new Cliente(this);
        pack();
        setVisible(true); 
        c.conectarLoad(jugadorCargado, nombre);
        }
        catch(Exception e){
            
        }
    }
    
    public static boolean isNumeric(String strNum) {
    if (strNum == null) {
        return false;
    }
    try {
        int i = Integer.parseInt(strNum);
        float f = Float.parseFloat(strNum);
        double d = Double.parseDouble(strNum);
        long l = Long.parseLong(strNum);
        
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
}
    
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    
    public Cliente getRefCliente() {
        return refCliente;
    }

    public void setRefCliente(Cliente refCliente) {
        this.refCliente = refCliente;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }
    
    private void agregarLbl(){
        int y = 0;
        JLabel [][] labelMatrix = new JLabel[20][30];
        System.out.println(labelMatrix.length);
        for(int i = 0; i < labelMatrix.length; i++){
            int x = 0;
            System.out.println(labelMatrix[i].length);
            for(int j = 0; j < labelMatrix[i].length; j++){
                labelMatrix[i][j] = new javax.swing.JLabel();
                labelMatrix[i][j].setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
                labels.add(labelMatrix[i][j]);
                labelsPanel.add(labelMatrix[i][j]);
                labelMatrix[i][j].setBounds(x, y, 20, 20);
                x += 20;
            }
            y += 20;
        }
        this.labelMatrix = labelMatrix;
    }
    
    public void crearPantalla(){
        ArrayList<Icon> iconos = new ArrayList<>();
        iconos.add(new ImageIcon(getClass().getResource("/Imagenes/azul.png")));
        iconos.add(new ImageIcon(getClass().getResource("/Imagenes/gris.png")));
        iconos.add(new ImageIcon(getClass().getResource("/Imagenes/anaranjado.png")));
        int cont = 0;
        
        int pos;
        int nCartas = 600;
        Stack < Integer > pCartas = new Stack < Integer > ();
        for (int i = 0; i < nCartas ; i++) {
          pos = (int) Math.floor(Math.random() * nCartas );
          while (pCartas.contains(pos)) {
            pos = (int) Math.floor(Math.random() * nCartas );
          }
          pCartas.push(pos);
        }
        
        for(int i=0; i < refCliente.personajes.size(); i++) {
            Personaje personaje = refCliente.personajes.get(i);
            double cantidad = 600*personaje.getPorcentaje();
            for(int j = 0; j < cantidad;j++){
                labels.get(pCartas.get(cont)).setIcon(iconos.get(i));
                labels.get(pCartas.get(cont)).setText("100");
                refCliente.personajes.get(i).agregarFichas(labels.get(j));
                refCliente.fichas.add(refCliente.personajes.get(i).fichas.get(j));
                cont++;
             }
            
        }
    }

    public boolean verificarVivo(){
        if(refCliente != null){
            return refCliente.vivo;
        }
        return true;
    }                                                                                                  
    
    public void imprimirTodo(ArrayList<JLabel> lbls){
        for(int i = 0;i < lbls.size();i++){
            System.out.println(lbls.get(i) + " tiene vida de: "+lbls.get(i).getText()+" posi "+i);
        }
    }
    
    /**
     * Creates new form InterfazCliente
     */
    public void setInicioPartida(){             // Configura la partida con los datos iniciales para empezarla
        commandTextArea.setText("Partida Iniciada");
      //  lblNumCasas.setText("Numero de casas: " + refCliente.getHiloCliente().getNumCasas());
     //   lblNumHoteles.setText("Numero de hoteles: " + refCliente.getHiloCliente().getNumHoteles());
     //   lblNumPropiedades.setText("Numero de propiedades: " + refCliente.getHiloCliente().getNumPropiedades());
    }
    
    public void addMensaje(String msj)              // Para agregar un mensaje en la interfaz del cliente
    {
        commandTextArea.append(msj + "\n");
    }   

    
    public String atacar(String[] separado) throws IOException{
        boolean flag = false;
        if(separado.length != 4){
            return "Comando invalido";
        }
        else{
            if("Kraken".equals(separado[1])){
                for(int i =0; i < 3; i++){
                    if("Kraken".equals(refCliente.personajes.get(i).ataque.getNombre()))
                        flag = true;
                }
                if(flag){
                    if("tentacles".equals(separado[2]) || "krakenBreath".equals(separado[2]) || "releaseTheKraken".equals(separado[2])) {
                        
                            refCliente.hiloCliente.writer.writeInt(6);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
                            refCliente.hiloCliente.writer.writeUTF(separado[3]);
                            refCliente.hiloCliente.writer.writeUTF(lblNombreJugador.getText());
                            refCliente.hiloCliente.writer.writeUTF(separado[2]);
                            return "Ataque realizado con exito";
                    }
                }
                else{
                    return "Usted no tiene al personaje del Kraken";
                }
            }
            else if("TheTrident".equals(separado[1])){
                for(int i =0; i < 3; i++){
                    if("TheTrident".equals(refCliente.personajes.get(i).ataque.getNombre()))
                        flag = true;
                }
                if(flag){
                    if("threeLines".equals(separado[2]) || "threeNumbers".equals(separado[2]) || "controlTheKraken".equals(separado[2])) {
                        
                            refCliente.hiloCliente.writer.writeInt(7);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
                            refCliente.hiloCliente.writer.writeUTF(separado[3]);
                            refCliente.hiloCliente.writer.writeUTF(lblNombreJugador.getText());
                            refCliente.hiloCliente.writer.writeUTF(separado[2]);
                            return "Ataque realizado con exito";
                    }
                }
                else{
                    return "Usted no tiene al personaje del The trident";
                }
            }
            else if("FishTelephaty".equals(separado[1])){
                for(int i =0; i < 3; i++){
                    if("FishTelephaty".equals(refCliente.personajes.get(i).ataque.getNombre()))
                        flag = true;
                }
                if(flag){
                    if("cardumen".equals(separado[2]) || "sharkAttack".equals(separado[2]) || "pulp".equals(separado[2])) {
                        
                            refCliente.hiloCliente.writer.writeInt(8);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
                            refCliente.hiloCliente.writer.writeUTF(separado[3]);
                            refCliente.hiloCliente.writer.writeUTF(lblNombreJugador.getText());
                            refCliente.hiloCliente.writer.writeUTF(separado[2]);
                            return "Ataque realizado con exito";
                    }
                }
                else{
                    return "Usted no tiene al personaje del The trident";
                }
            }
            else if("Volcanoes".equals(separado[1])){
                for(int i =0; i < 3; i++){
                    if("Volcanoes".equals(refCliente.personajes.get(i).ataque.getNombre()))
                        flag = true;
                }
                if(flag){
                    if("volcanoRaising".equals(separado[2]) || "volcanoExplotion".equals(separado[2]) || "thermalRush".equals(separado[2])) {
                        
                            refCliente.hiloCliente.writer.writeInt(9);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
                            refCliente.hiloCliente.writer.writeUTF(separado[3]);
                            refCliente.hiloCliente.writer.writeUTF(lblNombreJugador.getText());
                            refCliente.hiloCliente.writer.writeUTF(separado[2]);
                            return "Ataque realizado con exito";
                    }
                }
                else{
                    return "Usted no tiene al personaje del The trident";
                }
            }
        }
        return "No se pudo realizar el ataque";
    }
    
    public void perder(){
        lblNombreJugador.setText(lblNombreJugador.getText() + " (eliminado)");
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        generalPanel = new javax.swing.JPanel();
        lblNombreJugador = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        commandTextArea = new javax.swing.JTextArea();
        commandTextField = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        labelsPanels = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        labelsPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textArea2.setColumns(20);
        textArea2.setRows(5);
        jScrollPane1.setViewportView(textArea2);

        commandTextArea.setColumns(20);
        commandTextArea.setRows(5);
        jScrollPane3.setViewportView(commandTextArea);

        commandTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandTextFieldActionPerformed(evt);
            }
        });
        commandTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                commandTextFieldKeyPressed(evt);
            }
        });

        jButton4.setText("jButton4");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        labelsPanels.setLayout(null);

        labelsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout labelsPanelLayout = new javax.swing.GroupLayout(labelsPanel);
        labelsPanel.setLayout(labelsPanelLayout);
        labelsPanelLayout.setHorizontalGroup(
            labelsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 598, Short.MAX_VALUE)
        );
        labelsPanelLayout.setVerticalGroup(
            labelsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 398, Short.MAX_VALUE)
        );

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("jButton3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout generalPanelLayout = new javax.swing.GroupLayout(generalPanel);
        generalPanel.setLayout(generalPanelLayout);
        generalPanelLayout.setHorizontalGroup(
            generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(82, 82, 82)
                                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton2)
                                    .addComponent(jButton4)
                                    .addComponent(jButton1)
                                    .addComponent(jButton3)))
                            .addComponent(commandTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(67, 67, 67)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addComponent(lblNombreJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(labelsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(labelsPanels, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );
        generalPanelLayout.setVerticalGroup(
            generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalPanelLayout.createSequentialGroup()
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelsPanels, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addComponent(lblNombreJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(191, 191, 191))
                            .addComponent(labelsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 8, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generalPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generalPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(commandTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generalPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(17, 17, 17)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addGap(102, 102, 102))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(generalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(generalPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String i = "crearPersonaje-Zeus-1-100-100-100-Kraken-35";
        String d = "crearPersonaje-Zeus-1-75-75-75-Volcanoes-46";
        String f = "crearPersonaje-Zeus-1-50-50-50-FishTelephaty-19";
        String[] a = i.split("-");
        String[] b = d.split("-");
        String[] c = f.split("-");
        addMensaje(refCliente.crearPersonajes(a));
        addMensaje(refCliente.crearPersonajes(b));
        addMensaje(refCliente.crearPersonajes(c));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        crearPantalla();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void commandTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandTextFieldActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_commandTextFieldActionPerformed

    private void commandTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_commandTextFieldKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            this.vivo = verificarVivo();
            
            String command = commandTextField.getText();
            String[] separado = command.split("-");// Crear Personajes        ataque-Kraken-tentacles-davi
            if(null == separado[0]){
                addMensaje("Error, el comando ingresado es inexistente");
            }
            else switch (separado[0]) {
                case "crearPersonaje":
                    addMensaje(refCliente.crearPersonajes(separado));
                    break;
                case "crearServidor":
                    // Iniciar Servidor
                    if(separado.length == 2){
                        addMensaje(this.newServer(separado[1]));
                    }
                    else{
                        addMensaje("El comando no esta ingresado correctamente.");
                    }
                    break;
                case "nuevaConexion":
                    if(separado.length == 2){
                        String nombre = separado[1];
                        this.newPlayer(nombre);
                        lblNombreJugador.setText(nombre);
                    }
                    else{
                        addMensaje("El comando no esta ingresado correctamente.");
                    }
                    break;
                case "chatGeneral":
                    // Chat general
                    try {

                        // TODO add your handling code here:
                        refCliente.hiloCliente.writer.writeInt(2);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
                        refCliente.hiloCliente.writer.writeUTF(lblNombreJugador.getText());
                        refCliente.hiloCliente.writer.writeUTF(separado[1]);
                    } catch (IOException ex) {

                    }   break;
                case "chatPrivado":
                    if(separado.length == 3){
                        try {
                            // TODO add your handling code here:
                            refCliente.hiloCliente.writer.writeInt(3);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
                            refCliente.hiloCliente.writer.writeUTF(lblNombreJugador.getText());
                            refCliente.hiloCliente.writer.writeUTF(separado[1]);
                            refCliente.hiloCliente.writer.writeUTF(separado[2]);
                        } catch (IOException ex) {

                        }
                    }
                    else{
                        addMensaje("Ha ocurrido un error con el envio del mensaje");
                    }   break;
                case "ataque":
                    if(this.vivo){
                        try {

                            addMensaje(atacar(separado));
                        } catch (IOException ex) {
                            Logger.getLogger(InterfazCliente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else{
                        addMensaje("Usted esta muerto, buena suerte en la proxima");
                    }
                 break;   
                default:
                    addMensaje("Error, el comando ingresado es inexistente");
                    break;             
                }
                commandTextField.setText(""); 
        }
    }//GEN-LAST:event_commandTextFieldKeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            String command = "ataque-Volcanoes-volcanoExplotion-fer";
            String[] separado = command.split("-");// Crear Personajes
            addMensaje(atacar(separado));
        } catch (IOException ex) {
            Logger.getLogger(InterfazCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        for(int i = 0;i < labels.size();i++){
            System.out.println(labels.get(i) + " tiene vida de: "+labels.get(i).getText()+" posi "+i);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfazCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea commandTextArea;
    private javax.swing.JTextField commandTextField;
    private javax.swing.JPanel generalPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel labelsPanel;
    private javax.swing.JPanel labelsPanels;
    private javax.swing.JLabel lblNombreJugador;
    private javax.swing.JTextArea textArea2;
    // End of variables declaration//GEN-END:variables
}
