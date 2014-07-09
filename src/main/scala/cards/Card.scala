package cards

import cards.Face._
import cards.Suit._

/*Defining Card Classes*/
abstract class Card {
  /*Check if Cards have the same value*/
  def isEqual(otherCard:Card):Boolean
}

/*No need for a name in ValueCard because the name is the value*/
case class ValueCard(cardValue: Int,suit: Suit) extends Card {
	def value = cardValue
	
	override def toString:String = {value.toString+" of "+suit}
	
	/*Define equality in the context of ValueCard*/
	def isEqual(otherCard:Card):Boolean = {
			otherCard match {
				case currentCard: ValueCard => currentCard.value == this.value
				case _ => false
			}
	}
}
/*No need for a value in FaceCard because they are all worth 10*/
case class FaceCard(name: Face,suit: Suit) extends Card{
	override def toString:String = {name+" of "+suit}
	
	/*Define equality in the context of ValueCard*/
	def isEqual(otherCard:Card):Boolean = {
			otherCard match {
				/*We can compare the names directly because they are Enum*/
				case currentCard: FaceCard => currentCard.name == this.name
				case _ => false
			}
	}
}

/*Special case of the Ace*/
case class Ace(suit: Suit) extends Card{
	override def toString:String = {"Ace of "+suit}
	
	/*Define equality in the context of ValueCard*/
	def isEqual(otherCard:Card):Boolean = {
			otherCard match {
				case currentCard: Ace => true
				case _ => false
			}
	}
}