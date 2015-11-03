package gui;

import javax.imageio.ImageIO;

public class CardPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	private BufferedImage image;
	private Card card;
	public CardPanel(Card card) {
		//Prueba Borrar
		String colorCard;
		this.card = card;
		String cardColor = card.getColor(),
			   cardType = card.getType(),
		       imagePath = null;
		
		if(cardColor != null){
			setBackground(getColor(cardColor));				
		}
		
		if(cardType.equals("Number")){
			imagePath = card.getNumber();
			System.out.print("Numero " + imagePath);
			
		}
		
		else{
			imagePath = card.getType();
		}
		
		setSize(150, 200);
		try {
			image = ImageIO.read(new File(imagePath + ".png"));
		} catch (IOException e) {
			System.out.println("Error IO "+imagePath);
			System.out.println(" largo " + cardType.length());
		}
	}
	
	private Color getColor(String color){
		if(color.equals("GREEN"))
			return Color.GREEN;
		if(color.equals("RED"))
			return Color.RED;
		else if(color.equals("BLUE"))
			return Color.BLUE;
		else if(color.equals("YELLOW"))
			return Color.YELLOW;
		else
			return null;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image, 0, 0,null);
	}
	
//	public static void main(String[]args){
//		JFrame frame = new JFrame();
//		JPanel panelContenido = new JPanel(null);
//		panelContenido.setSize(650, 200);
////		CardPanel cardPanel = new CardPanel("four", Color.RED);
////		cardPanel.setBounds(0, 0, 150, 200);
//		PlayerCards panelCartas = new PlayerCards();
////		panelContenido.add(cardPanel);
//		panelContenido.add(panelCartas);
//		frame.setContentPane(panelContenido);		
//		frame.setSize(panelContenido.getSize());
//		frame.setVisible(true);
//		
//		Scanner kb = new Scanner(System.in);
//		String numero, color;
//		while (true){
//			System.out.print("Numero:");
//			numero = kb.nextLine();
//			
//			System.out.print("Color:");
//			color = kb.nextLine();
//			
//			panelCartas.addCard(numero, color);
//		}
		
//	}
	
	public Card getCard(){
		return this.card;
	}

}
