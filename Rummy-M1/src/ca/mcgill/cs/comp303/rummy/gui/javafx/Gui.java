package ca.mcgill.cs.comp303.rummy.gui.javafx;

import java.io.File;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.gui.javafx.GameButtons.BntName;
import ca.mcgill.cs.comp303.rummy.model.Card;
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
	private static final int SIZE_X = 1200;
	private static final int SIZE_Y = 800;
	static final int BOX_SPACE = 5;
	static final int PADDING = 30;
	private static final String DEFAULTNAME = "G";
	
	private static final String CHOOSENAME = "Please choose a name.";
	private static final String CHOOSELEVEL = "Please choose a level.";
	
	private final GameEngine CHICKEN = new GameEngine();
	private final StackPane ROOT = new StackPane();
	
	private OnlineObserver aLogDisplay;
	private CardDisplayer aCardDisplay;
	
	private enum Level
	{
		WOBBUFFET, PSYDUCK
	}
	
	private static Player aHumanPlayer;
	
	/**
	 * @pre aHumanPlayer != null
	 * @return
	 */
	public static Set<Card> getHumanHand()
	{
		return aHumanPlayer.getHand();
	}

	/*
	 * setup initial positions of the cards
	 */
	void reset()
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
		pane.getChildren().add(GameButtons.getButton(BntName.LOAD));
		return pane;
	}
	
	/*
	 * create menu
	 */
	private Pane getMenu()
	{
		HBox menu = new HBox(BOX_SPACE);
		menu.getChildren().add(GameButtons.getButton(BntName.NEW));
		menu.getChildren().add(GameButtons.getButton(BntName.SAVE));
		menu.getChildren().add(GameButtons.getButton(BntName.LOAD));
		// TODO: add more buttons
		return menu;
	}
	
	/*
	 * Draw everything that are not related to the cards. 
	 */
	private void drawEverything()
	{	
		HBox pane = new HBox(BOX_SPACE);
		pane.setAlignment(Pos.CENTER);;
		// draw left
		VBox leftBar = new VBox(BOX_SPACE);
		leftBar.setAlignment(Pos.BOTTOM_LEFT);
		aLogDisplay.displayDisplay(leftBar);
		leftBar.getChildren().add(getMenu());
		pane.getChildren().add(leftBar);
		// draw center
		aCardDisplay.setDiplayer(pane);
		
		ROOT.getChildren().clear();
		ROOT.getChildren().add(pane);
		
		CHICKEN.newGame();
	}
	
	@Override
    public void start(Stage pStage) 
	{	
		// initialization
		pStage.setTitle("Gin Rummy");
		aLogDisplay = new OnlineObserver();
		aCardDisplay = new CardDisplayer();
		CHICKEN.addObservers(aLogDisplay);
		CHICKEN.addObservers(aCardDisplay);
		ROOT.setPadding(new Insets(PADDING));
		GameButtons.initialize(CHICKEN, this);
		// get name of player
		ROOT.getChildren().add(getPlayer());
		// css
        File f = new File("style.css");
        ROOT.getStylesheets().clear();
        ROOT.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
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

