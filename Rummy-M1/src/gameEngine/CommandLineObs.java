package gameEngine;

public class CommandLineObs implements GameObserver {

	@Override
	public void update(IGameEngineGetter pGameEngine) {
		System.out.println(pGameEngine.getPlayed());
		for (Player p: pGameEngine.getPlayers()){
			System.out.print(p.toString() + "\t");
			System.out.println(p.printHand());
		}
	}

}
