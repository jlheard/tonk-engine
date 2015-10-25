package com.jlheard.service

import com.jlheard.tonk.Deck
import com.jlheard.tonk.Game
import com.jlheard.tonk.GameStatus
import com.jlheard.tonk.Player

import static com.jlheard.tonk.GameStatus.*


/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/21/15
 * Time: 9:36 PM
 */
class GameService {

    public static final ADD_PLAYER_TO_GAME_STATUSES = [NEW]
    public static final DETERMINE_PLAYER_TO_DEAL_STATUSES = [NEW]
    public static final START_GAME_STATUSES = [NEW]
    public static final START_NEW_ROUND_STATUSES = [IN_PROGRESS, ROUND_FINISHED]
    public static final int NUMBER_OF_CARDS_TO_DEAL = 5

    DeckService deckService
    DealService dealService

    Game game

    //TODO: Will new players be able to sit at a game in progress?
    // TODO: Should there be tables or a limit to the number of players in a game?
    def addPlayerToGame(Player player) {
        if(isCorrectGameStatus(ADD_PLAYER_TO_GAME_STATUSES)) {
            game.players.add(player)
        } else {
            false
        }
    }

    void createNewGame(int stake) {
        game = new Game()
        game.status = NEW
        game.stake = stake
    }


    def determinePlayerToDeal() {
        if(isCorrectGameStatus(DETERMINE_PLAYER_TO_DEAL_STATUSES)) {
            shuffleDeck()
            deckService.cutDeck(game.deck)
            dealService.dealCards(game, 1)

            game.players.sort{it.hand}

            establishNewDealer()

            true
        } else {
            false
        }
    }

    private establishNewDealer() {
        if(!game.players.empty) {
            def dealer = game.players.first()
            dealer.isDealer = true
        }
    }

    private def isCorrectGameStatus(List<GameStatus> correctStatuses) {
        game.status in correctStatuses
    }

    private void shuffleDeck() {
            game.deck = deckService.getShuffledDeck()
            game.stock = new Deck()
            game.discardPile = new Deck()
    }

    def startGame() {
        if(isCorrectGameStatus(START_GAME_STATUSES)) {
            game.status = IN_PROGRESS
            true
        } else {
            false
        }
    }

    def startNewRound() {
        if(isCorrectGameStatus(START_NEW_ROUND_STATUSES)) {
            shuffleDeck()
            deckService.cutDeck(game.deck)
            dealService.dealCards(game, NUMBER_OF_CARDS_TO_DEAL)
            game.status = ROUND_IN_PROGRESS
            true
        } else {
            false
        }
    }



}
