/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 *
 * @author marco
 */
public class Ntp_Server extends Thread {

    public static final int port = 5000;
    private ServerSocket socketS;
    
    public Ntp_Server() throws IOException {
        socketS = new ServerSocket(port);
    }
    
    public void run() {
        
        DataOutputStream salida;
        DataInputStream entrada;
        String message,messageSal;
        
        while(true) {
            try {
                Socket socketC = socketS.accept();
                entrada = new DataInputStream(socketC.getInputStream());
                message = entrada.readUTF(); //recibe hora del cliente
                // System.out.println("el tiempo en el cliente es: "+message);
                Date tiempo = new Date(); //obtiene hora del servidor
                messageSal=Long.toString(tiempo.getTime())+"@";//convierte a cadena y concatena con @
                salida = new DataOutputStream(socketC.getOutputStream());
                salida.writeUTF(messageSal+tiempo.getTime()); //envia hora del cliente y hora del servidor
               // System.out.println("el tiempo de server es: "+tiempo.getTime());
               
               // tiempo.setTime(Long.parseLong(message));
               // System.out.println("la hora es: "+tiempo);
                socketC.close();
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        try {
            Thread t = new Ntp_Server();
            t.start();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
}
