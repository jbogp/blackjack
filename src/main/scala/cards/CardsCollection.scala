package cards

import scala.collection.mutable.ArrayBuffer

/*Trait common to all classes with a set of card*/
trait CardsCollection {
  
  	val cards =  new ArrayBuffer[Card]

}