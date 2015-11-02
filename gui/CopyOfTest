package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import TCP.TCPClient;
import TCP.TCPServer;

import java.awt.Color;
import java.io.IOException;
import java.util.Scanner;

public class CopyOfTest extends JPanel {

	/**
	 * Create the panel.
	 * @throws IOException 
	 */
	static TCPClient conection;
	
	public CopyOfTest(){
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
	
		JFrame frame = new JFrame();
		JPanel panelContenido = new JPanel(null);
		panelContenido.setSize(800, 800);
//		CardPanel cardPanel = new CardPanel("four", Color.RED);
//		cardPanel.setBounds(0, 0, 150, 200);
		CopyOfTest table = new CopyOfTest();
		table.setBounds(0, 0, 800, 800);
//		panelContenido.add(cardPanel);
		panelContenido.add(table);
		frame.setContentPane(panelContenido);		
		frame.setSize(panelContenido.getSize());
		frame.setVisible(true);
		
		conection = new TCPClient(5000);
		conection.run();
		
		
		
	}
}
