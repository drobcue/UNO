package TCP;
//TCPClient.java

import gui.PlayerCards;
import gui.TablePane;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import prototype.Card;

public class TCPClient extends Thread 
{
	private String FromServer;
	private String ToServer;
	private Socket clientSocket;
	private BufferedReader inFromUser =
             new BufferedReader(new InputStreamReader(System.in));
 
	private PrintWriter outToServer;
 
	private BufferedReader inFromServer;
	private String msg;
	
	public TCPClient(int port) throws UnknownHostException, IOException{
		clientSocket = new Socket("localhost", port);
	}
	
	@Override
	public void run(){
		try {
			outToServer = new PrintWriter(
					     clientSocket.getOutputStream(),true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				     
		BufferedReader inFromServer = null;
		try {
			inFromServer = new BufferedReader(new InputStreamReader(
			clientSocket.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				     
		while (true){			  
				    
			try {
				FromServer = inFromServer.readLine();
				readCMD(FromServer);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				    
		}   
				  
	}
	
	  private void readCMD(String data){
	    	Scanner tokenizer = new Scanner(data);
	    	String cmd = tokenizer.next();
	    	String type = null, color = null, number = null;
	    	//System.out.println(cmd);
	    	
	    	
	    	switch (cmd){
	    		case "Throw": 
	    			type = tokenizer.next();
	    			if(tokenizer.hasNext())
	    				color = tokenizer.next();
	    			if(tokenizer.hasNext())
	    				number = tokenizer.next();
	    			TablePane.updateCard(new Card(type, color, number));
	    			break;
	    		case "Turn":
	    			TablePane.setTurn(true);
	    			 synchronized(this){
		       			 try {
		       				 wait();
		       				ToServer = this.msg;
					         outToServer.println(ToServer);
		       			 } catch (InterruptedException e) {
		       				 // TODO Auto-generated catch block
		       				 e.printStackTrace();
					       }
		       		 }
	    			break;
	    		case "Add":
	    			type = tokenizer.next();
	    			if(tokenizer.hasNext())
	    				color = tokenizer.next();
	    			if(tokenizer.hasNext())
	    				number = tokenizer.next();
	    			TablePane.addCard(new Card(type, color, number));
	    			if(TablePane.getTurn()){
	    				synchronized(this){
			       			 try {
			       				 wait();
			       				ToServer = this.msg;
						         outToServer.println(ToServer);
			       			 } catch (InterruptedException e) {
			       				 // TODO Auto-generated catch block
			       				 e.printStackTrace();
						       }
			       		 }
	    			}	    				
	    			break;
	    		default :
	    			System.out.println("Error reading comand!");
	    	}
	    	tokenizer.close();
	    }
	  
	  public void setMsg(String msg){
		  this.msg = msg;
	  }

}
