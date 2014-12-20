package ca.mcgill.cs.comp303fall2014.lecture02b;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ca.mcgill.cs.comp303fall2014.lecture02b.Card.Rank;
import ca.mcgill.cs.comp303fall2014.lecture02b.Card.Suit;

@SuppressWarnings("serial")
public class PokerHandGenerator extends JFrame implements ActionListener
{
	private static final int MARGIN = 10;
	
	private static final String P_TWO_PAIRS = "^(two|2)\\s+pairs$";
	private static final String P_FLUSH = "flush$";
	private static final String P_STRAIGHT = "straight$";
	
	private List<JLabel> aCards = new ArrayList<JLabel>();
	private JPanel aDisplay = new JPanel();
	private Random aRandom = new Random();
	
	private JTextField aInput = new JTextField();
	
	private static final boolean ALLOW_JOCKER = true;
	
	public PokerHandGenerator()
	{
		super("Poker hand generator");
		ImageIcon image = CardImages.getBack();
		for( int i = 0; i < 5; i++ )
		{
			JLabel label = new JLabel();
			label.setIcon(image);
			aDisplay.add(label);
			aCards.add(label);
		}
			
		setLayout(new BorderLayout(MARGIN, MARGIN));
		add(aDisplay, BorderLayout.CENTER);
		add(aInput, BorderLayout.SOUTH);
		aInput.addActionListener(this);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(false);
		setVisible(true);
	}

	// helper methods
	private Rank randRank(boolean allowJocker){
		Rank r;
		do{
			r = Rank.values()[(int) Math.floor(aRandom.nextFloat()*Rank.values().length)];
			if (allowJocker) return r;
		}while (r.equals(Rank.JOCKER));
		return r;
	}
	private Rank randRank(){
		return randRank(false);
	}
	private Suit randSuit(){
		return Suit.values()[(int) Math.floor(aRandom.nextFloat()*Suit.values().length)];
	}
	
	private enum InputType{
		TWOPAIRS, FLUSH, STRAIGHT, OTHER
	}
	
	public InputType matchText(String pText){
		String[] patterns = new String[]{
			P_TWO_PAIRS, P_FLUSH, P_STRAIGHT
		};
		for (int i=0; i<patterns.length; i++){
			Pattern p = Pattern.compile(patterns[i],Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(pText);
			if (m.matches()) return InputType.values()[i];
		}
		return InputType.OTHER;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		Card[] cards = null;
		switch (matchText(aInput.getText())){
			case TWOPAIRS: 
				cards = twoPairs();
				break;
			case FLUSH: 
				cards = flush();
				break;
			case STRAIGHT:
				cards = straight();
				break;
			default:
				cards = anything();
		}
		int i = 0;
		for(JLabel label : aCards)
		{
			label.setIcon( CardImages.getCard(cards[i]));
			i++;
		}
		aInput.setText("");
		
	}
	
	private Card[] twoPairs() // TODO
	{	
		ArrayList<Card> cards = new ArrayList<Card>();
		for (int i=0; i<5; i++){
			if (i%2==1){ // much match previous card's rank
				Suit s;
				do{
					s = randSuit();
				}while (s.equals(cards.get(i-1).getSuit()));
				cards.add(new Card(cards.get(i-1).getRank(), s));
			}else if (i==2){
				Rank r;
				do{
					r = randRank();
				}while (r.equals(cards.get(i-1).getRank()));
				cards.add(new Card(r, randSuit()));
			}else if (i==4){
				Card c;
				do{
					c = new Card(randRank(ALLOW_JOCKER), randSuit());
				}while (cards.contains(c));
				cards.add(c);
			}else{
				cards.add(new Card(randRank(), randSuit()));
			}
		}
		Collections.shuffle(cards);
		return cards.toArray(new Card[5]);
	}
	
	private Card[] flush() // TODO
	{
		ArrayList<Card> cards = new ArrayList<Card>();
		Suit s = randSuit();
		for (int i=0; i<5; i++){
			Card c;
			do {
				c = new Card(randRank(), s);
			}while (cards.contains(c));
			cards.add(c);
		}
		return cards.toArray(new Card[5]); 
	}
	
	private Card[] straight(){ // TODO
		ArrayList<Card> cards = new ArrayList<Card>();
		Rank startR;
		do{
			startR = randRank();
		}while(startR.ordinal() < Rank.TWO.ordinal() || 
				startR.ordinal() > Rank.TEN.ordinal());
		for (int i=0; i<5; i++){
			Card c = new Card(startR, randSuit());
			if (startR.equals(Rank.KING)){
				startR = Rank.ACE;
			}else{
				startR = Rank.values()[startR.ordinal()+1];
			}
			cards.add(c);
		}
		Collections.shuffle(cards);
		return cards.toArray(new Card[5]); 
	}
	
	private Card[] anything()
	{
		Deck deck = new Deck();
		deck.shuffle();
		Card[] lReturn = new Card[5];
		for(int i = 0; i < 5; i ++)
		{
			lReturn[i] = deck.draw();
		}
		return lReturn;
	}
	
	public static void main(String[] args)
	{
		new PokerHandGenerator();
	}
}
