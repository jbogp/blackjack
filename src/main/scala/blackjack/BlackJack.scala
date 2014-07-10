package blackjack

import players.Dealer
import players.Player
import game.Table

/*Main function, just starts the game*/
object BlackJack {
	def main(args: Array[String]) {
		Table.play
	}
}