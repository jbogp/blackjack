package players

import cards.Hand
import cards.Deck

class Dealer {
  
  /*Dealer can't split so only one hand*/
  val hand:Hand = new Hand
    
  /*Hit a card from the Deck for a particular hand*/
  def hit {
	  hand.addCard(Deck.drawCard)
  }
  
  /*Hit until we reach 17 and return the hand*/
  def unfoldGame:Hand = {
    hand.score match{
      case x if x>=17 => this.hand
      case _ => {
        this.hit
        this.unfoldGame
      }
    }
  }
  
  
}