package ca.mcgill.cs.comp303.rummy.model.gameCM;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Card.Rank;
import ca.mcgill.cs.comp303.rummy.model.Card.Suit;
import ca.mcgill.cs.comp303.rummy.model.Hand;
import ca.mcgill.cs.comp303.rummy.model.HandException;
import ca.mcgill.cs.comp303.rummy.model.ICardSet;
import ca.mcgill.cs.comp303.rummy.model.gameModel.GameModelPlayer;
import ca.mcgill.cs.comp303.rummy.model.gamePlayers.AbstractHumanPlayer;

/**
 * 
 * @author ytixu
 *
 */
public class CMHumanPlayer extends AbstractHumanPlayer 
{
	private Scanner aInput = new Scanner(System.in);
	private Random aRnd = new Random();
	private String[] aErrorMessage = new String[]
			{
			"What the **** did you say?",
			"That's no valid input, you morron.",
			"Robillard will be disappointed of you..."
			};

	/**
	 * 
	 * @param pName
	 */
	public CMHumanPlayer(String pName) 
	{
		super(pName);		
	}
	
	/**
	 * 
	 */
	private void printError()
	{
		System.out.println(aErrorMessage[aRnd.nextInt()%3]);
	}
	
	
	/**
	 * 
	 * @param pCard
	 * @return
	 */
	private String cardToString(Card pCard)
	{
		int rank = pCard.getRank().ordinal() + 1;
		String sRank = "";
		switch (rank)
		{
			case 1:
				sRank = "A";
				break;
			case 11:
				sRank = "J";
				break;
			case 12:
				sRank = "Q";
				break;
			case 13:
				sRank = "K";
				break;
			default:
				sRank = Integer.toString(rank);
				break;
				
		}
		return String.join("", sRank, pCard.getSuit().toString().subSequence(0, 1));
	}
	
	/**
	 * 
	 * @param pStr
	 * @return
	 */
	private Card stringToCard(String pStr)
	{
		if (pStr.length() != 2)
		{
			return null;
		}
		Rank rank;
		switch (pStr.charAt(0))
		{
			case 'A':
				rank = Rank.ACE;
				break;
			case 'J':
				rank = Rank.JACK;
				break;
			case 'Q':
				rank = Rank.QUEEN;
				break;
			case 'K':
				rank = Rank.KING;
				break;
			default:
				try
				{
					rank = Rank.values()[pStr.charAt(0)-'1'];
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					return null;
				}
		}
		Suit suit;
		switch (pStr.charAt(1))
		{
			case 'S':
				suit = Suit.SPADES;
				break;
			case 'D':
				suit = Suit.DIAMONDS;
				break;
			case 'H':
				suit = Suit.HEARTS;
				break;
			case 'C':
				suit = Suit.CLUBS;
				break;
			default:
				return null;
		}
		return new Card(rank, suit);
	}
	
	/**
	 * 
	 * @param pCards
	 */
	private void displayHand()
	{
		for (Card c : aHand.getHand())
		{
			
			System.out.print(String.join("", cardToString(c),"\t"));
		}
		System.out.println("");
	}
	
	/**
	 * 
	 * @param pCard
	 * @return
	 */
	public boolean selectMatchSet(List<Card> pCard)
	{
		assert aSelectedMatchSet.size() == 0;
		assert pCard.size() <= Hand.HANDSIZE;
		for (Card c : pCard)
		{
			aSelectedMatchSet.add(c);
		}
		aSelectedMatchSet.autoMatch();
		return aSelectedMatchSet.getUnmatchedCards().size() == aSelectedMatchSet.size();
	}

	@Override
	public boolean play(GameModelPlayer pModel) 
	{
		displayHand();
		Card picked = null;
		Card pickedDiscard = null;
		while (picked == null)
		{
			System.out.println("The discard card is " + pModel.getDiscard().toString());
			System.out.println("Do you want to pick the discarded card? Y or N");
			String answer = aInput.nextLine();
			if (answer.equals("Y"))
			{
				picked = pModel.getDiscard();
				pickedDiscard = picked;
			}
			else if (answer.equals("N"))
			{
				picked = pModel.draw();
			}
			printError();
		}
		cardToString(picked);
		displayHand();
		
		Card toDiscard = null;
		while (toDiscard == null)
		{
			displayHand();
			System.out.println("Choose a card to discard.");
			String answer = aInput.nextLine();
			toDiscard = stringToCard(answer);
			if (toDiscard == null)
			{
				printError();
			}
		}
		pModel.setDiscard(toDiscard);
		discard(toDiscard);
		
		aHand.autoMatch();
		if (aHand.canKnock())
		{
			while (true)
			{
				System.out.println("You can knock. Do you want to knock? Y or N");
				String answer = aInput.nextLine();
				if (answer == "Y")
				{
					return true;
				}
				else if (answer == "N")
				{
					return false;
				} 
				printError();
			} 
		}
		return false;
	}
}
