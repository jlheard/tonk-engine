package com.jlheard.service

import com.jlheard.tonk.Game
import com.jlheard.tonk.Player

import static Game.Status.*

/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/21/15
 * Time: 9:36 PM
 */
class GameService {

    DeckService deckService

    Game game

    def addPlayerToGame(Player player) {
        if(isCorrectGameStatus([NEW])) {
            game.players.add(player)
            game.playerDealOrder.add(player)
        } else {
            false
        }
    }

    def createNewGame() {
        game = new Game()
        game.status = NEW
    }

    def determinePlayerToDeal() {

    }

    def isCorrectGameStatus(List<Game.Status> correctStatuses) {
        game.status in correctStatuses
    }

    def startGame() {
        if(isCorrectGameStatus([NEW])) {
            game.status = IN_PROGRESS
            true
        } else {
            false
        }
    }

    def shuffleDeck() {
        if(isCorrectGameStatus([IN_PROGRESS, NEW_DEAL])) {
            game.deck = deckService.getShuffledDeck()
            game.stock = null
            game.discardPile = null
            true
        } else {
            false
        }
    }



}
