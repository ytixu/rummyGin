package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Models a hand of 10 cards. The hand is not sorted. Not threadsafe.
 * The hand is a set: adding the same card twice will not add duplicates
 * of the card.
 * @inv size() > 0
 * @inv size() <= HAND_SIZE
 */
public class Hand implements Iterable<Card>
{
	public static final int HANDSIZE = 10;
	private static final int MAX_POINT = 10;
	
	private HashMap<Card, Boolean> aCards;
	private HashSet<ICardSet> aMatchSet;
	private boolean autoMatchCalled;
	
	/**
	 * Creates a new, empty hand.
	 */
	public Hand()
	{
		aCards = new HashMap<Card, Boolean>();
		aMatchSet = null;
		autoMatchCalled = false;
	}
	
	/**
	 * Adds pCard to the list of unmatched cards.
	 * If the card is already in the hand, it is not added.
	 * @param pCard The card to add.
	 * @throws HandException if the hand is complete.
	 * @throws HandException if the card is already in the hand.
	 * @pre pCard != null
	 */
	public void add( Card pCard )
	{
		assert (pCard != null);
		if (isComplete()) throw new HandException("Complete hand.");
		if (contains(pCard)) throw new HandException("Already in hand.");
		aCards.put(pCard, false);
		autoMatchCalled = false;
	}
	
	/**
	 * Remove pCard from the hand and break any matched set
	 * that the card is part of. Does nothing if
	 * pCard is not in the hand.
	 * @param pCard The card to remove.
	 * @pre pCard != null
	 */
	public void remove( Card pCard )
	{
		assert (pCard != null);
		aCards.remove(pCard);
		autoMatchCalled = false;
	}
	
	/**
	 * @return True if the hand is complete.
	 */
	public boolean isComplete()
	{
		return aCards.size() == HANDSIZE;
	}
	
	/**
	 * Removes all the cards from the hand.
	 */
	public void clear()
	{
		aCards.clear();
	}
	
	/**
	 * Reset automatch
	 */
	public void reset(){
		for (Card c : this){
			aCards.put(c, false);
		}
		aMatchSet = null;
	}
	
	/**
	 * @return A copy of the set of matched sets
	 */
	public Set<ICardSet> getMatchedSets()
	{
		return aMatchSet;
	}
	
	/**
	 * @return A copy of the set of unmatched cards.
	 */
	public Set<Card> getUnmatchedCards()
	{
		HashSet<Card> cards = new HashSet<Card>(aCards.keySet());
		for (Card c : this){
			if (aCards.get(c)){
				cards.remove(c);
			}
		}
		return cards;
	}
	
	/**
	 * @return The number of cards in the hand.
	 */
	public int size()
	{
		return aCards.size();
	}
	
	/**
	 * Determines if pCard is already in the hand, either as an
	 * unmatched card or as part of a set.
	 * @param pCard The card to check.
	 * @return true if the card is already in the hand.
	 * @pre pCard != null
	 */
	public boolean contains( Card pCard )
	{
		assert (pCard != null);
		return aCards.containsKey(pCard);
	}
	
	/**
	 * @return The total point value of the unmatched cards in this hand.
	 */
	public int score()
	{
		int score = 0;
		for (Map.Entry pair : aCards.entrySet()){
			if (! (Boolean) pair.getValue()){
				if (((Card) pair.getKey()).getRank().ordinal() >= MAX_POINT){
					score += MAX_POINT;
				}else{
					score += ((Card) pair.getKey()).getRank().ordinal();
				}
			}
		}
		return score;
	}
	
	/**
	 * Create groups
	 * @return a set of groups
	 */
	public Set<ICardSet> createGroups()
	{
		Comparator<Card> groupComparator = new Comparator<Card>(){
			@Override
			public int compare(Card arg0, Card arg1) {
				return arg0.getRank().compareTo(arg0.getRank());
			}
		};
		ArrayList<Card> sorted = new ArrayList<Card>(aCards.keySet());
		sorted.sort(groupComparator);
		HashSet<ICardSet> sets = new HashSet<ICardSet>();
		for (int i=0; i<sorted.size(); i++){
			for (int j=i+1; j<sorted.size(); j++){
				if (groupComparator.compare(sorted.get(i), sorted.get(j)) != 0){
					if (i+2<j){
						HashSet<Card> temp = new HashSet<Card>();
						for (int k = 1; k<j; k++){
							temp.add(sorted.get(k));
						}
						sets.add(new GroupSet(temp));
					}else{
						break;
					}
				}
				
			}
		}
		return sets;
	}
	
