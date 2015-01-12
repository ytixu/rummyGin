package GUI;

import gameEngine.CardButton;
import gameEngine.CommandLineObs;
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
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ca.mcgill.cs.comp303.rummy.gui.swing.CardImages;
import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Hand;

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
	
	private Set<Card> selectedCards = new HashSet<Card>();

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
			if (getTurn() == i){
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
		aDiscardImage.setCard(peekDiscard());
	}
	
	private void updateHandDisplay(){
		int i=0;
		for (Card c: getPlayers()[getTurn()]){
			((CardButton)aHandDisplay.getComponent(i)).setCard(c);
			i++;
		}
		aHandDisplay.revalidate();
		aHandDisplay.repaint();
	}
	
	private void setupCardsPacks(){
		JPanel aCardStackDisplay = new JPanel();
		aCardStackDisplay.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 0));
		aDeckImage.setIcon(CardImages.getBack());
		aDeckImage.setToolTipText("Deck");
//		aDeckImage.addActionListener(new ActionListener(){
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				CardButton btn = (CardButton)e.getSource();
//				btn.checkSelection();
//			}
//		});
		aCardStackDisplay.add(aDeckImage);
		aDiscardImage.setToolTipText("Discard");
		updateDiscardImage();
		aCardStackDisplay.add(aDiscardImage);
		aHandDisplay.setLayout(new FlowLayout());
		for (int i=0; i<Hand.HANDSIZE; i++){
			aHandDisplay.add(new CardButton());
		}
		JPanel stack = new JPanel();
		stack.setLayout(new BoxLayout(stack, BoxLayout.Y_AXIS));
		stack.add(Box.createRigidArea(new Dimension(0,200)));
		stack.add(aCardStackDisplay);
		stack.add(Box.createRigidArea(new Dimension(0,200)));
		stack.add(aHandDisplay);
		aMainPanel.add(stack, BorderLayout.CENTER);
	}
	
	public void start(){
		playNextRound();
		updateHandDisplay();
	}
	
	public void show(JFrame jf){
		displayPlayer();
		setupCardsPacks();
		CommandLineObs clo = new CommandLineObs();
		addObserver(clo);
		start();
		jf.add(aMainPanel);
	}
}
