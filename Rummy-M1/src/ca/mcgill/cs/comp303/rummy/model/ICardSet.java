package ca.mcgill.cs.comp303.rummy.model;


/**
 * Implementors of this class should be immutable.
 */
public interface ICardSet extends Iterable<Card>//, Comparable<ICardSet>
{
	/**
	 * @param pCard A card to check
	 * @return True if pCard is in this set
	 */
	boolean contains(Card pCard);

	/**
	 * @return The size of the group.
	 */
	int size();
	
	/**
	 * @return true if the object represents a group.
	 */
	boolean isGroup();
	
	/**
	 * @return true if the object represents a run.
	 */
	boolean isRun();
	
	/**
	 * @return the first card in the set.
	 */
	Card getFirst();
	
	/**
	 * @return the last card in the set.
	 */
	Card getLast();
	
	/**
	 * @return merge two cardSet if possible
	 * return null otherwise.
	 */
	ICardSet add(ICardSet pSet);
}
