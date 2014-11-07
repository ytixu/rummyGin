package ca.mcgill.cs.comp303.rummy.gui.javafx;

import ca.mcgill.cs.comp303.rummy.model.gameModel.GameModelPlayer;
import ca.mcgill.cs.comp303.rummy.model.gamePlayers.AbstractHumanPlayer;

/**
 * 
 * @author ytixu
 *
 */
public class FXHumanPlayer extends AbstractHumanPlayer 
{

	public FXHumanPlayer(String pName) 
	{
		super(pName);
		GameButtons.initialize(this);
	}

	@Override
	public boolean play(GameModelPlayer pModel) 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	public void knock()
	{
		
	}

}
