package com.example;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.TreeUI;

public class server{
  
  private static List<ClientHandler> clients = new ArrayList<>();
  
    public static void main( String[] args ) throws Exception
    {
        ServerSocket ss = new ServerSocket(3000);
        System.out.println("Server in ascolto sulla porta 3000");
        boolean a=true;
        int contatore=0;
        
        while(a){
        contatore++;
        Socket s = ss.accept();
        System.out.println("connesso al client!");
        ClientHandler ch=new ClientHandler(s,clients,contatore);
        clients.add(ch);
        ch.start();
                
      }

      ss.close();
        
    }
}
