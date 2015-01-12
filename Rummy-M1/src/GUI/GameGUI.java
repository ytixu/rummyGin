package GUI;

import gameEngine.CardButton;
import gameEngine.GameEngine;
import gameEngine.Player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.cs.comp303.rummy.gui.swing.CardImages;

public class GameGUI extends GameEngine {
	
	private JPanel aMainPanel = new JPanel();
	private JPanel aHandDisplay = new JPanel();
	private JPanel aLogDisplay = new JPanel(); // score and log
	private JPanel aLayoutDisplay = new JPanel();
	private CardButton aDeckImage = new CardButton();
	private CardButton aDiscardImage = new CardButton();
	private JButton aKnockBtn;
	private JButton aDiscardBtn;
	private JButton aDoneBtn;

	public GameGUI(int margin){
		super();
		aMainPanel.setLayout(new BorderLayout(margin, margin));
	}
	
	private void displayPlayer(){
		JPanel playerDisplay = new JPanel();
		playerDisplay.setLayout(new FlowLayout());
		JPanel playerNames = new JPanel();
		playerNames.setLayout(new BoxLayout(playerNames, BoxLayout.Y_AXIS));
		JPanel playerTurn = new JPanel();
		playerTurn.setLayout(new BoxLayout(playerTurn, BoxLayout.Y_AXIS));
		Player[] ps = getPlayers();
		for (int i=0; i<ps.length; i++){
			if (getTrun() == i){
				playerTurn.add(new JLabel(">"));
			}else{
				playerTurn.add(new JLabel(" "));
			}
			playerNames.add(new JLabel(ps[i].toString()));
		}
		playerDisplay.add(playerTurn);
		playerDisplay.add(playerNames);
		aMainPanel.add(playerDisplay, BorderLayout.WEST);
	}
	
	private void updateDiscardImage(){
		aDiscardImage.setIcon(CardImages.getCard(peekDiscard()));
	}
	
	private void setupCardsPacks(){
		JPanel aCardStackDisplay = new JPanel();
		aCardStackDisplay.setLayout(new GridLayout(0,2,5,5));
		
		JLabel a = new JLabel("Deck");
		a.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		aCardStackDisplay.add(a);
		JLabel b = new JLabel("Discard");
		a.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		aCardStackDisplay.add(b);
		aDeckImage.setIcon(CardImages.getBack());
		aDeckImage.setBorderPainted(false);  
		aDeckImage.setFocusPainted(false);  
		aDeckImage.setContentAreaFilled(false);
//		aDeckImage.addActionListener(new ActionListener(){
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				CardButton btn = (CardButton)e.getSource();
//				btn.checkSelection();
//			}
//		});
		aCardStackDisplay.add(aDeckImage);
		updateDiscardImage();
		aDiscardImage.setBorderPainted(false);  
		aDiscardImage.setFocusPainted(false);  
		aDiscardImage.setContentAreaFilled(false);
		aCardStackDisplay.add(aDiscardImage);
		aMainPanel.add(aCardStackDisplay);
	}
	
	public void show(JFrame jf){
		displayPlayer();
		setupCardsPacks();
		jf.add(aMainPanel);
	}
}
