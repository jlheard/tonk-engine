package com.jlheard.service

import com.jlheard.tonk.Deck
import com.jlheard.tonk.Game

/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/22/15
 * Time: 12:48 AM
 */
class DealService {

    void dealCards(Game game, int cardsToDealEachPlayer) {
        int totalCardsDealt = 0
        int totalCardsToDeal = game.players.size() * cardsToDealEachPlayer

        while (totalCardsDealt < totalCardsToDeal) {
            game.players.each { player ->
                def cardDealt = game.deck.drawCard(game.deck.first)
                player.hand.add(cardDealt)

                totalCardsDealt++
            }
        }

        game.stock = game.deck
        game.deck = new Deck()

        def firstCardOfDiscardPile = game.stock.drawCard(game.stock.first)
        game.discardPile.add(firstCardOfDiscardPile)
    }

}
