package ca.mcgill.cs.comp303.rummy.gui.javafx;

import java.io.File;

import ca.mcgill.cs.comp303.rummy.model.gameModel.GameEngine;
import ca.mcgill.cs.comp303.rummy.model.gamePlayers.Player;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
/**
 * 
 * @author ytixu
 *
 */
public class Gui extends Application
{
	private static final int SIZE_X = 1600;
	private static final int SIZE_Y = 800;
	private static final int BOX_SPACE = 5;
	
	private final GameEngine CHICKEN = new GameEngine();
	private final StackPane ROOT = new StackPane();
	
	private Player aHumanPlayer;
	
	private enum Level{
		WOBBUFFET, PSYDUCK
	}
	private Level aLevel;

	/*
	 * setup initial positions of the cards
	 */
	private void reset()
	{
		//TODO
	}
	
	/**
	 * get name
	 */
	private Pane initialize()
	{
		HBox levelPane = new HBox(BOX_SPACE);
		levelPane.setAlignment(Pos.CENTER);
		levelPane.getChildren().add(new Label("Choose an opponent:"));
		Level[] levels = Level.values();
		ToggleGroup levGroup = new ToggleGroup();
		for (Level l : Level.values())
		{
			ToggleButton lev = new ToggleButton(l.name());
			if (l.ordinal() == 0)
			{
				aLevel = l;
				lev.setSelected(true);
			}
			levelPane.getChildren().add(lev);
			lev.setToggleGroup(levGroup);
		}
		
		TextField nameInput = new TextField("Choose a name.");
		nameInput.setMaxSize(200, 20);
		
		Button submit = new Button("Enter");
		Label error = new Label();
		submit.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent pEvent)
			{
				ToggleButton bnt = (ToggleButton) levGroup.getSelectedToggle();
				if (bnt == null)
				{
					error.setText("Please choose a level.");
					return;
				}
				for (Level l : Level.values())
				{
					if (l.name().equals(bnt.getText()))
					{
						aLevel = l;
					}
				}
				aHumanPlayer = new FXHumanPlayer(nameInput.getText());
				drawEverything();
			}
		});
		
		VBox pane = new VBox(BOX_SPACE);
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().add(levelPane);
		pane.getChildren().add(nameInput);
		pane.getChildren().add(submit);
		pane.getChildren().add(error);
		return pane;
	}
	
	/*
	 * create menu
	 */
	private Pane getMenu()
	{
		VBox menu = new VBox(BOX_SPACE);
		Button newGame = new Button("New Game");
		newGame.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent pEvent)
			{
				CHICKEN.newGame();
				reset();
			}
		});
		menu.getChildren().add(newGame);
		// TODO: add more buttons
		return menu;
	}
	
	private void drawEverything()
	{
		ROOT.setAlignment(Pos.TOP_LEFT);
		Pane manu = getMenu();
        
		ROOT.getChildren().clear();
		ROOT.getChildren().add(manu);
	}
	
	@Override
    public void start(Stage pStage) 
	{
		pStage.setTitle("G");
		// get name of player
		ROOT.getChildren().add(initialize());
		
		// css
//        File f = new File("style.css");
//        ROOT.getStylesheets().clear();
//        ROOT.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        // size
        pStage.setScene(new Scene(ROOT, SIZE_X, SIZE_Y));
        pStage.show();
    }
	
	/**
	 * Launcher.
	 * @param pElephant
	 */
	public static void main(String[] pElephant) 
	{
        launch(pElephant);
    }
}

