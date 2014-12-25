package gameEngine;
/**
 * Pull technique. 
 *
 */
public interface GameObserver {
	/**
	 * Update method when notifyObserver is called.
	 * @param pGameEngine
	 */
	void update(IGameEngineGetter pGameEngine);
}
