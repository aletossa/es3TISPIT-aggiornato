package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public class ClientHandler extends Thread {

    private Socket s;
    private int x=0;
    private boolean a=true;
    private LocalDate date = LocalDate.now();
    private LocalTime time = LocalTime.now();
    private String nomeServer="SERVERONE";
    int firstSpace;
    private List<ClientHandler> clients;
    // per parlare
    PrintWriter pr=null;
    //per ascoltare
    BufferedReader br=null;

    public ClientHandler(Socket s, List<ClientHandler> clients, int contatore){
        this.s=s;
        this.clients = clients;
        x=contatore;
        try {
            pr=new PrintWriter(s.getOutputStream(), true);
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            
            e.printStackTrace();
        }

    } 

    public void run(){

        try {
            
            System.out.println("Client connesso");
            System.out.println(br.readLine()); // ricevo: Eccomi
            pr.println("Ciao, come ti chiami?"); // invio messaggio
            String nome = br.readLine(); // ricevo il nome
            System.out.println("nome ricevuto");
            System.out.println(nome.toUpperCase());//converto il nome in maiuscolo
            
            pr.println("Benvenuto " + nome + " sei l'utente numero: " + x);
        
        while(a){
            
            pr.println("Cosa vuoi sapere?"); 
            String comandi = br.readLine();
            
            if(comandi.equals("data")){
               
                pr.println(date);
            }else if(comandi.equals("ora")){

                pr.println(time);

            }else if(comandi.equals("nome")){

                pr.println(nomeServer);
            }else if(comandi.equals("id")){

                pr.println(x); 
            }else if (comandi.startsWith("all")) {
                firstSpace = comandi.indexOf(" ");
                if (firstSpace != 1){
                    sendToAll(comandi.substring(firstSpace + 1));        
                }  
            }else if(comandi.equals("chiudi")){
                System.out.println("hai chiuso la connessione");
                sendToAll("@");
                clients.removeAll(clients);
                break;
            }else{
                pr.println("il comando non é valido");
            }
           
        }
        pr.println("Grazie e ciao"); 

        
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

    private void sendToAll(String msg) {
        for (ClientHandler client : clients) {
            client.pr.println(msg);
        }
    }
}

