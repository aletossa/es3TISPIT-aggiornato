package com.example;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerConnection implements Runnable{

    private Socket s;
    BufferedReader br;
        
    public ServerConnection(Socket s) throws IOException{

        this.s=s;
        this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }
    
    
    public Socket getS() {
        return s;
    }

    public void run() {
        
        boolean a=true;
        try{
            while(a){

                String serverRisposta=br.readLine(); 
                if(serverRisposta.equals("@")){
                    System.out.println("chiudo");
                    s.close();
                    System.exit(0);
                }
                    System.out.println(serverRisposta);

            }
               
        } catch (IOException e) {
            e.printStackTrace();
        }
        

    }
    
}
