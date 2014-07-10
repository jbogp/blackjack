package players

import cards.Hand
import cards.Deck

/* Singleton object Dealer*/
object Dealer {
	
	/*Dealer can't split so only one hand*/
	val hand:Hand = new Hand
	
	/*Hit a card from the Deck for a particular hand*/
	def hit {
		hand.addCard(Deck.drawCard)
	}

	/*Hit until we reach 17*/
	def unfoldGame:Unit = {
		hand.score match{
			case x if x>=17 => Unit
			case _ => {
				this.hit
				this.unfoldGame
			}
		}
	}


}