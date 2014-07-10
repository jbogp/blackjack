package game

import players.Dealer
import players.Player
import cards.Deck
import cards.Hand


/*
* Singleton object Table, implements:
* - The logic of the game
* - Interactions between participants
* - User interactions with the program
*/
object Table {

	/*Just delays a function to give the game a real time feeling*/
	def realTimefeel(callback:()=>Unit,time:Long) {
		Thread.sleep(time)
		callback()
	}


	def play:Unit = {
		var userChoice:Int = 0
		try{
			Player.moneyz match {
				/*Exit Program*/
				case x if x==0 && !Player.isPlaying => println("No more money! Security, show this man to the door please")
				case _ => {
					Player.isPlaying match {
						case true => {
							Player.hands.filter{h: Hand => !h.isStanding && h.score<=21}.foreach{hand =>
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
										Player.hit(hand)
										/*print last card drawn*/
										println("You hit: "+hand.cards(hand.cards.length-1))
										/*if bust, out of the game*/
										if(hand.isBusted){
											println("Busted ! score: "+hand.score)
											hand.isStanding = true
										}
									}
									case 2 => hand.isStanding = true
									case 3 => Player.split(hand)
								}
							}

							var gameState = Player.endOfGameState
							/*If all hands are standing, resolve game*/
							if(gameState._2){
								/*If needed, make the dealer hit his cards*/
								if(gameState._1){
									/*Make the dealer draw until 17*/
									Dealer.unfoldGame
									this.realTimefeel(()=>{println("Dealer final hand: " + Dealer.hand)},500)
								}
								/*Count the points foreach hand*/
								Player.hands.foreach{hand =>
									hand.winsAgainstDealer(Dealer.hand) match{
										/*Lose, you lose your bet*/
										case Outcomes.Lose => {
											Player.moneyz -= Player.bet
											this.realTimefeel(()=>{println("Sorry, you Lose: -"+Player.bet+" chips\n\n")},500)
										}
										/*Push, you get back your bet*/
										case Outcomes.Push => {
											this.realTimefeel(()=>{println("Push! You keep your bet\n\n")},500)
										}
										/*You get twice your bet*/
										case Outcomes.Win => {
											Player.moneyz += Player.bet
											this.realTimefeel(()=>{println("Yeah you win !  +"+Player.bet+" chips\n\n")},500)
										}
									}
								}
								/*Indicate the player is not playing anymore*/
								Player.isPlaying=false
								/*Update the deck*/
								Deck.updateDeck
							}
							this.play

						}
						case false => {
							/*intialize the player state at the start of the game*/
							Player.initNewGame
							Dealer.hand.emptyHand
							println("Wanna play?. Enter the table with a bet of how many? (min 1, 0 to exit program). you have "+Player.moneyz+" chips available") 
							userChoice = readInt()
							/*Exit if needed*/
							if(userChoice > 0){
								this.realTimefeel(()=>{println("Let's Go!")}, 500)
								/*Place the bet per hand*/
								Player.placeBet(userChoice)
								/*Hit the dealer's first card*/
								Dealer.hit
								/*Inform user of dealer's first card*/
								this.realTimefeel(()=>{println("Dealer just hit:" +Dealer.hand+"\n")}, 500)


								/*Hit the user's first two cards which are in the first hand*/
								Player.hit(Player.hands(0));
								this.realTimefeel(()=>{println("You just hit:" +Player.hands(0).cards(0))}, 500)
								Player.hit(Player.hands(0));
								this.realTimefeel(()=>{println("you just hit:" +Player.hands(0).cards(1))}, 500)
								this.play
							}
						}
					}
				}
			}
		}
		catch{
			case e: NumberFormatException => {println("You need to enter a Integer value");this.play}
			case e: MatchError => {println("This choice is not valid, try again");this.play}
			case e: IllegalArgumentException => {println(e.getMessage());this.play}
			case e: Throwable => {println("There's been an error "+e.toString());this.play}
		}
	}

}

