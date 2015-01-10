package ca.mcgill.cs.comp303.rummy.model;

import java.util.ArrayList;
import java.util.List;

public class GroupSet extends CardSet{
	
	public GroupSet(List<Card> pCards) {
		super(pCards);
	}

	@Override
	public boolean isGroup() {
		return true;
	}

	@Override
	public boolean isRun() {
		return false;
	}

	@Override
	public ICardSet add(ICardSet pSet) {
		if (pSet.isGroup() && getFirst().getRank() == pSet.getFirst().getRank()){
				
			ArrayList<Card> cards = new ArrayList<Card>(aSet);
			for (Card c : pSet){
				cards.add(c);
			}
			return new GroupSet(cards);
		}
		return null;
	}
}
