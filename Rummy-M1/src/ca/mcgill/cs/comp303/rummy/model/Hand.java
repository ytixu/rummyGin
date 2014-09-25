package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;


/**
 * Models a hand of 10 cards. The hand is not sorted. Not threadsafe.
 * The hand is a set: adding the same card twice will not add duplicates
 * of the card.
 * @inv size() > 0
 * @inv size() <= HAND_SIZE
 */
public class Hand
{	
	private HashMap<Card, Boolean> aHand;
	
	/*
	 * A set of CardSet
	 * It tells which CardSets gives the optimal score
	 * But at each time a new card is added (and another card is removed), this must be rebuilt
	 * Might think of a better way to keep previous solution
	 */
	private HashMap<CardSet, Boolean> aMatchedSet;
	
	private boolean aAutomatched; // state, whether automatched is called
	
	/**
	 * Creates a new, empty hand.
	 */
	public Hand()
	{
		aMatchedSet = new HashMap<CardSet, Boolean>();
		aHand = new HashMap<Card, Boolean>();
		aAutomatched = false; 
	}
	
	/*
	 * Get a list of the card in the hand
	 */
	public Set<Card> getHand(){
		return aHand.keySet();
	}
	
	/*
	 * Prepare to call autoMatch again
	 */
	public void reset(){
		aMatchedSet.clear();
		for (Card c : aHand.keySet()){
			aHand.put(c, false);
		}
		aAutomatched = false; // now we can call autoMatch again
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
		assert(pCard != null);
		if (contains(pCard)) throw new HandException("Card already in hand");
		if (isComplete()) throw new HandException("Hand is complete.");
		aHand.put(pCard, false);
		
		if (aAutomatched) reset();
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
		assert(pCard != null);
		aHand.remove(pCard);
		
		if (aAutomatched) reset();
	}
	
	/**
	 * @return True if the hand is complete.
	 */
	public boolean isComplete()
	{
		if (aHand.size() < 10) return false; 
		return true; 
	}
	
	/**
	 * Removes all the cards from the hand.
	 */
	public void clear()
	{
		aHand.clear();
	}
	
	
	/**
	 * @return A copy of the set of matched sets
	 */
	public Set<CardSet> getMatchedSets() // changed input type Set<ICardSet>
	{
		HashSet<CardSet> matchedSets = new HashSet<CardSet>();
		for (Entry<CardSet, Boolean> cs : aMatchedSet.entrySet()){
			if ((boolean) cs.getValue()){
				matchedSets.add(cs.getKey());
			}
		}
		
		return matchedSets;
	}
	
	/**
	 * @return A copy of the set of unmatched cards.
	 */
	public Set<Card> getUnmatchedCards()
	{
		Set<Card> cardSet = new HashSet<Card>();
		for (Entry<Card, Boolean> cs : aHand.entrySet()){
			if (! (boolean) cs.getValue()){
				cardSet.add(cs.getKey());
			}
		}
		return cardSet; 
	}
	
	/**
	 * @return The number of cards in the hand.
	 */
	public int size()
	{
		return aHand.size();
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
		for (Card c : aHand.keySet()){
			if (c.equals(pCard)) return true;
		}
		return false;
	}
	
	/**
	 * @return The total point value of the unmatched cards in this hand.
	 */
	public int score()
	{
		int score = 0;
		for (Entry<Card, Boolean> cs : aHand.entrySet()){
			if (! (boolean) cs.getValue()){
				score += cs.getKey().getRank().ordinal();
			}
		}
		return score;
	}
	
	/**
	 * Creates a group of cards of the same rank.
	 * @param pCards The cards to groups
	 * @pre pCards != null
	 * @throws HandException If the cards in pCard are not all unmatched
	 * cards of the hand or if the group is not a valid group.
	 */
	public void createGroup( Set<Card> pCards )
	{
		assert(pCards != null);
		ArrayList<Card> sortedHand = new ArrayList<Card>(pCards);
		Collections.sort(sortedHand, new Comparator<Card>(){
            public int compare(Card c1,Card c2){
                return c2.getRank().ordinal() -  c1.getRank().ordinal();
            }
        });
		for (int i=0; i<sortedHand.size() - 2; i++){
			for (int j=i+2; j<sortedHand.size(); j++){
				CardSet tryMatch = new CardSet((ArrayList<Card>) sortedHand.subList(i, j));
				if (tryMatch.isGroup()){
					aMatchedSet.put(tryMatch, false);
				}else{
					break;
				}
			}
		}
	}
	
	/**
	 * Creates a run of cards of the same suit.
	 * @param pCards The cards to group in a run
	 * @pre pCards != null
	 * @throws HandException If the cards in pCard are not all unmatched
	 * cards of the hand or if the group is not a valid group.
	 */
	public void createRun( Set<Card> pCards )
	{
		assert(pCards != null);
		ArrayList<Card> sortedHand = new ArrayList<Card>(pCards);
		Collections.sort(sortedHand);
		for (int i=0; i<sortedHand.size() - 2; i++){
			for (int j=i+2; j<sortedHand.size(); j++){
				CardSet tryMatch = new CardSet((ArrayList<Card>) sortedHand.subList(i, j));
				if (tryMatch.isRun()){
					aMatchedSet.put(tryMatch, false);
				}else{
					break;
				}
			}
		}
	}
	
	/**
	 * Calculates the matching of cards into groups and runs that
	 * results in the lowest amount of points for unmatched cards.
	 */
	public void autoMatch()
	{
		if (aAutomatched) return; // matched already done.
		
		createGroup(aHand.keySet());
		createRun(aHand.keySet());
		
		// minimization algo
		int minScore = Integer.MAX_VALUE;
		ArrayList<CardSet> cardSets = new ArrayList<CardSet>(aMatchedSet.keySet());
		@SuppressWarnings("unchecked")
		HashMap<Card, Boolean> matchedCards = (HashMap<Card, Boolean>) aHand.clone();
		@SuppressWarnings("unchecked")
		HashMap<CardSet, Boolean> usedCardSet = (HashMap<CardSet, Boolean>) aMatchedSet.clone();
		
		for (int i=0; i<cardSets.size(); i++){
			for (CardSet s : cardSets.subList(i+1, cardSets.size()-1)){
				// check if all cards are unmatched
				boolean isFree = true;
				for (Card c : s) if ((boolean) matchedCards.get(c)) break;
				// if a card is matched, skip
				if (! isFree) continue;
				// else add this cardset to solution
				for (Card c : s) matchedCards.put(c, true);
				usedCardSet.put(s, true);
			}
			// update and reset
			int score = score();
			if (score < minScore){
				minScore = score;
				for (Entry<CardSet, Boolean> cb : usedCardSet.entrySet()){
					aMatchedSet.put(cb.getKey(), cb.getValue());
					usedCardSet.put(cb.getKey(), false);
				}
				for (Entry<Card, Boolean> cb : matchedCards.entrySet()){
					aHand.put(cb.getKey(), cb.getValue());
					matchedCards.put(cb.getKey(), false);
				}
			}else{
				for (CardSet s : usedCardSet.keySet()) usedCardSet.put(s, false);
				for (Card c : matchedCards.keySet()) matchedCards.put(c, false);
			}
		}
		
	}
}
