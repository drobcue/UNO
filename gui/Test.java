package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import prototype.Card;
import TCP.TCPServer;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class Test extends JPanel {

	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	static TCPServer conection;
	static Stack<Card> toPlayStack = new Stack<>(),
				playedStack = new Stack<>();
	
	public Test(){
		TablePane table = null;
		setLayout(null);
		try {
			 table = new TablePane();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		table.setBounds(0, 0, 800, 800);
		
		add(table);
	}
	public static void main(String[]args) throws IOException{
	
		setToPlayStack();
		JFrame frame = new JFrame();
		JPanel panelContenido = new JPanel(null);
		Test table = new Test();
		table.setBounds(0, 0, 800, 800);
		panelContenido.setSize(800, 800);
//		CardPanel cardPanel = new CardPanel("four", Color.RED);
//		cardPanel.setBounds(0, 0, 150, 200);
		
//		panelContenido.add(cardPanel);
		panelContenido.add(table);
		frame.setContentPane(panelContenido);		
		frame.setSize(panelContenido.getSize());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		
		conection = new TCPServer(5000);
		conection.run();
		
		
		
		
	}
	
	public static Card getCard(){
		return toPlayStack.pop();
	}
	
	private static void setToPlayStack(){
			ArrayList <String> redCards = new ArrayList<>(Arrays.asList("0", "1", "1", 
					"2" ,"2", "3" ,"3", "4", 
					"4", "5", "5", "6", "6", 
					"7", "7", "8", "8","9", 
					"9", "Skip", "Skip", 
					"Reverse", "Reverse", 
					"Plus2", "Plus2")),
		   blueCards = new ArrayList<>(Arrays.asList("0", "1", "1", 
					"2" ,"2", "3" ,"3", "4", 
					"4", "5", "5", "6", "6", 
					"7", "7", "8", "8","9", 
					"9", "Skip", "Skip", 
					"Reverse", "Reverse", 
					"Plus2", "Plus2")),
		   yellowCards = new ArrayList<>(Arrays.asList("0", "1", "1", 
					"2" ,"2", "3" ,"3", "4", 
					"4", "5", "5", "6", "6", 
					"7", "7", "8", "8","9", 
					"9", "Skip", "Skip", 
					"Reverse", "Reverse", 
					"Plus2", "Plus2")),
		   greenCards = new ArrayList<>(Arrays.asList("0", "1", "1", 
					"2" ,"2", "3" ,"3", "4", 
					"4", "5", "5", "6", "6", 
					"7", "7", "8", "8","9", 
					"9", "Skip", "Skip", 
					"Reverse", "Reverse", 
					"Plus2", "Plus2")),
		   wildCards = new ArrayList<>(Arrays.asList("Simple", "Simple",
				                       "Simple", "Simple",
				                       "Plus4", "Plus4",
				                       "Plus4", "Plus4"));
	Random rnd = new Random();
	while(!(redCards.isEmpty() && blueCards.isEmpty() && 
	greenCards.isEmpty() && yellowCards.isEmpty() &&
	wildCards.isEmpty())){
	int indexOfCard = 0;
	
	switch (rnd.nextInt(5))
	{
	case 0:
	if(! redCards.isEmpty()){
		indexOfCard = rnd.nextInt(redCards.size());
		String card = redCards.get(indexOfCard);
		if (Character.isDigit(card.charAt(0)))
			toPlayStack.add(new Card("Number","RED", card));
		else 
			toPlayStack.add(new Card(card, "RED"));
		System.out.println("RED");
		redCards.remove(indexOfCard);
	}
	break;
	
	case 1:
	if(! blueCards.isEmpty()){
		indexOfCard = rnd.nextInt(blueCards.size());
		String card = blueCards.get(indexOfCard);
		if (Character.isDigit(card.charAt(0)))
			toPlayStack.add(new Card("Number","BLUE", card));
		else 
			toPlayStack.add(new Card(card, "BLUE"));
		System.out.println("BLUE");
		blueCards.remove(indexOfCard);
	}
	break;
	
	case 2:
	if(! yellowCards.isEmpty()){
		indexOfCard = rnd.nextInt(yellowCards.size());
		String card = yellowCards.get(indexOfCard);
		if (Character.isDigit(card.charAt(0)))
			toPlayStack.add(new Card("Number","YELLOW", card));
		else 
			toPlayStack.add(new Card(card, "YELLOW"));
		System.out.println("YELLOW");
		yellowCards.remove(indexOfCard);
	}
	
	case 3:
	if(! greenCards.isEmpty()){
		indexOfCard = rnd.nextInt(greenCards.size());
		String card = greenCards.get(indexOfCard);
		if (Character.isDigit(card.charAt(0)))
			toPlayStack.add(new Card("Number","GREEN", card));
		else 
			toPlayStack.add(new Card(card, "GREEN"));
		System.out.println("GREEN");
		greenCards.remove(indexOfCard);
	}
	break;
	
	case 4:
	if(! wildCards.isEmpty()){
		indexOfCard = rnd.nextInt(wildCards.size());
		String card = wildCards.get(indexOfCard);
		toPlayStack.add(new Card(card));
		System.out.println("WILD");
		wildCards.remove(indexOfCard);
	}
	break;
	default : 
	System.out.println("Error");
	}
	
	}
	}
}
