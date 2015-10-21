package com.jlheard.game

/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/21/15
 * Time: 12:06 AM
 */
class DeckTest extends GroovyTestCase {

    void testDeckCreation() {

        def deck = new Deck()

        assert deck.size() == 52

        Card.Suit.values().each { suit ->
            Card.Value.values().each { value ->
                assert new Card(suit: suit, value: value) in deck
            }
        }

    }

}
