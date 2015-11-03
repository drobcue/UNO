package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import prototype.Card;
import TCP.TCPServer;

public class TablePane extends JPanel {

	private int ancho = 800, largo = 800;
	static int xcardCenter = 350, ycardCenter = 200;
	static CardPanel cardPanel;
	static boolean turn = false;
	static PlayerCards playerCards;
	
	public TablePane() throws IOException{
		
		
		
		setLayout(null);
		setSize(ancho, largo);
		
		cardPanel = new CardPanel(new Card("Number", "RED", "4"));
		cardPanel.setLocation(xcardCenter, ycardCenter);
		add(cardPanel);		
		
		playerCards = new PlayerCards();
		playerCards.setLocation(0, 450);
		/*String parentClass = "class gui.Test";     //getParent().getClass().toString(); Borrar
		if (parentClass.equalsIgnoreCase("class gui.Test")){
			for(int i = 0; i < 4; i++){
				playerCards.addCard(Test.toPlayStack.pop());
			}
		}
		*/
		
//		playerCards.setCardInPlay(cardPanel);
		add(playerCards);
		
		JButton btnGetCards = new JButton();
		btnGetCards.setBounds(0, 0, 40, 40);
		btnGetCards.setVisible(true);
		btnGetCards.setAction(new RequestCard());
		add(btnGetCards);
		
		
		
	}
	public static void addCard(Card card){
		playerCards.addCard(card);
	}
	public static void updateCard(Card card){
		
		
		CardPanel tempPanel = new CardPanel(card);   // Ojo modificar cuando queiras tirar cartas
		
		cardPanel.setVisible(false);
		TablePane table = (TablePane)cardPanel.getParent();
		table.remove(cardPanel);			
		cardPanel = tempPanel;
		table.add(cardPanel);
		cardPanel.setLocation(xcardCenter, ycardCenter);
		cardPanel.setSize(150, 200);
		cardPanel.setVisible(true);		
		table.revalidate();
		
		
	}
		
	public void noifyThrown(String msg){
		
		String parentClass =getParent().getClass().toString();
		if (parentClass.equalsIgnoreCase("class gui.Test")){
			Test.conection.setMsg(msg);
			synchronized(Test.conection){
				Test.conection.notify();
			}
		}
		else{
			CopyOfTest.conection.setMsg(msg);
			synchronized(CopyOfTest.conection){
				CopyOfTest.conection.notify();
			}
		}

	}
	
	public static boolean getTurn(){
		return TablePane.turn;
	}
	
	public static void setTurn(boolean turn){
		TablePane.turn = turn;
	}
	
	private class RequestCard extends AbstractAction{
		
		public RequestCard() {
			putValue(NAME, "Get Cards");
			putValue(SHORT_DESCRIPTION, "Request for more cards");
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			noifyThrown("RequestCard");
			
		}
		
	}
}
