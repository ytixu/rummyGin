package ca.mcgill.cs.comp303.rummy.model;

import java.util.Set;

public class GroupSet extends CardSet{
	
	public GroupSet(Set<Card> pCards) {
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

}
