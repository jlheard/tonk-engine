package com.jlheard.tonk

import com.jlheard.service.DeckService

/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/21/15
 * Time: 1:20 AM
 */
class PlayerTest extends GroovyTestCase {

    void testPlayerEqualsByUsername() {
        def userName = "test-user"
        assert new Player(username: userName) == new Player(username: userName)
    }

    void testPointsInHandCorrect() {
        def deck = new DeckService().getShuffledDeck()

        def hand = new Hand()
        deck.take(5).each {hand << it}

        def player = new Player(hand: hand)

        assert hand.value.points.sum() == player.pointsInHand

        hand = new Hand()

        [new Card(suit: Card.Suit.DIAMOND, value: Card.Value.NINE),
         new Card(suit: Card.Suit.SPADE, value: Card.Value.SIX),
         new Card(suit: Card.Suit.DIAMOND, value: Card.Value.JACK),
         new Card(suit: Card.Suit.HEART, value: Card.Value.EIGHT),
         new Card(suit: Card.Suit.CLUB, value: Card.Value.SEVEN)]
                .each { hand << it }

        player.hand = hand
        def points = 40

        assert points == player.pointsInHand

    }

}
