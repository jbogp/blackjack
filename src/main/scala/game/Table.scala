package game

import players.Dealer
import players.Player
import cards.Deck
import cards.Hand

class Table {
  /*Create a player and a dealer*/
  val player = new Player
  val dealer = new Dealer
  
  
  /*Just delays a function to give the game a real time feeling*/
  def realTimefeel(callback:()=>Unit,time:Long) {
	  Thread.sleep(time)
	  callback()
  }
  
  
  def play:Unit = {
    var userChoice:Int = 0
    try{
	    player.moneyz match {
	      case x if x==0 && !player.isPlaying => println("No more money! Security, show this man to the door please")
	      case _ => {
	        player.isPlaying match {
	          case true => {
	            player.hands.filter{h: Hand => !h.isStanding && h.score<=21}.foreach{hand =>
	              /*Printing user's available choices*/
	              this.realTimefeel(()=>{
	                println()
	                println(hand)
	                println("You can:")
	                println("1. Hit me !")
	                println("2. Stand")
	                if(hand.isSplittable) {
	                  println("3. Split")
	                }
	              }, 500)
	              userChoice = readInt()
	              println(userChoice.toString)
	              userChoice match {
	                case 1 => {
	                  player.hit(hand)
	                  /*print last card drawn*/
	                  println("You hit: "+hand.cards(hand.cards.length-1))
	                  /*if bust, out of the game*/
	                  if(hand.score>21){
	                    println("Busted ! score: "+hand.score)
	                    hand.isStanding = true
	                  }
	                }
	                case 2 => hand.isStanding = true
	                case 3 => player.split(hand)
	              }
	            }
	            /*Are all the hands standing? If so end the round*/
	            if(player.allHandsStanding){
	              /*Make the dealer draw until 17*/
	              var dealerHand = dealer.unfoldGame
	              println("Dealer final hand: " + dealerHand)
	              /*Count the points foreach hand*/
	              player.hands.foreach{hand =>
	                hand.winsAgainstDealer(dealerHand) match{
	                  /*Lose, you lose your bet*/
	                  case Outcomes.Lose => {
	                    player.moneyz -= player.bet
	                    println("Sorry, you Lose: -"+player.bet+" chips\n\n")
	                  }
	                  /*Push, you get back your bet*/
	                  case Outcomes.Push => {
	                    println("Push! You keep your bet\n\n")
	                  }
	                  /*You get twice your bet*/
	                  case Outcomes.Win => {
	                    player.moneyz += player.bet
	                    println("Yeah you win !  +"+player.bet+" chips\n\n")
	                  }
	                }
	              }
	              player.isPlaying=false
	            }
	          }
	          case false => {
	        	/*intialize the player state at the start of the game*/
	            player.initNewGame
	            dealer.hand.emptyHand
	            println("Wanna play?. Enter the table with a bet of how many? (min 1). you have "+player.moneyz+" chips available") 
	            userChoice = readInt()
	            this.realTimefeel(()=>{println("Let's Go!")}, 500)
	            /*Place the bet per hand*/
	            player.placeBet(userChoice)
	            /*Hit the dealer's first card*/
	            dealer.hit
	            /*Inform user of dealer's first card*/
	            this.realTimefeel(()=>{println("Dealer just hit:" +dealer.hand+"\n")}, 500)
	            
	            
	            /*Hit the user's first two cards which are in the first hand*/
	            player.hit(player.hands(0));
	            this.realTimefeel(()=>{println("You just hit:" +player.hands(0).cards(0))}, 500)
	            player.hit(player.hands(0));
	            this.realTimefeel(()=>{println("you just hit:" +player.hands(0).cards(1))}, 500)
	                       
	            
	          }
	        }
	        this.play
	      }
	    }
     }
    catch{
      case e: NumberFormatException => {println("You need to enter a Integer value");this.play}
      case e: MatchError => {println("This choice is not valid, try again");this.play}
      case e: IllegalArgumentException => {println(e.toString());this.play}
      case e:Throwable => {println("There's been an error "+e.toString());this.play}
    }
  }

}