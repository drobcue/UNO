package TCP;
//TCPServer.java

import gui.TablePane;
import gui.Test;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import prototype.Card;

public class TCPServer extends Thread
{
	String fromclient;
    String toclient;
    
    //Socket para las conexiones entrantes
    ServerSocket Server;
    
    //Arreglo de socket para la conexiones al server por el puerto espesificado
    Socket [] playersConections = new Socket[3];
    
    //Arreglos de boffered readers, para recivir data desde los clientes
    BufferedReader [] inFromClients = new BufferedReader[3];
    
    //Arreglo de PrintWriter para enviar datos a los clientes
    PrintWriter[] outToClient = new PrintWriter[3];

    // numero de conexiones y turno de las conexiones
    int conections = 0, turn = 0;
    
    //BufferedReader para que recoger el teclado del server
    BufferedReader inFromUser = 
          new BufferedReader(new InputStreamReader(System.in));
	private char[] myMove;
	private Semaphore semaph = new Semaphore(1, true);
	private String msg;
    
    public TCPServer(int port) throws IOException{
    	this.Server = new ServerSocket (port);
    }
    
    @Override
    public void run(){
        
        
        System.out.println ("TCPServer Waiting for client on port 5000");
        
        //Loop crear conexiones
        while (conections < 3){      
        	try {
        		playersConections[conections] = Server.accept(); //Crea conexion con cliente
       		
        		inFromClients[conections] = new BufferedReader(
					 						new InputStreamReader(
					 								playersConections[conections].getInputStream())); 	 //Crea un lector desde el cliente
        		outToClient[conections] = new PrintWriter(
											playersConections[conections].getOutputStream(),true);
        	} catch (IOException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
       	
       	 	//Borrar
       	 	System.out.println("Player # " + (++conections) + " conected.");
        }
        
        //borrar
        System.out.println("All players conected\nThe game is begin");
        
        for(int i = 0; i < 1; i++){
        	outToClient[0].println(getCardToSend());
        	outToClient[1].println(getCardToSend());
        	outToClient[2].println(getCardToSend());
        	readCMD(getCardToSend());
        	
        }
        
        while(true){
        //	if(semaph.availablePermits() > 0)
      	//	 {
        		if(turn < 3){
        			System.out.println("Estas dentro de while");
       		      		 
        			if(semaph.availablePermits() > 0){
        				outToClient[turn].println("Turn");
            			try {
            				semaph.acquire();				
            			} catch (InterruptedException e) {
            				// TODO Auto-generated catch block
            				e.printStackTrace();
            			}   
        			}
        			try {
						readCMD(inFromClients[turn].readLine());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			    		
        		}
        		else{
        			System.out.println("Serevers Turns");
        			TablePane.setTurn(true);
        			synchronized(this){
        				try {
        					wait();
        				} catch (InterruptedException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
        			}
        			readCMD(msg);
        		}
        		
//        		turn = (turn + 1) % 4;
        		System.out.println("Turn #" + turn);
      	//	 }
        //	else
        //     	System.out.println("Error Semaphore");
        }
       
    }
    
    private String getCardToSend(){
    	Card temp = Test.getCard();
    	return "Add " + temp.getType() + " " + temp.getColor() + " " + temp.getNumber();
    }
    
    public void setMove(String move){
    	myMove = move.toCharArray();
    }
    
    private void readCMD(String data){
    	Scanner tokenizer = new Scanner(data);
    	String cmd = tokenizer.next();
    	String type = null, color = null, number = null;
    	System.out.println(cmd);
    	
    	
    	switch (cmd){
    		case "Throw": 
    			type = tokenizer.next();
    			if(tokenizer.hasNext())
    				color = tokenizer.next();
    			if(tokenizer.hasNext())
    				number = tokenizer.next();
    			TablePane.updateCard(new Card(type, color, number));
    			System.out.println("Luego del update");
    			for (PrintWriter clientOut : outToClient) {
    				System.out.println("Antes del Dato");
					clientOut.println(data);
					System.out.println("Luego Del Dato");
				}
    			turn = (turn + 1) % 4;
    			semaph.release();
    			break;
    		case "Add":
    			type = tokenizer.next();
    			if(tokenizer.hasNext())
    				color = tokenizer.next();
    			if(tokenizer.hasNext())
    				number = tokenizer.next();
    			TablePane.addCard(new Card(type, color, number));
    			break;
    		case "RequestCard":
    			if(turn < 3){
    				outToClient[turn].println(getCardToSend());
    			}
    			else{
    				readCMD(getCardToSend());
    			}
    		default :
    			System.out.println("Error reading comand!");
    	}
    	tokenizer.close();
    }
    
    public void setMsg(String msg){
		  this.msg = msg;
	  }    

}
