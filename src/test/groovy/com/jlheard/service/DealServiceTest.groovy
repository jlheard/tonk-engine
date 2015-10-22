package com.jlheard.service

import com.jlheard.tonk.Game
import com.jlheard.tonk.Player

/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/22/15
 * Time: 1:54 AM
 */
class DealServiceTest extends GroovyTestCase {

    void testDealCards() {

        def cardsToDeal = 5
        def dealService = new DealService()
        dealService.deckService = new DeckService()
        def game = new Game()
        def player1 = new Player(username: "player1")
        def player2 = new Player(username: "player2")

        game.players.add(player1)
        game.players.add(player2)

        dealService.dealCards(game, cardsToDeal)

        game.players.each { player ->
            assert player.hand.size() == cardsToDeal
        }

        assert game.deck.size() == 0
        assert game.stock.size() == 52 - (cardsToDeal * 2) - 1
        assert game.discardPile.size() == 1
    }

}
