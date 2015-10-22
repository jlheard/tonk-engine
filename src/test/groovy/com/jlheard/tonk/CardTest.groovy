package com.jlheard.tonk

/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/22/15
 * Time: 12:08 AM
 */
class CardTest extends GroovyTestCase {

    void testCardEquals() {
        assert new Card(suit: Card.Suit.SPADE, value: Card.Value.JACK) == new Card(suit: Card.Suit.SPADE, value: Card.Value.JACK)
    }

    void testCardCompareTo() {
        assert 0 == new Card(suit: Card.Suit.SPADE, value: Card.Value.JACK).compareTo(new Card(suit: Card.Suit.SPADE, value: Card.Value.JACK))
        assert 1 == new Card(suit: Card.Suit.DIAMOND, value: Card.Value.JACK).compareTo(new Card(suit: Card.Suit.SPADE, value: Card.Value.TEN))
        assert -1 == new Card(suit: Card.Suit.SPADE, value: Card.Value.JACK).compareTo(new Card(suit: Card.Suit.SPADE, value: Card.Value.QUEEN))
    }

}
