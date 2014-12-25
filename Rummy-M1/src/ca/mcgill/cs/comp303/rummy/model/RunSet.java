package ca.mcgill.cs.comp303.rummy.model;

import java.util.Set;

public class RunSet extends CardSet{
	
	public RunSet(Set<Card> pCards) {
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
}