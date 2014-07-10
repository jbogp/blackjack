package cards

import scala.collection.mutable.ArrayBuffer
import game.Outcomes._
import cards.Suit._
import game.Outcomes


/*Class Hand is a CardsCollection and represents the cards owned by a participant*/
class Hand extends CardsCollection{

	
	var score:Int = 0
	
	var isStanding = false
	var isBusted = false
	
	/*Define secondary constructor when we need to apply a function at creation of the object*/
	def this(callback:(Hand) => Unit){
		/*Create the Hand*/
		this
		/*Apply the function*/
		callback(this)
		
	}
	
	/*Determines the correct value of an Ace depending upon the current score*/
	private def aceValue(score:Int):Int =  {
		score match {
			case score if score>10 => 1
			case _ => 11
		}
	}
	
	/*Computes the score given the cards at hand*/ 
	def computeScore {
		var points = 0
		var numAce = 0
		cards.foreach {card =>
			card match {
				case currentCard: ValueCard => points += currentCard.value
				case currentCard: FaceCard => points += 10
				case currentCard: Ace => numAce += 1	
			}
		}
		
		/*We need to process the Aces separately, as we need to know the rest of the score choose their values*/
		for(i <- 0 until numAce) {
			points += aceValue(points)
		}

		if(points>21) {
			this.isBusted = true
		}
		
		this.score = points
	}
	
	/*toString override, to print the hand*/
	override def toString:String = {
		cards.length match {
			case 0 => "Empty Hand"
			case _ => {
				var returnString = ""
				cards.foreach {card => 
					returnString += " --- "+card.toString
				}
				returnString += "(score:" + this.score + ")"
				returnString
			}
		}
	}
	
	/*Add a card to the hand*/
	def addCard(card:Card) {
		cards += card
		this.computeScore
	}
	
	/*Empties the hand*/
	def emptyHand {
		this.score = 0
		cards.remove(0, cards.size)
		this.isStanding = false
		this.isBusted = false
	}
	
	/*is this hand splittable?*/
	def isSplittable:Boolean = {
		/*Check hand contain 2 cards have the same values*/
		this.cards.length == 2 && this.cards(0).isEqual(this.cards(1))
	}
	
	/*Does this hand (for a player) win against a dealer's hand?*/
	def winsAgainstDealer(dealerHand:Hand):Outcomes = {
		if(!this.isBusted){
			dealerHand match{
				case x if x.isBusted => Outcomes.Win
				case x if x.score<this.score => Outcomes.Win
				case x if x.score==this.score => Outcomes.Push
				case _ => Outcomes.Lose
			}
		}
		/*Busted, you lose*/
		else {
			Outcomes.Lose
		}
	}
	
}