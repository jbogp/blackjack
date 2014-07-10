Insight Data Engineering - Coding Challenge
===========================================

Scala implementation of a simple one player blackjack table for the Insight Data Engineering coding challenge.

Compiling the game
-------------------
The easiest way to compile and play the game is to use [sbt](http://www.scala-sbt.org/). Simply open a Terminal in the folder containing the code and launch sbt by typing:

`sbt`

Once inside sbt, simply type `run`. On the first compile, sbt will fetch the necessary dependencies, compile and run the game.

Cards value
-----------
- Cards from 2 to 10 have the value written on the card
- Face cards have the value 10
- Aces can be either 1 or 11

Deck management
---------------
The Deck is refilled and reshuffled every 4 rounds

Game play
---------
The game play is very basic:

- The player starts with 100 chips
- Each round the player bets an amount of chips and the game begins with the Dealer hitting one card and the player hitting two.
- Depending upon the 2 cards hit, 2 or 3 choices are available
  - Hit another card
  - Stand
  - If the 2 cards are the same(for example Queen of Spades and Queen of Diamonds), the player can split his hand into 2 independent hands. (The starting bet is consequently doubled, both hands with the same bet)

- During this process if the hand exceeds 21, the hand is busted and the bet is lost

- When all non busted hands are standing, the dealer will hit cards until reaching a minimum of 17.
  - If the player's hand is higher than the dealer's or if the dealer is busted, the bet is won
  - If the player's and the dealer's hand have the same value, it's a "push" and the bet chips are simply returned and a new round starts
  - If the player's hand is lower than the dealer's, the bet is lost

- When the player is out of money, the program terminates.

Comments
--------
This implementation is very basic. I should mention that I am fairly new to Scala but I really like so I decided to give it a go. Consequently, I may have missed opportunities to use nice syntactic options.

Class structure
---------------
The source structure is a classic sbt (or Maven) organisation :
- src/
  - main/
    - scala/
      - blackjack/
        - BlackJack.scala (contains the main method)
      - cards/
        - Card.scala (defines the abstract class of a card and the implementation of all card types)
        - CardsCollection.scala (trait defining a collection of cards)
        - Deck.scala (CardsCollection representing the Deck)
        - Hand.scala (CardsCollection, defines a participant's hand player or dealer)
        - Face.scala (Enumeration of the face cards names)
        - Suit.scala (Enumeration of the cards' suits)
      - game/
        - Outcomes.scala (Enumeration of on round possible outcomes)
        - Table.scala (defines the main game logic, user input and interactions between participants)
      - players/
        - Dealer.scala (dealer's logic)
        - Player.scala (player's logic)


