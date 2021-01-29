/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Personaje.Personaje;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
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
    public Cliente refCliente;           // Nombre del jugador cuyo turno es actualmente
    private String nombreJugador = "";
    
    public InterfazCliente() {
        initComponents();
        
        agregarLbl();
        
    }
    
    
    
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
    
    public void revisarCasillas(){
        for(int i = 0;i < labels.size();i++){
            addMensaje(labels.get(i) + " tiene vida de: "+labels.get(i).getText()+" posicion " + i);
        }
    }
    
    public String revisarEnvio(){
        String mensaje = "";
        for(int i = 0;i < labels.size();i++){
            mensaje += labels.get(i) + " tiene vida de: "+labels.get(i).getText()+" posicion " + i;
        }
        return mensaje;
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
    
    public void verificarLabels(){
        int muertas;
        double porc;
        double total;
        for(int i = 0;i < 3;i++){
            Personaje perso = refCliente.personajes.get(i);
            if(i == 0){
                muertas = perso.casillasMuertas();
                porc = (perso.getPorcentaje()*100);
                total = 600*perso.getPorcentaje();
                casillas1.setText(muertas+" de "+total+" casillas");
                porcentajePerso1.setText((muertas/total*100)+"");
            }
            else if(i == 1){
                muertas = perso.casillasMuertas();
                porc = (perso.getPorcentaje()*100);
                total = 600*perso.getPorcentaje();
                casillas2.setText(muertas+" de "+total+" casillas");
                porcentajePerso2.setText((muertas/total*100)+"");
            }
            else if(i == 2){
                muertas = perso.casillasMuertas();
                porc = (perso.getPorcentaje()*100);
                total = 600*perso.getPorcentaje();
                casillas3.setText(muertas+" de "+total+" casillas");
                porcentajePerso3.setText((muertas/total*100)+"");
            }
        }
    }
    
    private void acomodarLabelsPersonajes(){
        for(int i = 0;i < 3;i++){
            Personaje perso = refCliente.personajes.get(i);
            if(i == 0){
                darImagen(imagen_lbl1, perso.imagen);
                nombre_lbl1.setText(perso.getNombre());
                poder_lbl1.setText(perso.getAtaque().getNombre());
                porcentaje_lbl1.setText((perso.getPorcentaje()*100)+"");
                num_poder1.setText(perso.getPoder()+"");
                resis_num1.setText(perso.getResistencia()+"");
                sanidad_num1.setText(perso.getSanidad()+"");
                lbl_vida1.setText(perso.getNombre());
                porcentajePerso1.setText((perso.getPorcentaje()*100)+" %");
                casillas1.setText((600*perso.getPorcentaje())+" de "+(600*perso.getPorcentaje())+" casillas ");
            }
            else if(i == 1){
                darImagen(imagen_lbl2, perso.imagen);
                nombre_lbl2.setText(perso.getNombre());
                poder_lbl2.setText(perso.getAtaque().getNombre());
                porcentaje_lbl2.setText((perso.getPorcentaje()*100)+"");
                num_poder2.setText(perso.getPoder()+"");
                resis_num2.setText(perso.getResistencia()+"");
                sanidad_num2.setText(perso.getSanidad()+"");
                lbl_vida2.setText(perso.getNombre());
                porcentajePerso2.setText((perso.getPorcentaje()*100)+" %");
                casillas2.setText((600*perso.getPorcentaje())+" de "+(600*perso.getPorcentaje())+" casillas ");
            }
            else if(i == 2){
                darImagen(imagen_lbl3, perso.imagen);
                nombre_lbl3.setText(perso.getNombre());
                poder_lbl3.setText(perso.getAtaque().getNombre());
                porcentaje_lbl3.setText((perso.getPorcentaje()*100)+"");
                num_poder3.setText(perso.getPoder()+"");
                resis_num3.setText(perso.getResistencia()+"");
                sanidad_num3.setText(perso.getSanidad()+"");
                lbl_vida3.setText(perso.getNombre());
                porcentajePerso3.setText((perso.getPorcentaje()*100)+" %");
                casillas3.setText((600*perso.getPorcentaje())+" de "+(600*perso.getPorcentaje())+" casillas ");
            }
            
            
            
        }
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
        acomodarLabelsPersonajes();
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

    
    public void revisarCasillaEspecific(String[] separado){
        if(separado.length != 3){
            addMensaje("Comando ingresado en formato incorrecto");
        }
        else if(isNumeric(separado[1]) && isNumeric(separado[2])){
            addMensaje(labelMatrix[Integer.parseInt(separado[2])][Integer.parseInt(separado[1])] + " tiene vida de: "+labelMatrix[Integer.parseInt(separado[2])][Integer.parseInt(separado[1])].getText());
        }
    }
    
    public void darImagen(JLabel label, String url){
        ImageIcon imagenIcono = new ImageIcon(url);
        Image imagenCambiar = imagenIcono.getImage();
        Image cambiada = imagenCambiar.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT);
        ImageIcon definitiva = new ImageIcon(cambiada);
        label.setIcon(definitiva);
    }
    
    public void enviarStatus(String atacado){
        try {
            String mensaje = revisarEnvio();
            refCliente.hiloCliente.writer.writeInt(13);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
            refCliente.hiloCliente.writer.writeUTF(lblNombreJugador.getText());
            refCliente.hiloCliente.writer.writeUTF(atacado);
            refCliente.hiloCliente.writer.writeUTF(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(InterfazCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String atacar(String[] separado) throws IOException{
        boolean flag = false;
        if(separado.length != 4){
            return "Comando invalido";
        }
        else{
            if(null != separado[1])switch (separado[1]) {
                case "Kraken":
                    for(int i =0; i < 3; i++){
                        if("Kraken".equals(refCliente.personajes.get(i).ataque.getNombre()))
                            flag = true;
                    }   if(flag){
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
                    }   break;
                case "TheTrident":
                    for(int i =0; i < 3; i++){
                        if("TheTrident".equals(refCliente.personajes.get(i).ataque.getNombre()))
                            flag = true;
                    }   if(flag){
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
                    }   break;
                case "FishTelephaty":
                    for(int i =0; i < 3; i++){
                        if("FishTelephaty".equals(refCliente.personajes.get(i).ataque.getNombre()))
                            flag = true;
                    }   if(flag){
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
                    }   break;
                case "Volcanoes":
                    for(int i =0; i < 3; i++){
                        if("Volcanoes".equals(refCliente.personajes.get(i).ataque.getNombre()))
                            flag = true;
                    }   if(flag){
                        if("volcanoRaising".equals(separado[2]) || "volcanoExplotion".equals(separado[2]) || "thermalRush".equals(separado[2])) {
                            
                            refCliente.hiloCliente.writer.writeInt(9);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
                            refCliente.hiloCliente.writer.writeUTF(separado[3]);
                            refCliente.hiloCliente.writer.writeUTF(lblNombreJugador.getText());
                            refCliente.hiloCliente.writer.writeUTF(separado[2]);
                            return "Ataque realizado con exito";
                        }
                    }
                    else{
                        return "Usted no tiene al personaje del Volcanoes";
                    }   break;
                case "Thunder":
                    for(int i =0; i < 3; i++){
                        if("Thunder".equals(refCliente.personajes.get(i).ataque.getNombre()))
                            flag = true;
                    }   if(flag){
                        if("thunderRain".equals(separado[2]) || "poseidonThunder".equals(separado[2]) || "eelAtack".equals(separado[2])) {
                            
                            refCliente.hiloCliente.writer.writeInt(10);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
                            refCliente.hiloCliente.writer.writeUTF(separado[3]);
                            refCliente.hiloCliente.writer.writeUTF(lblNombreJugador.getText());
                            refCliente.hiloCliente.writer.writeUTF(separado[2]);
                            return "Ataque realizado con exito";
                        }
                    }
                    else{
                        return "Usted no tiene al personaje del ThunderUnderTheSea";
                    }   break;
                case "WavesControl":
                    for(int i =0; i < 3; i++){
                        if("Thunder".equals(refCliente.personajes.get(i).ataque.getNombre()))
                            flag = true;
                    }   if(flag){
                        if("swirlRaising".equals(separado[2]) || "sendHumanGarbage".equals(separado[2]) || "radioactiveRush".equals(separado[2])) {
                            
                            refCliente.hiloCliente.writer.writeInt(10);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
                            refCliente.hiloCliente.writer.writeUTF(separado[3]);
                            refCliente.hiloCliente.writer.writeUTF(lblNombreJugador.getText());
                            refCliente.hiloCliente.writer.writeUTF(separado[2]);
                            return "Ataque realizado con exito";
                        }
                    }
                    else{
                        return "Usted no tiene al personaje del WavesControl";
                    }   break;
                default:
                    break;
            }
            
        }
        return "No se pudo realizar el ataque";
    }
    //Thunder
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

        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        generalPanel = new javax.swing.JPanel();
        lblNombreJugador = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        commandTextArea = new javax.swing.JTextArea();
        commandTextField = new javax.swing.JTextField();
        labelsPanels = new javax.swing.JPanel();
        labelsPanel = new javax.swing.JPanel();
        imagen_lbl1 = new javax.swing.JLabel();
        nombre_lbl1 = new javax.swing.JLabel();
        poder_lbl1 = new javax.swing.JLabel();
        porcentaje_lbl1 = new javax.swing.JLabel();
        poder_1 = new javax.swing.JLabel();
        num_poder1 = new javax.swing.JLabel();
        resistencia_lbl1 = new javax.swing.JLabel();
        resis_num1 = new javax.swing.JLabel();
        sanidad_lbl1 = new javax.swing.JLabel();
        sanidad_num1 = new javax.swing.JLabel();
        nombre_lbl2 = new javax.swing.JLabel();
        poder_lbl2 = new javax.swing.JLabel();
        porcentaje_lbl2 = new javax.swing.JLabel();
        poder_2 = new javax.swing.JLabel();
        num_poder2 = new javax.swing.JLabel();
        resistencia_lbl2 = new javax.swing.JLabel();
        resis_num2 = new javax.swing.JLabel();
        sanidad_lbl2 = new javax.swing.JLabel();
        imagen_lbl2 = new javax.swing.JLabel();
        sanidad_num2 = new javax.swing.JLabel();
        nombre_lbl3 = new javax.swing.JLabel();
        poder_lbl3 = new javax.swing.JLabel();
        porcentaje_lbl3 = new javax.swing.JLabel();
        poder_3 = new javax.swing.JLabel();
        num_poder3 = new javax.swing.JLabel();
        resistencia_lbl3 = new javax.swing.JLabel();
        resis_num3 = new javax.swing.JLabel();
        sanidad_lbl3 = new javax.swing.JLabel();
        imagen_lbl3 = new javax.swing.JLabel();
        sanidad_num3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbl_vida1 = new javax.swing.JLabel();
        lbl_vida2 = new javax.swing.JLabel();
        lbl_vida3 = new javax.swing.JLabel();
        porcentajePerso1 = new javax.swing.JLabel();
        porcentajePerso2 = new javax.swing.JLabel();
        porcentajePerso3 = new javax.swing.JLabel();
        casillas1 = new javax.swing.JLabel();
        casillas2 = new javax.swing.JLabel();
        casillas3 = new javax.swing.JLabel();
        lbl_turnos = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbl_turno = new javax.swing.JLabel();

        jLabel23.setText("jLabel4");

        jLabel24.setText("jLabel5");

        jLabel25.setText("jLabel6");

        jLabel26.setText("jLabel7");

        jLabel27.setText("jLabel8");

        jLabel28.setText("jLabel9");

        jLabel29.setText("jLabel10");

        jLabel30.setText("jLabel11");

        jLabel31.setText("jLabel2");

        jLabel32.setText("jLabel12");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

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

        poder_1.setText("Poder:");

        resistencia_lbl1.setText("Resis:");

        sanidad_lbl1.setText("Sanidad:");

        poder_2.setText("Poder:");

        resistencia_lbl2.setText("Resis:");

        sanidad_lbl2.setText("Sanidad:");

        poder_3.setText("Poder:");

        resistencia_lbl3.setText("Resis:");

        sanidad_lbl3.setText("Sanidad:");

        jLabel1.setText("Vida: ");

        jLabel2.setText("100 %");

        jLabel3.setText("Casiilas destruidas");

        jLabel4.setText("0 ");

        jLabel5.setText("Turno de:");

        javax.swing.GroupLayout generalPanelLayout = new javax.swing.GroupLayout(generalPanel);
        generalPanel.setLayout(generalPanelLayout);
        generalPanelLayout.setHorizontalGroup(
            generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generalPanelLayout.createSequentialGroup()
                .addComponent(lbl_turnos)
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_turno, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNombreJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_vida1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(porcentajePerso1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(casillas1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(labelsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(commandTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(poder_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nombre_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(porcentaje_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addComponent(poder_1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(num_poder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addComponent(resistencia_lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resis_num1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addComponent(sanidad_lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sanidad_num1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(imagen_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(poder_lbl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nombre_lbl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(porcentaje_lbl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addComponent(poder_2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(num_poder2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addComponent(resistencia_lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resis_num2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addComponent(sanidad_lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sanidad_num2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(imagen_lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(poder_lbl3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nombre_lbl3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(porcentaje_lbl3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addComponent(poder_3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(num_poder3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addComponent(resistencia_lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resis_num3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(imagen_lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addComponent(sanidad_lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sanidad_num3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(labelsPanels, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(generalPanelLayout.createSequentialGroup()
                                    .addComponent(porcentajePerso2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(porcentajePerso3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, generalPanelLayout.createSequentialGroup()
                                    .addComponent(lbl_vida2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(60, 60, 60)
                                    .addComponent(lbl_vida3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addComponent(casillas2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(casillas3, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        generalPanelLayout.setVerticalGroup(
            generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(generalPanelLayout.createSequentialGroup()
                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(generalPanelLayout.createSequentialGroup()
                                        .addComponent(imagen_lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(nombre_lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(poder_lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(porcentaje_lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(poder_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(num_poder2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(resistencia_lbl2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(resis_num2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(sanidad_lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(sanidad_num2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(generalPanelLayout.createSequentialGroup()
                                        .addComponent(imagen_lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(nombre_lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(poder_lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(porcentaje_lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(poder_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(num_poder1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(resistencia_lbl1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(resis_num1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(sanidad_lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(sanidad_num1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generalPanelLayout.createSequentialGroup()
                                        .addComponent(imagen_lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(nombre_lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(poder_lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(porcentaje_lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(poder_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(num_poder3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(resistencia_lbl3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(resis_num3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(sanidad_lbl3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(sanidad_num3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, generalPanelLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl_vida1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_vida2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_vida3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(porcentajePerso3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(porcentajePerso2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(porcentajePerso1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(casillas1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(casillas2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(casillas3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(generalPanelLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(generalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(generalPanelLayout.createSequentialGroup()
                                .addComponent(lblNombreJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_turno, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_turnos))
                            .addComponent(labelsPanels, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commandTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(generalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(generalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                case "revisarTodo":
                    revisarCasillas();
                    break;
                case "revisarCasilla":
                    revisarCasillaEspecific(separado);
                    break;
                case "ultimoAtaque":
                    addMensaje(refCliente.ultimoAtaque);
                    break;   
                case "logTotal":
                    addMensaje(refCliente.bitacora);
                    break;  
                case "consultarEnemigo":
                    if(separado.length != 2){
                try {
                    refCliente.hiloCliente.writer.writeInt(12);      // Se envia al servidor la accion de enviar un mensaje por chat y se envia el mensaje
                    refCliente.hiloCliente.writer.writeUTF(lblNombreJugador.getText());
                    refCliente.hiloCliente.writer.writeUTF(separado[1]);
                } catch (IOException ex) {
                    Logger.getLogger(InterfazCliente.class.getName()).log(Level.SEVERE, null, ex);
                }
                    }
                    else{
                        addMensaje("Algoritmo ingresado en formato incorrecto.");
                    }
                    break;
                case "terminarTurno":
                    try {
                        this.getRefCliente().getHiloCliente().writer.writeInt(9);
                        this.getRefCliente().getHiloCliente().writer.writeUTF(lblNombreJugador.getText());
                    } catch (IOException ex) {
                        Logger.getLogger(InterfazCliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break; 
                default:
                    addMensaje("Error, el comando ingresado es inexistente");
                    break;             
                }
                commandTextField.setText(""); 
        }
    }//GEN-LAST:event_commandTextFieldKeyPressed

    
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
    private javax.swing.JLabel casillas1;
    private javax.swing.JLabel casillas2;
    private javax.swing.JLabel casillas3;
    private javax.swing.JTextArea commandTextArea;
    private javax.swing.JTextField commandTextField;
    private javax.swing.JPanel generalPanel;
    private javax.swing.JLabel imagen_lbl1;
    private javax.swing.JLabel imagen_lbl2;
    private javax.swing.JLabel imagen_lbl3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel labelsPanel;
    private javax.swing.JPanel labelsPanels;
    private javax.swing.JLabel lblNombreJugador;
    private javax.swing.JLabel lbl_turno;
    private javax.swing.JLabel lbl_turnos;
    private javax.swing.JLabel lbl_vida1;
    private javax.swing.JLabel lbl_vida2;
    private javax.swing.JLabel lbl_vida3;
    private javax.swing.JLabel nombre_lbl1;
    private javax.swing.JLabel nombre_lbl2;
    private javax.swing.JLabel nombre_lbl3;
    private javax.swing.JLabel num_poder1;
    private javax.swing.JLabel num_poder2;
    private javax.swing.JLabel num_poder3;
    private javax.swing.JLabel poder_1;
    private javax.swing.JLabel poder_2;
    private javax.swing.JLabel poder_3;
    private javax.swing.JLabel poder_lbl1;
    private javax.swing.JLabel poder_lbl2;
    private javax.swing.JLabel poder_lbl3;
    private javax.swing.JLabel porcentajePerso1;
    private javax.swing.JLabel porcentajePerso2;
    private javax.swing.JLabel porcentajePerso3;
    private javax.swing.JLabel porcentaje_lbl1;
    private javax.swing.JLabel porcentaje_lbl2;
    private javax.swing.JLabel porcentaje_lbl3;
    private javax.swing.JLabel resis_num1;
    private javax.swing.JLabel resis_num2;
    private javax.swing.JLabel resis_num3;
    private javax.swing.JLabel resistencia_lbl1;
    private javax.swing.JLabel resistencia_lbl2;
    private javax.swing.JLabel resistencia_lbl3;
    private javax.swing.JLabel sanidad_lbl1;
    private javax.swing.JLabel sanidad_lbl2;
    private javax.swing.JLabel sanidad_lbl3;
    private javax.swing.JLabel sanidad_num1;
    private javax.swing.JLabel sanidad_num2;
    private javax.swing.JLabel sanidad_num3;
    // End of variables declaration//GEN-END:variables
}

/*
        String i = "crearPersonaje-Aquaman-F:\\Semestre II\\POO\\SeaWar\\SeaWars\\SeaWars\\src\\imagenes\\aquaman.jpg-100-100-100-Kraken-35";
        String d = "crearPersonaje-Poseidon-F:\\Semestre II\\POO\\SeaWar\\SeaWars\\SeaWars\\src\\imagenes\\poseidon.jpg-75-75-75-Volcanoes-46";
        String f = "crearPersonaje-Manta-F:\\Semestre II\\POO\\SeaWar\\SeaWars\\SeaWars\\src\\imagenes\\manta.jpg-50-50-50-WavesControl-19";
        String[] a = i.split("-");
        String[] b = d.split("-");
        String[] c = f.split("-");
        addMensaje(refCliente.crearPersonajes(a));
        addMensaje(refCliente.crearPersonajes(b));
        addMensaje(refCliente.crearPersonajes(c));*/