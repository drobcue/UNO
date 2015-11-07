package gui;

import javax.swing.JPanel;

import prototype.Card;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class PlayerCards extends JPanel {
	private int ancho = 800, alto = 300; //Dimensiones del arreglo de cartas
	private ArrayList<CardPanel> panelArr = new ArrayList<>();
	private CardListener listener = new CardListener();
	private CardPanel cardInPlay;
	/**
	 * Create the panel.
	 */
	public PlayerCards() {
		setLayout(null);
		setSize(ancho, alto);
		//setBackground(Color.DARK_GRAY); // solo para prueba
	}
	
	public void addCard(Card card){
		Color cardColor = null;
		
		CardPanel tempPanel = new CardPanel(card);
		tempPanel.addMouseListener(listener);
		
		panelArr.add(tempPanel);
		
		if (panelArr.size() == 1)
			panelArr.get(0).setLocation((ancho/2) - (150/2), 0);
		else 
			moveCards();
		add(tempPanel);
		this.repaint();
		System.out.println(card.toString());
	}
		
	private void moveCards(){
		int arrMid = panelArr.size()/2,
			midPoint = ancho/2,
			cardsGap,
			leftPoint, rigthPoint, leftCard, rigthCard;
			if(panelArr.size() > 5)
				cardsGap = ancho / panelArr.size();
			else 
				cardsGap = panelArr.get(0).getWidth();
		if ((panelArr.size() % 2) == 0){
			leftPoint = midPoint - cardsGap;
			leftCard = arrMid - 1;
			rigthPoint = midPoint;
			rigthCard = arrMid;
		}
		
		else{
			leftPoint = midPoint - (cardsGap/2)*3;
			leftCard = arrMid - 1;
			rigthPoint = midPoint + (cardsGap/2);
			rigthCard = arrMid + 1;
			panelArr.get(arrMid).setLocation(midPoint - (cardsGap/2), 100);
		}
		
		for (int l = leftCard, r = rigthCard; l >= 0; l--, r++){
			panelArr.get(l).setLocation(leftPoint, 100);
			panelArr.get(r).setLocation(rigthPoint, 100);
			leftPoint -= cardsGap;
			rigthPoint += cardsGap;
		}
		validate();
	}
	
	private void throwCard(CardPanel selectedCard){
//		PlayerCards panel = (PlayerCards)arg0.getComponent().getParent();
		CardPanel cardInPlay = TablePane.cardPanel;
		panelArr.remove(
				panelArr.indexOf(
						selectedCard));
		selectedCard.setVisible(false);
		remove(selectedCard);
		if (!panelArr.isEmpty())
			moveCards();
		cardInPlay.setVisible(false);
		TablePane table = (TablePane)this.getParent();
		table.remove(cardInPlay);			
		cardInPlay = selectedCard;
		cardInPlay.setLocation(TablePane.xcardCenter, TablePane.ycardCenter);
		cardInPlay.setVisible(true);
		table.add(cardInPlay);
		TablePane.cardPanel = cardInPlay;
		sendMsg();
		TablePane.turn = false;
		table.revalidate();
	}
	private void sendMsg(){
		String msg = "Throw " + 
				TablePane.cardPanel.getCard().getType() + " " + 
				TablePane.cardPanel.getCard().getColor() + " " +
				TablePane.cardPanel.getCard().getNumber();
		System.out.println(msg);
		((TablePane) this.getParent()).noifyThrown(msg);
		
//		TablePane.noifyThrown(msg);
	}
	
	private boolean isValidMove(CardPanel throwCard){
		if (throwCard.getCard().getColor().equalsIgnoreCase(TablePane.cardPanel.getCard().getColor()))
			return true;
		else if (throwCard.getCard().getNumber().equalsIgnoreCase(TablePane.cardPanel.getCard().getNumber()))
			return true;
		else
			return false;
	}
	
	private class CardListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {	
			CardPanel selectedCard = (CardPanel)arg0.getComponent();
//			System.out.println(getParent().getParent().getParent().getParent().getParent().getParent().getClass().toString());
			if(isValidMove(selectedCard) && TablePane.turn)
				throwCard(selectedCard);
			
		}
		

		@Override
		public void mouseEntered(MouseEvent arg0) {
			CardPanel hoverCard =(CardPanel)arg0.getComponent();
			Rectangle bound = hoverCard.getBounds();
			bound.y = bound.y - 100;
			hoverCard.setBounds(bound);			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			CardPanel hoverCard =(CardPanel)arg0.getComponent();
			Rectangle bound = hoverCard.getBounds();
			bound.y = bound.y + 100;
			hoverCard.setBounds(bound);		
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
