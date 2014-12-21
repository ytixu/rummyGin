package ca.mcgill.cs.comp303fall2013.lecture03;

/**
  * An immutable description of a playing card.
  */
public final class Card implements Cloneable
{
//	public static void main(String[] args)
//	{
//		Card card = new Card(Rank.ACE, Suit.DIAMONDS);
//		
//		List<Card> list = new ArrayList<Card>();
//		list.add(card.clone());
//		Card newobject = new Card(card);
//	}
	/**
	 * Enum type representing the rank of the card.
	 */
	public enum Rank 
	{ ACE, TWO, THREE, FOUR, FIVE, SIX,
		SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING }
	
	/**
	 * Enum type representing the suit of the card.
	 */
	public enum Suit 
	{ CLUBS, DIAMONDS, SPADES, HEARTS;
		
		public Suit converse()
		{
			return values()[(ordinal()+2)%4];
		}
	}
	
	/**
	 * copy constructor.
	 * @param pCard
	 */
	public Card(Card pCard)
	{
		aRank = pCard.aRank;
		aSuit = pCard.aSuit;
	}

	private final Rank aRank;
	private final Suit aSuit;
	
	/**
	 * Create a new card object. 
	 * @param pRank The rank of the card.
	 * @param pSuit The suit of the card.
	 */
	public Card(Rank pRank, Suit pSuit )
	{
		aRank = pRank;
		aSuit = pSuit;
	}
	
	/**
	 * Obtain the rank of the card.
	 * @return An object representing the rank of the card.
	 */
	public Rank getRank()
	{
		return aRank;
	}
	
	public static void main(String[] args)
	{
		Card card = new Card(Rank.ACE, Suit.CLUBS);
		Card copy = new Card(card);
	}
	
	/**
	 * Obtain the suit of the card.
	 * @return An object representing the suit of the card 
	 */
	public Suit getSuit()
	{
		return aSuit;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * @return See above.
	 */
	public String toString()
	{
		return aRank + " of " + aSuit;
	}
	
	public Card clone()
	{
		return new Card(this);
	}


	/**
	 * Two cards are equal if they have the same suit and rank.
	 * @param pCard The card to test.
	 * @return true if the two cards are equal
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals( Object pCard ) 
	{
		if( pCard == null )
		{
			return false;
		}
		if( pCard == this )
		{
			return true;
		}
		if( pCard.getClass() != getClass() )
		{
			return false;
		}
		return (((Card)pCard).getRank() == getRank()) && (((Card)pCard).getSuit() == getSuit());
	}

	/** 
	 * The hashcode for a card is the suit*13 + that of the rank (perfect hash).
	 * @return the hashcode
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() 
	{
		return getSuit().ordinal() * Rank.values().length + getRank().ordinal();
	}
}
