Gin Rummy
========

Card game software in Java (course project in software design)

How to play
-----------

The rules of the game can be found [here](http://www.pagat.com/rummy/ginrummy.html).

In short,

* 2 payers, each with 10 cards
* Value of the card from is their rank; J, Q, K have all value 10 points
* Cards are drawn from the deck on at a time. 
* On the first hand, the player who draw the lowest card is the dealer. Subsequently, the loser of the previous hand becomes the next hand's dealer. The 21st card is turn face up to start the discard pile.

Objetive:

* Collect a hand where most or all of the cards can be combined into sets and runs and the point value of the remaining unmatched cards (called "deadwook") is low .
	* a run or a sequence consists of 3 or more cards of the same suit in consecutive order
	* a set or group is three or four cards of the same rank
	* a card can only be part of one run or set

At each turn:

* Draw from the stock (face down pile) or frmo the discard pile (face up pile).
* Discard a card, add to the discard pile.
	* Not permitted to discard a discard card that the player has just picked. 
* The non-dealer plays first.

Kocking:

* Players can kock after discarding a card (face down) only when their deadwook is at most 10 points. 
* The knocker layout his card, arrange into sets and runs. If no deadwook, then this is called "go gin".
* Oppenents also layout their card, arrange into sets and runs, and extend their deadwook to the knocker's piles if possible.
* A hand ends when there are only two cards left. If the player who picks up the 3rd last card doesn't choose to knock, then the hand is cancelled. 
	* In a variation, the next player may choose to pick up the discard card after the previous one picks up the 3rd last card and discards, but only for the purpose of knocking.

Scoring:

* "Standard score": deadwook value difference between the two player
* Undercut bonus: 10 pts
* Go gin bonus: 20 pts

* If knocker has the lowest deadwook, then his gets the standard score.
* If knocker did not go gin, and the count is equal or knocker has higher value (when he is undercut), then the other player gets the standard score and a undercut bonus.
* If knocker goes gin, then he cannot be undercut and gets standard score plus go gin bonus.
* The game ends when a player scores more up to 100 pts. Then he recieve an addition of 100 pts. If the opponent didn't score any, he recieves 200 pts instead. For each hand they won, the players gets 20 more pts (line bonus or box bonus).
