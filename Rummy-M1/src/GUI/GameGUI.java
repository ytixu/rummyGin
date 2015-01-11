package GUI;

import gameEngine.GameEngine;
import gameEngine.Player;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameGUI extends GameEngine {
	
	private JPanel aMainPanel = new JPanel();
	private JPanel aHandDisplay = new JPanel();
	private JPanel aLogDisplay = new JPanel(); // score and log
	private JPanel aLayoutDisplay = new JPanel();
	private JPanel aCardStackDisplay = new JPanel();
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
	
	private void setupCardsPacks(){
		aCardStackDisplay.setLayout(new GridLayout(0,2,5,5));
		aCardStack
	}
	
	public void show(JFrame jf){
		displayPlayer();
		jf.add(aMainPanel);
	}
}
