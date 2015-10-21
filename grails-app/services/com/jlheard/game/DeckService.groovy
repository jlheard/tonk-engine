package com.jlheard.game

/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/21/15
 * Time: 1:25 AM
 */
class DeckService {

    def Deck getShuffledDeck() {
        def deck = new Deck()
        Collections.shuffle(deck)
        return deck
    }

}
