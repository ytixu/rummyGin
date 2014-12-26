package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementors of this class should be immutable.
 */
public abstract class CardSet implements ICardSet
{
	protected ArrayList<Card> aSet;
	
	public CardSet(Set<Card> pCards){
		aSet = new ArrayList<Card>(pCards);
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
	
	@Override
	public int compareTo(ICardSet set) {
		if (this.isGroup() && set.isRun()) return 1;
		if (this.isRun() && set.isGroup()) return -1;
		int diff = aSet.get(0).hashCode() - set.iterator().next().hashCode();
		if (diff == 0) return aSet.size() - set.size();
		return diff;
	}
	
	public boolean equals(ICardSet set){
		if (isGroup() != set.isGroup() || size() != set.size()) return false;
		for (Card c: this){
			if (!set.contains(c)){
				return false;
			}
		}
		return true;
	}
}
