package com.jlheard.tonk

/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/20/15
 * Time: 11:33 PM
 */
class Deck extends LinkedList<Card> {

    void populate() {
        Card.Value.values().each { value ->
          Card.Suit.values().each { suit ->
                add(new Card(suit: suit, value: value))
            }
        }
    }

    def drawCard(Card card) {
        remove(card)
        return card
    }
}
