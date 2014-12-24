package ca.mcgill.cs.comp303.rummy.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementors of this class should be immutable.
 */
public abstract class CardSet implements ICardSet
{
	private HashSet<Card> aSet;
	
	public CardSet(Set<Card> pCards){
		aSet = new HashSet<Card>(pCards);
	}
	
	@Override
	public Iterator<Card> iterator() {
		return aSet.iterator();
	}
	
	/**
	 * @param pCard A card to check
	 * @return True if pCard is in this set
	 */
	public boolean contains(Card pCard) {
		assert (pCard != null);
		return aSet.contains(pCard);
	}
	
	/**
	 * @return The size of the group.
	 */
	public int size() {
		return aSet.size();
	}
	
	/**
	 * @return true if the object represents a group.
	 */
	public abstract boolean isGroup();
	
	/**
	 * @return true if the object represents a run.
	 */
	public abstract boolean isRun();
	
	/**
	 * For printing.
	 * @return the string to print
	 */
	public String toString(){
		return aSet.toString();
	}
}
