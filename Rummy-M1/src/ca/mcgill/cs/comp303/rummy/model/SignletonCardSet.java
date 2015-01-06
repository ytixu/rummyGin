package ca.mcgill.cs.comp303.rummy.model;

import java.util.HashSet;

public class SignletonCardSet extends CardSet {

	public SignletonCardSet(Card pCard) {
		super(null);
		aSet.add(pCard);
	}

	@Override
	public boolean isGroup() {
		return true;
	}

	@Override
	public boolean isRun() {
		return true;
	}
	
	@Override
	public ICardSet add(ICardSet pSet) {
		return null;
	}
}
