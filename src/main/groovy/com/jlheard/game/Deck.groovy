package com.jlheard.game

/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/20/15
 * Time: 11:33 PM
 */
class Deck extends LinkedList<Card> {

    Deck() {
        Card.Suit.values().each { suit ->
            Card.Value.values().each { rank ->
                add(new Card(suit: suit, value: rank))
            }
        }
    }
}
