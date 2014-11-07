package ca.mcgill.cs.comp303.rummy.gui.javafx;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Hand;
import ca.mcgill.cs.comp303.rummy.model.gameModel.GameModelLogger;
import ca.mcgill.cs.comp303.rummy.model.gameModel.GameObserver;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CardDisplayer implements GameObserver{
	private final String IMAGE_LOCATION = "";
	private final String IMAGE_SUFFIX = ".gif";
	private final String[] RANK_CODES = {"a", "2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k"};
	private final String[] SUIT_CODES = {"c", "d", "h", "s"};
	
	private final int CARD_SPACE = -50;
	private final int IMAGE_SPACE = -40;
	private final int HSPACE = 600;

	private VBox mainPane;
	private HBox opponent;
	private HBox player;
	private HBox cardStacks;
	private HBox knock;
	private HBox playerDeadWook;
	private HBox opponentDeadWook;
	
	public class ImageButton extends Button {
	    
	    protected final String STYLE_NORMAL = "-fx-background-color: transparent; -fx-padding: 5 5 5 5;";
	    protected final String STYLE_PRESSED = "-fx-background-color: transparent; -fx-padding: 6 4 4 6;";
	    private ImageView aImage;
	    
	    public void setImage(Image img)
	    {
	    	aImage.setImage(img);
	    }
	    
	    public ImageButton(Image img) {
	    	aImage = new ImageView(img);
	        setGraphic(aImage);
	        setStyle(STYLE_NORMAL);
	        
	        setOnMousePressed(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	                setStyle(STYLE_PRESSED);
	            }            
	        });
	        
	        setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	               setStyle(STYLE_NORMAL);
	            }            
	        });
	    }
	    
	}
	
	public class HandCardButton extends ImageButton{
		private boolean toggled = false;
		private final String STYLE_UP = "-fx-background-color: transparent; -fx-padding: 10 5 0 5;";
		private final String STYLE_UP_PRESSED = "-fx-background-color: transparent; -fx-padding: 9 4 1 6;";
		public HandCardButton(Image img) {
			super(img);
			setOnMousePressed(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	if (toggled){
	            		setStyle(STYLE_UP_PRESSED);
	            		toggled = false;
	            	}
	            	else{
	            		setStyle(STYLE_PRESSED);
	            		toggled = true;
	            	}
	            }            
	        });
		        
		    setOnMouseReleased(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	if (toggled) setStyle(STYLE_UP);
	            	else setStyle(STYLE_NORMAL);
	            }            
	        }); 
		}	
	}
	
	private Map<String, Image> aCards;
	private ImageButton discardPile = null;
	private HandCardButton[] playerCards;
	private ImageView[] opponentCards;
	
	public CardDisplayer(){
		aCards = new HashMap<String, Image>();
		mainPane = new VBox(Gui.BOX_SPACE);
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(Gui.PADDING));
		opponent = new HBox(IMAGE_SPACE);
		opponent.setAlignment(Pos.CENTER);
		player = new HBox(CARD_SPACE);
		player.setAlignment(Pos.TOP_CENTER);
		playerCards = new HandCardButton[Hand.HANDSIZE];
		opponentCards = new ImageView[Hand.HANDSIZE];
		for (int i=0; i<Hand.HANDSIZE; i++)
		{
			playerCards[i] = new HandCardButton(getBack());
			opponentCards[i] = new ImageView(getBack());
			opponent.getChildren().add(opponentCards[i]);
			player.getChildren().add(playerCards[i]);
		}
		cardStacks = new HBox(Gui.BOX_SPACE);
		cardStacks.setAlignment(Pos.CENTER_LEFT);
		cardStacks.getChildren().add(new ImageButton(getBack()));
		
		knock = new HBox(IMAGE_SPACE);
		knock.setMinHeight(300);
		knock.setAlignment(Pos.CENTER);
		playerDeadWook = new HBox(IMAGE_SPACE);
		playerDeadWook.setMinHeight(100);
		playerDeadWook.setAlignment(Pos.CENTER);
		opponentDeadWook = new HBox(IMAGE_SPACE);
		opponentDeadWook.setMinHeight(100);
		opponentDeadWook.setAlignment(Pos.CENTER);
		VBox endPane = new VBox(Gui.BOX_SPACE);
		endPane.getChildren().add(knock);
		endPane.getChildren().add(playerDeadWook);
		endPane.getChildren().add(opponentDeadWook);
		endPane.setMinWidth(HSPACE);
		cardStacks.getChildren().add(endPane);
		
		mainPane.getChildren().add(opponent);
		mainPane.getChildren().add(cardStacks);
		mainPane.getChildren().add(player);
	}
	
	/**
	 * Add to pane. 
	 * @param pPane
	 */
	public void setDiplayer(Pane pPane){
		pPane.getChildren().add(mainPane);
	}
	
	private void distribute(Set<Card> pCards){
		int i=0;
		for (Card c: pCards)
		{
			playerCards[i].setImage(getCard(c));
			i++;
		}
	}
	
	private void refresh(Set<Card> pCards){
		int i=0;
		for (Card c: pCards)
		{
			playerCards[i].setImage(getCard(c));
			i++;
		}
	}
	
	/*
	 * Return an image of the back of a card.
	 */
	private Image getBack()
	{
		return getCardImage("b");
	}
	
	/*
	 * Return the image of a card.
	 */
	private Image getCard(Card pCard)
	{
		return getCardImage(getCode(pCard));
	}
	
	private String getCode(Card pCard)
	{
		return RANK_CODES[ pCard.getRank().ordinal() ] + SUIT_CODES[ pCard.getSuit().ordinal() ];		
	}
	
	private Image getCardImage(String pCode)
	{
		Image lIcon = aCards.get(pCode);
		if( lIcon == null )
		{	
			lIcon = new Image(IMAGE_LOCATION + pCode + IMAGE_SUFFIX);
			aCards.put( pCode, lIcon );
		}
		return lIcon;
	}

	@Override
	public void logStartGame(GameModelLogger pEngine) {
		distribute(Gui.getHumanHand());
		if (discardPile != null) discardPile.setImage(getCard(pEngine.getDiscard()));
		else {
			discardPile = new ImageButton(getCard(pEngine.getDiscard()));
			cardStacks.getChildren().add(discardPile);
		}
		
	}

	@Override
	public void logPlayed(GameModelLogger pEngine) {
		discardPile.setImage(getCard(pEngine.getDiscard()));
		refresh(Gui.getHumanHand());
	}

	@Override
	public void logEndGame(GameModelLogger pEngine) {
		// TODO Auto-generated method stub
		
	}
}
