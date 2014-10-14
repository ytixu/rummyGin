package ca.mcgill.cs.comp303.rummy.model;
/**
 * 
 * @author ytixu
 *
 */
public interface GameObserver {
	/**
	 * Call back function.
	 * @param pEngine
	 */
	void log(GameEngine pEngine);
}
