package com.jlheard.tonk

/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/21/15
 * Time: 12:06 AM
 */
class DeckTest extends GroovyTestCase {

    void testDeckPopulation() {

        def deck = new Deck()
        deck.populate()

        assert deck.size() == 52

        Card.Suit.values().each { suit ->
            Card.Value.values().each { value ->
                assert new Card(suit: suit, value: value) in deck
            }
        }
    }

    void testDrawCard() {
        def deck = new Deck()
        deck.populate()

        def expectedCard = deck.first
        def drawnCard = deck.drawCard(expectedCard)

        assert deck.size() == 51
        assert expectedCard == drawnCard
        assert !(drawnCard in deck)
    }

}
