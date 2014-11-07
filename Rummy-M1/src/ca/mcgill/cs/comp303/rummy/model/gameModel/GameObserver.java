package ca.mcgill.cs.comp303.rummy.model.gameModel;


/**
 * 
 * @author ytixu
 *
 */
public interface GameObserver 
{
	/**
	 * 
	 * @param pEngine
	 */
	void logStartGame(GameModelLogger pEngine);
	
	/**
	 * Call back function.
	 * @param pEngine
	 */
	void logPlayed(GameModelLogger pEngine);
	
	/**
	 * 
	 * @param pEngine
	 */
	void logEndGame(GameModelLogger pEngine);
}