	/**
	 * Creates runs
	 * @return a set of runs
	 */
	public Set<ICardSet> createRun()
	{
		Comparator<Card> runComparator = new Comparator<Card>(){
			@Override
			public int compare(Card arg0, Card arg1) {
				return arg0.compareTo(arg1);
			}
		};
		ArrayList<Card> sorted = new ArrayList<Card>(aCards.keySet());
		sorted.sort(runComparator);
		HashSet<ICardSet> sets = new HashSet<ICardSet>();
		for (int i=0; i<sorted.size(); i++){
			for (int j=i+1; j<sorted.size(); j++){
				if (runComparator.compare(sorted.get(j), sorted.get(i)) != 1){
					if (i+2<j){
						HashSet<Card> temp = new HashSet<Card>();
						for (int k = 1; k<j; k++){
							temp.add(sorted.get(k));
						}
						sets.add(new RunSet(temp));
					}else{
						break;
					}
				}
				
			}
		}
		return sets;
	}
	
	private HashMap<Integer, HashSet<ICardSet>> recursiveAutoMatch(Stack<ICardSet> sets){
		if (sets.isEmpty()) return new HashMap<Integer, HashSet<ICardSet>>();
		ICardSet first = sets.pop();
		int score = 0;
		for (Card c : first){
			if (c.getRank().ordinal() >= MAX_POINT){
				score += MAX_POINT;
			}else{
				score += c.getRank().ordinal();
			}
		}
		HashMap<Integer, HashSet<ICardSet>> withFirst = recursiveAutoMatch(sets);
		if (withFirst.isEmpty()){
			withFirst.put(score, new HashSet<ICardSet>(Arrays.asList(first)));
			withFirst.put(0, new HashSet<ICardSet>());
		}else{
			HashMap<Integer, HashSet<ICardSet>> temp = new HashMap<Integer, HashSet<ICardSet>>();
			for (Map.Entry pair : withFirst.entrySet()){
				boolean contains = false;
				for (ICardSet s : (HashSet<ICardSet>) pair.getValue()){
					for (Card c : s){
						if (first.contains(c)){
							contains = true;
							break;
						}
					}
					if (contains) break;
				}
				if (!contains){
					HashSet<ICardSet> newSet = new HashSet<ICardSet>((HashSet<ICardSet>)pair.getKey());
					newSet.add(first);
					temp.put((Integer) pair.getKey() + score, newSet);
				}
			}
			withFirst.putAll(temp);
		}
		return withFirst;
	}
	
	/**
	 * Calculates the matching of cards into groups and runs that
	 * results in the lowest amount of points for unmatched cards.
	 */
	public void autoMatch()
	{
		if (autoMatchCalled) return; // automatch already called 
		if (aMatchSet != null) reset();
		Stack<ICardSet> sets = new Stack<ICardSet>();
		sets.addAll(createRun());
		sets.addAll(createGroups());
		HashMap<Integer, HashSet<ICardSet>> allCombos = recursiveAutoMatch(sets);
		
		// maximize points in matches = minimize points in deadwook
		int maxPoint = 0;
		HashSet<ICardSet> optimal = null; // TODO: ties
		for (Map.Entry pair : allCombos.entrySet()){
			if (maxPoint < (Integer) pair.getKey()){
				maxPoint = (Integer) pair.getKey();
				optimal = (HashSet<ICardSet>) pair.getValue();
			}
		}
		if (optimal == null) return;
		for (ICardSet s : optimal){
			for (Card c : s){
				aCards.put(c, true);
			}
		}
		aMatchSet = optimal;
	}

	@Override
	public Iterator<Card> iterator() {
		return aCards.keySet().iterator();
	}
}
