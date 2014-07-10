package cards

import scala.collection.mutable.ArrayBuffer
import cards.Suit._

object Deck extends CardsCollection{

	/*Number of rounds before the deck is reshuffled*/
	val limitRounds = 4
	/*Number of rounds played with the same deck*/
	var numberOfRounds = 0

	/*Update the deck and reshuffle is needed*/
	def updateDeck {
		this.numberOfRounds = {
			(numberOfRounds+1 == limitRounds)  match {
				case true => {
					/*reshuffle the deck*/
					this.currentDeck = shuffleDeck
					/*Return value*/
					0
				}
				case false => this.numberOfRounds+1
			}
		}
	}

	/*Fill one suit with 13 cards*/
	private def fillOneSuit(value:Int,suit: Suit) {
		value match{
			case 1 => {
				cards += new Ace(suit)
				fillOneSuit(value+1,suit)
			}
			case x if x>1 && x<=10 => {
				cards += new ValueCard(value,suit)
				fillOneSuit(value+1,suit)
			}
			case 11 => {
				cards += new FaceCard(Face.Jack,suit)
				fillOneSuit(value+1,suit)
			}
			case 12 => {
				cards += new FaceCard(Face.Queen,suit)
				fillOneSuit(value+1,suit)
			}
			case 13 => {
				cards += new FaceCard(Face.King,suit)
			}
			
		}
	}	

	/*Fill the deck for each Suit*/
	private def fillSuits {
		/*Index offset to fill the 52 cards*/
		var indexOffset = 0
		Suit.values foreach{i=>
			/*Fill the Suit*/
			fillOneSuit(1,i)
			/*increment the offset of 13 cards*/
			indexOffset += 13
		}
	}
	
	/*Create a shuffled deck from the ordered Deck*/
	def shuffleDeck:ArrayBuffer[Card] = {
		util.Random.shuffle(cards)
	}
	
	/*Draw random card from the deck*/
	def drawCard:Card = {
		/*if Deck is running low on cards, reshuffle*/
		if(currentDeck.length==1){
			currentDeck = shuffleDeck
		}
		/*We simply remove the first card of the Deck (which has been shuffled previously)*/
		currentDeck.remove(0)
	}
	
	

	/*Initialize the Deck*/
	fillSuits
	var currentDeck = shuffleDeck
	
	


}