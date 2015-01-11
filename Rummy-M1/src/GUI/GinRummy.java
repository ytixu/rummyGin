package GUI;

import gameEngine.GameEngine;
import gameEngine.Player;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class GinRummy extends JFrame{
	private static final int MARGIN = 10;
	private static final int WIDTH = 400;
	private static final int HEIGHT = 300;
	
	private GameEngine ge = new GameEngine();
	private final Menu menu = new Menu(MARGIN);
	
	public GinRummy(){
		super();
//		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		JPanel contentPanel = new JPanel();
		Border padding = BorderFactory.createEmptyBorder(MARGIN, MARGIN, MARGIN, MARGIN);
		contentPanel.setBorder(padding);
		setContentPane(contentPanel);
		setLayout(new BorderLayout(MARGIN, MARGIN));
		showMenu();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		//
		menu.setStartBtn(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Set<Player> p = menu.getOptions(ge);
				ge.setPlayer(p);
			}
			
		});
	}
	
	private void showMenu(){
		add(new JLabel("Configure Players", SwingConstants.CENTER), BorderLayout.NORTH);
		add(menu, BorderLayout.CENTER);
	}
	
	public static void main(String[] args){
		new GinRummy();
	}
}