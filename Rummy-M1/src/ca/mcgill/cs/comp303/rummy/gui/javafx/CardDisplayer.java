package ca.mcgill.cs.comp303.rummy.gui.javafx;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ca.mcgill.cs.comp303.rummy.model.Card;
import ca.mcgill.cs.comp303.rummy.model.Hand;
import ca.mcgill.cs.comp303.rummy.model.gameModel.GameModelLogger;
import ca.mcgill.cs.comp303.rummy.model.gameModel.GameObserver;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CardDisplayer implements GameObserver{
	private final String IMAGE_LOCATION = "";
	private final String IMAGE_SUFFIX = ".gif";
	private final String[] RANK_CODES = {"a", "2", "3", "4", "5", "6", "7", "8", "9", "t", "j", "q", "k"};
	private final String[] SUIT_CODES = {"c", "d", "h", "s"};
	
	private final int CARD_SPACE = -50;
	private final int HSPACE = 800;
	private final int VSPACE = 400;
	
	private Map<String, Image> aCards;
	private VBox mainPane;
	private HBox opponent;
	private HBox player;
	private HBox cardStacks;
	private ImageView discardPile;
	private HBox playerKnock;
	private HBox opponentKnock;
	private HBox playerDeadWook;
	private HBox opponentDeadWook;
	
	private ImageView[] playerCards;
	private ImageView[] opponentCards;
	
	
	
	public CardDisplayer(){
		playerCards = new ImageView[Hand.HANDSIZE];
		opponentCards = new ImageView[Hand.HANDSIZE];
		for (int i=0; i<Hand.HANDSIZE; i++)
		{
			playerCards[i] = new ImageView();
			opponentCards[i] = new ImageView();
		}
		mainPane = new VBox(Gui.BOX_SPACE);
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(Gui.PADDING));
		aCards = new HashMap<String, Image>();
		opponent = new HBox(CARD_SPACE);
		opponent.setAlignment(Pos.CENTER);
		player = new HBox(CARD_SPACE);
		player.setAlignment(Pos.CENTER);
		player.setMinWidth(HSPACE);
		cardStacks = new HBox(Gui.BOX_SPACE);
		cardStacks.setAlignment(Pos.CENTER_LEFT);
		cardStacks.getChildren().add(new ImageView(getBack()));
		cardStacks.setMinHeight(VSPACE);
		discardPile = new ImageView();
		cardStacks.getChildren().add(discardPile);
//		playerKnock = new HBox()
		
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
		player.getChildren().clear();
		opponent.getChildren().clear();
		int i=0;
		for (Card c: pCards)
		{
			playerCards[i].setImage(getCard(c));
			opponentCards[i].setImage(getBack());
			player.getChildren().add(playerCards[i]);
			opponent.getChildren().add(opponentCards[i]);
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
		Image lIcon = (Image) aCards.get(pCode);
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
