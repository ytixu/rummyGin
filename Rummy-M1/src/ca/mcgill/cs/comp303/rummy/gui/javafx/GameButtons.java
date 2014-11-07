package ca.mcgill.cs.comp303.rummy.gui.javafx;

import java.util.HashMap;

import ca.mcgill.cs.comp303.rummy.model.gameModel.GameEngine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Save, load, new game, ... buttons.
 * @author Yi Tian
 *
 */
public class GameButtons 
{
	private static HashMap<BntName, Button> bottons = new HashMap<BntName, Button>();
	public enum BntName{
		NEW, SAVE, LOAD, KNOCK, PASS
	}
	
	public static void initialize(GameEngine pEngine, Gui pGui)
	{
		Button newGame = new Button("New Game");
		newGame.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent pEvent)
			{
				pEngine.newGame();
				pGui.reset();
			}
		});
		bottons.put(BntName.NEW, newGame);
		
		Button save = new Button("Save");
		save.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent pEvent)
			{
				pEngine.save();
			}
		});
		bottons.put(BntName.SAVE, save);
		
		Button load = new Button("Load");
		load.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent pEvent)
			{
				pEngine.load();
				// TODO: refresh gui
			}
		});
		bottons.put(BntName.LOAD, load);
		
	}
	
	public static void initialize(FXHumanPlayer pPlayer)
	{
		Button knock = new Button("Knock");
		knock.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent pEvent)
			{
				pPlayer.knock();
				// TODO: refresh gui
			}
		});
		bottons.put(BntName.KNOCK, knock);
		
		Button pass = new Button("Pass");
		pass.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent pEvent)
			{
				// TODO
			}
		});
		bottons.put(BntName.PASS, pass);
	}
	
	/**
	 * @pre bottons is initialized
	 * @param pName
	 * @return
	 */
	public static Button getButton(BntName pName)
	{
		return bottons.get(pName);
	}
}
