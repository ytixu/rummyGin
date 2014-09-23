package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * A immutable set of cards. 
 * @author Yi Tian
 *
 */
public class CardSet implements ICardSet {
	
	private ArrayList<Card> aSet;
	
	/**
	 * Creates a new, card set.
	 * @param cards a set of cards
	 */
	public CardSet(ArrayList<Card> cards){
		//assume that the cards are distinct
		aSet = cards;
	}
	
	@Override
	public Iterator<Card> iterator() {
		return aSet.iterator();
	}

	@Override
	public boolean contains(Card pCard) {
		for (Card c : this){
			if (c.equals(pCard)) return true;
		}
		return false;
	}

	@Override
	public int size() {
		return aSet.size();
	}

	@Override
	public boolean isGroup() {
		if (size() < 3) return false;
		// linear scan
		int rank = -1;
		for (Card c : this){
			if (rank == -1) rank = c.getRank().ordinal();
			else{
				if (rank != c.getRank().ordinal()) return false;
			}
		}
		return true;
	}

	@Override
	public boolean isRun() {
		if (size() < 3) return false;
		// sort the hashcode
		int[] hashcode = new int[size()];
		Iterator<Card> cardIter = iterator();
		for(int i=0; cardIter.hasNext(); i++){
			hashcode[i] = cardIter.next().hashCode();
		}
		Arrays.sort(hashcode);
		// linear scan
		for(int i=1; i<hashcode.length; i++){
			if (hashcode[i-1] + 1 != hashcode[i]) return false;
		}
		return true;
	}

}
