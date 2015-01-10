package gameEngine;

import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.ICardSet;
/**
 * Simplified GameEngine for testing Player.
 *
 */
public class GameEngineTest implements IGameEngineSetter {

	private Card nextCardInDeck;
	private Card aDiscard;
	
	public GameEngineTest(){
		nextCardInDeck = null;
		aDiscard = null;
	}
	
	public void setCards(Card inDeck, Card discard){
		nextCardInDeck = inDeck;
		aDiscard = discard;
	}
	
	@Override
	public Card drawFromDeck() {
		System.out.println("Drawing from deck, " + nextCardInDeck.toString());
		return nextCardInDeck;
	}

	@Override
	public Card peekDiscard() {
		return aDiscard;
	}

	@Override
	public Card takeDiscard() {
		System.out.println("Take discard, " + aDiscard.toString());
		return aDiscard;
	}

	@Override
	public void discard(Card pCard) {
		System.out.println("Discarding card, " + pCard.toString());
		aDiscard = pCard;
	}

	@Override
	public void knock() {
		System.out.println("Playing knocking.");
		return;
	}

	@Override
	public void layout(Set<ICardSet> aSets) {
		return;
	}

}
