package ca.mcgill.cs.comp303.rummy.model;

public class SignletonCardSet extends CardSet {

	public SignletonCardSet(Card pCard) {
		super(null);
		aSet.add(pCard);
	}

	@Override
	public boolean isGroup() {
		return false;
	}

	@Override
	public boolean isRun() {
		return false;
	}
}
