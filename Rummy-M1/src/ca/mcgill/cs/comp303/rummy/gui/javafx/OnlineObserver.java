package ca.mcgill.cs.comp303.rummy.gui.javafx;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import ca.mcgill.cs.comp303.rummy.model.ICardSet;
import ca.mcgill.cs.comp303.rummy.model.gameModel.GameModelLogger;
import ca.mcgill.cs.comp303.rummy.model.gameModel.GameObserver;

/**
 * Observer that write on the GUI.
 * @author ytixu
 *
 */
public class OnlineObserver implements GameObserver 
{
	private final int aX = 300;
	private final int aY = 200;
	private TextField aDisplayer = new TextField();
	private Label[] aPlayerScores;
	
	/**
	 * Add the text displayer to the GUI.
	 * @param pPane
	 */
	public void displayDisplay(Pane pPane)
	{
		aDisplayer.setMinSize(aX, aY);
		aDisplayer.setDisable(true);
		aDisplayer.setAlignment(Pos.BOTTOM_LEFT);
		for (Label l : aPlayerScores)
		{
			pPane.getChildren().add(l);
		}
		pPane.getChildren().add(aDisplayer);
	}
	
	private void write(String text)
	{
		aDisplayer.setText(aDisplayer.getText() + text + "\n");
	}
	
	@Override
	public void logPlayed(GameModelLogger pEngine) 
	{
		write(pEngine.getTrun() + " played");
		Object knock = pEngine.getKnocked();
		Object layout = pEngine.getLayout();
		if (layout != null)
		{
			write("Layout.");
			for (ICardSet s : (Set<ICardSet>) layout)
			{
				write(s.toString());
			}
		}
		else if (knock != null)
		{
			write("Player knocked.");
			for (ICardSet s : (Set<ICardSet>) knock)
			{
				write(s.toString());
			}
		}
		else
		{
			write("The top of the dicard pile is " + pEngine.getDiscard().toString());
		}
	}

	@Override
	
	public void logEndGame(GameModelLogger pEngine) 
	{
		HashMap<String, Integer> scores = (HashMap<String, Integer>)pEngine.getScore();
		write("Game Ended");
		int i = 0;
		for (Entry<String, Integer> s : scores.entrySet())
		{
			String[] data = aPlayerScores[i].getText().split(" : ");
			int score = Integer.parseInt(data[1]);
			write(s.getKey() + " +" + (s.getValue() - score));
			aPlayerScores[i].setText(data[0] + " : " + s.getValue());
			i++;
		}
	}

	/**
	 * @pre pEngine.getPlayers() returns an array of length 2
	 */
	@Override
	public void logStartGame(GameModelLogger pEngine) 
	{
		HashMap<String, Integer> score = (HashMap<String, Integer>)pEngine.getScore();
		aPlayerScores = new Label[score.size()];
		int i = 0;
		for (Entry<String, Integer> s : score.entrySet())
		{
			aPlayerScores[i] = new Label(s.getKey() + " : " + s.getValue());
			i++;
		}
		String[] names = pEngine.getPlayers();
		write(names[0] + " vs " + names[1]); 
	}
}
