package ca.mcgill.cs.comp303.rummy.model.gamePlayers;

import java.util.List;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Hand;
import ca.mcgill.cs.comp303.rummy.model.gameModel.GameModelPlayer;

/**
 * 
 * @author Yi Tian
 *
 */
public abstract class AbstractHumanPlayer extends AbstractPlayer 
{
	protected Hand aSelectedMatchSet;
	protected Card aSelectedDiscard;
	protected boolean pickDiscard;
	
	/**
	 * Constructor.
	 * @param pName
	 */
	public AbstractHumanPlayer(String pName) 
	{
		super(pName);
		aSelectedMatchSet = new Hand();
		aSelectedDiscard = null;
	}
	
	/**
	 * User selected card.
	 * @param pCard
	 */
	public void selectDiscard(Card pCard)
	{
		aSelectedDiscard = pCard;	
	}
}