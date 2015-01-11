package GUI;

import gameEngine.GameEngine;
import gameEngine.HumanPlayer;
import gameEngine.OptPlayer;
import gameEngine.Player;
import gameEngine.RandomPlayer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class Menu extends JPanel{
	
	private final static int WIDTH = 200;
	private final static String[] PlayerType = new String[]{"HumanPlayer", "Wobbuffet", "Psyduck"}; 
	
	private int numberOfPlayers = 0;
//	private HashMap<Integer, String> options = null;
	private JPanel grid; 
	private JPanel buttons;
	private HashMap<JComboBox, JTextField> options = new HashMap<JComboBox, JTextField>();
	
	public Menu(int margin){
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel text = new JLabel("Configure Players");
		text.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(text);
		grid = new JPanel();
		grid.setLayout(new GridLayout(0,2,margin,0));
		grid.add(new JLabel("Player type"));
		grid.add(new JLabel("Player name"));
		appendPlayer();
		appendPlayer();
		add(grid);
		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		add(buttons);
	}
	
	private void appendPlayer(){
		numberOfPlayers++;
		JComboBox playerList = new JComboBox(PlayerType);
		playerList.setPreferredSize(new Dimension(WIDTH, 20));
		playerList.setMaximumSize( playerList.getPreferredSize() );
		JTextField name = new JTextField();
		name.setText("Name");
		name.setPreferredSize(new Dimension(200, 20));
		name.setMaximumSize( name.getPreferredSize() );
		grid.add(playerList);
		grid.add(name);
		options.put(playerList, name);
	}
	
	public void setStartBtn(ActionListener actlst){
		JButton aButton = new JButton("Start");
		aButton.addActionListener(actlst);
		buttons.add(aButton);
		JButton bButton = new JButton("Add another player");
		buttons.add(bButton);
	}
	
	private Player getPlayer(String type, String name, GameEngine ge){
		if (type.equals(PlayerType[0])){
			return new HumanPlayer(name, ge);
		}else if (type.equals(PlayerType[1])){
			return new OptPlayer(ge);
		}else{
			return new RandomPlayer(ge);
		}
	}
	
	public Set<Player> getOptions(GameEngine ge){
		Set<Player> o = new HashSet<Player>();
		for(JComboBox b: options.keySet()){
			String p = String.valueOf(b.getSelectedItem());
			String n = options.get(b).getText();
			o.add(getPlayer(p,n,ge));
		}
		return o;
	}
	
}
