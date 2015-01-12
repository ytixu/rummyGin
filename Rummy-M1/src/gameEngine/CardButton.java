package gameEngine;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import GUI.GameGUI;

public class CardButton extends JButton{
	private boolean isClicked;
	private Icon cardIcon;
		
	public CardButton(){
		super();
		isClicked = false;
	}
	
	public boolean isClicked(){
		return isClicked;
	}
	
	public void setClicked(boolean b){
		isClicked = b;
	}
	
	@Override
	public void setIcon(Icon i){
		super.setIcon(i);
		cardIcon = i;
	}
	
	@Override
	public Icon getIcon(){
		return cardIcon;
	}
	
	public void removeIcon(){
		super.setIcon(null);
	}
	
	private JLabel getClickedCard(Icon i){
		JLabel card = new JLabel(i);
		card.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
		return card;
	}
	
	public void checkSelection(){
		if (isClicked()){
			setClicked(false);
			removeAll();
			setIcon(getIcon());
			revalidate();
			repaint();
		}else{
			setClicked(true);
			removeAll();
			add(getClickedCard(getIcon()));
			revalidate();
			repaint();
		}
	}
}
