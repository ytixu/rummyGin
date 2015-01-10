package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;
import java.util.List;

public class RunSet extends CardSet{
	
	public RunSet(List<Card> pCards) {
		super(pCards);
	}

	@Override
	public boolean isGroup() {
		return false;
	}

	@Override
	public boolean isRun() {
		return true;
	}
	
	@Override
	public ICardSet add(ICardSet pSet) {
		if (pSet.isRun() && getFirst().getSuit().equals(pSet.getFirst().getSuit())){
			if (getFirst().getRank().ordinal() - 1 == pSet.getLast().getRank().ordinal()){
				ArrayList<Card> cards = new ArrayList<Card>();
				for (Card c : pSet){
					cards.add(c);
				}
				cards.addAll(aSet);
				return new RunSet(cards);
			}
			if (getLast().getRank().ordinal() + 1 == pSet.getFirst().getRank().ordinal()){
				ArrayList<Card> cards = new ArrayList<Card>(aSet);
				for (Card c : pSet){
					cards.add(c);
				}
				return new RunSet(cards);
			}
		}
		return null;
	}
}
