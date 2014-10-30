package ca.mcgill.cs.comp303.rummy.model;

import java.util.Scanner;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.GameViewer.PILE;

public class KeyboardPlayer extends Player {

	private Scanner input;
	
	public KeyboardPlayer(GameViewer pGame) 
	{
		super(pGame);
		input = new Scanner(System.in);
	}

	@Override
	Card discard() 
	{
		System.out.println("Your current hand is: ");
		Set<Card> hand = getHand().getUnmatchedCards();
		int i = 1;
		for(Card c : hand) 
		{
			System.out.println("\t" + i + ". " + c.toString());
			i++;
		}
		System.out.println("Select a card to discard (number)");
		int answer = input.nextInt();
		while(!(answer <= i && answer > 0))
		{
			System.out.println("Please select a number between 1 and " + i);
			answer = input.nextInt();
		}
		int j = 1;
		Card toRemove = null;
		for(Card c : hand) 
		{
			if(answer == j)
			{
				toRemove = c;
				System.out.println("Picked " + c.toString() + " to discard");
			}
			j++;
		}
		return toRemove;
	}

	@Override
	PILE draw() 
	{
		System.out.println("Top card of discard pile is: " + getGame().topOfStack().toString());
		System.out.println("Your current hand is: ");
		Set<Card> hand = getHand().getUnmatchedCards();
		for(Card c : hand) 
		{
			System.out.println("\t" + c.toString());
		}
		System.out.println("Draw from DECK or DISCARD_PILE?");
		String answer = input.next();
		while(!(answer.equalsIgnoreCase("DECK") || answer.equalsIgnoreCase("DISCARD_PILE"))) 
		{
			System.out.println("Make sure you enter DECK or DISCARD_PILE");
			System.out.println("Top card of discard pile is: " + getGame().topOfStack().toString());
			System.out.println("Draw from DECK or DISCARD_PILE?");
			answer = input.next();
		}
		
		if(answer.equalsIgnoreCase("DECK"))
		{
			return PILE.DECK;
		}
		else
		{
			return PILE.DISCARD;
		}
	}

	@Override
	boolean knock() 
	{
		if(!checkKnockable()) 
		{
			return false;
		}
		
		System.out.println("Want to knock? (Y/N)");
		String answer = input.next();
		while(!(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES") || answer.equalsIgnoreCase("N") || answer.equalsIgnoreCase("NO")))
		{
			System.out.println("Please answer YES or NO");
			answer = input.next();
		}
		return answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES");
	}

	@Override
	boolean wantFirst()
	{
		System.out.println("Top card of discard pile is: " + getGame().topOfStack().toString());
		System.out.println("Your current hand is: ");
		Set<Card> hand = getHand().getUnmatchedCards();
		for(Card c : hand) 
		{
			System.out.println("\t" + c.toString());
		}
		System.out.println("Want to take? (Y/N)");
		String answer = input.next();
		while(!(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES") || answer.equalsIgnoreCase("N") || answer.equalsIgnoreCase("NO")))
		{
			System.out.println("Please answer YES or NO");
			answer = input.next();
		}
		
		return answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("YES");
	}
	
}