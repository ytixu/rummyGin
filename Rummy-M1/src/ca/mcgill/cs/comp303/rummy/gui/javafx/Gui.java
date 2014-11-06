package ca.mcgill.cs.comp303.rummy.gui.javafx;

import java.io.File;

import ca.mcgill.cs.comp303.rummy.model.gameModel.GameEngine;
import ca.mcgill.cs.comp303.rummy.model.gamePlayers.Player;
import ca.mcgill.cs.comp303.rummy.model.gamePlayers.RandomPlayer;
import ca.mcgill.cs.comp303.rummy.model.gamePlayers.RobotPlayer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
	private static final int PADDING = 10;
	private static final String DEFAULTNAME = "G";
	
	private static final String CHOOSENAME = "Please choose a name.";
	private static final String CHOOSELEVEL = "Please choose a level.";
	
	private final GameEngine CHICKEN = new GameEngine();
	private final StackPane ROOT = new StackPane();
	
	private Player aHumanPlayer;
	private OnlineObserver aLogDisplay;
	
	private enum Level
	{
		WOBBUFFET, PSYDUCK
	}

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
	private Pane getPlayer()
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
				lev.setSelected(true);
			}
			levelPane.getChildren().add(lev);
			lev.setToggleGroup(levGroup);
		}
		
		TextField nameInput = new TextField(CHOOSENAME);
		nameInput.setMaxSize(200, 20);
		
		Button submit = new Button("Enter");
		Label error = new Label();
		submit.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent pEvent)
			{
				ToggleButton bnt = (ToggleButton) levGroup.getSelectedToggle();
				if (bnt == null)
				{
					error.setText(CHOOSELEVEL);
					return;
				}
				Level level = null;
				for (Level l : Level.values())
				{
					if (l.name().equals(bnt.getText()))
					{
						level = l;
					}
				}
				assert level != null;
				String name = nameInput.getText();
				System.out.println(name.equals(CHOOSENAME));
				if (name.equals("") || name.equals(CHOOSENAME))
				{
					name = DEFAULTNAME;
				}
				aHumanPlayer = new FXHumanPlayer(name);
				if (level.equals(Level.WOBBUFFET))
				{
					CHICKEN.setPlayers(aHumanPlayer, new RandomPlayer());
				}
				if (level.equals(level.PSYDUCK))
				{
					CHICKEN.setPlayers(aHumanPlayer, new RobotPlayer());
				}
				drawEverything();
			}
		});
		
		VBox pane = new VBox(BOX_SPACE);
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().add(levelPane);
		pane.getChildren().add(nameInput);
		pane.getChildren().add(error);
		pane.getChildren().add(submit);
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
	
	/*
	 * Draw everything that are not related to the cards. 
	 */
	private void drawEverything()
	{	
		HBox pane = new HBox(BOX_SPACE);
		VBox leftBar = new VBox(BOX_SPACE);
		leftBar.setAlignment(Pos.BOTTOM_LEFT);
		aLogDisplay.displayDisplay(leftBar);
		leftBar.getChildren().add(getMenu());
        
		pane.getChildren().add(leftBar);
		ROOT.getChildren().clear();
		ROOT.getChildren().add(pane);
	}
	
	@Override
    public void start(Stage pStage) 
	{	
		// initialization
		pStage.setTitle("Gin Rummy");
		aLogDisplay = new OnlineObserver();
		CHICKEN.addObservers(aLogDisplay);
		ROOT.setPadding(new Insets(PADDING));
		// get name of player
		ROOT.getChildren().add(getPlayer());
		
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

