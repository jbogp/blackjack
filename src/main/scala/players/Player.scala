package players

import cards.Hand
import cards.Deck
import scala.collection.mutable.ArrayBuffer

/*
* singleton pbject Player, represents the user in the game
* the player has `moneyz` number of chips, and a bet.
* He can be in or out of a game and he has got a hand
*/
object Player {

	/*Starting amount of chips*/
	var moneyz = 100

	/*Is the player in a game now?*/
	var isPlaying = false

	/*Define the bet*/
	var bet = 0

	/*initialise players array of hands*/
	val hands = new ArrayBuffer[Hand]

	/*initialise a new game*/
	initNewGame

	/*Set things as needed for a new game*/
	def initNewGame {
		/*Reset bet to 0*/
		this.bet = 0
		/*if more than one hand remove hands to leave only one*/
		hands.length match{
			/*If no Hand created, create the first one*/
			case 0 => hands += new Hand
			/*If more than one hand created, leave only one*/
			case x if x>1 => {
				hands.remove(1, hands.length-1)
				/*Empty first hand*/
				hands(0).emptyHand
			}
			case 1 => hands(0).emptyHand
		}


	}

	/*Place a bet*/
	def placeBet(amount:Int) {
		if(amount<=this.moneyz){
			this.bet = amount
			this.isPlaying = true
		}
		else {
			throw new IllegalArgumentException("You are trying to spend money you don't have, have you learned nothing for the financial crisis?")
		} 

	}

	/*Hit a card from the Deck for a particular hand*/
	def hit(hand:Hand) {
		hand.addCard(Deck.drawCard)
	}

	/*Split one hand into two providing the hand only contains */
	def split(hand:Hand) {
		/*Is the hand splittable*/
		if(hand.isSplittable){
			/*Has the player enough money to split?*/
			if(this.moneyz>=(this.bet*(this.hands.length+1))){
				/*Create a new hand, add the second card of hand to split to first card of newly created hand and remove the second card from first hand*/
				hands += new Hand({h:Hand => h.addCard(hand.cards.remove(1))})
				hands.foreach{hand=> hand.computeScore}
			}
			else {
				throw new IllegalArgumentException("You don't have enough money to split, sorry !")
			}
		}
	}


  /*Returns a Tuple of Boolean
	* 1.Do we need the dealer to hit cards? ie. Is the game over (all hands standing) with at least one hand not busted
	* 2. Are all the hands Standing?
	*/
	def endOfGameState:(Boolean,Boolean) ={
		var allStanding = true
		var allBusted=true


		hands.foreach{hand=>
			if(allStanding && !hand.isStanding) {allStanding = false}
			if(allBusted && !hand.isBusted){allBusted = false}
		}
		/*Return the boolean tuple2*/
		(allStanding && !allBusted,allStanding)
	}
	
}