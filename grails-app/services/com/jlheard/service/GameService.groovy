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
    public static final END_ROUND_STATUSES = [ROUND_IN_PROGRESS]
    public static final START_GAME_STATUSES = [NEW]
    public static final START_NEW_ROUND_STATUSES = [IN_PROGRESS, ROUND_FINISHED]
    public static final START_NEW_TURN_STATUSES = [ROUND_IN_PROGRESS]
    public static final TAKE_TURN_STATUSES = [AWAITING_PLAYER_TURN]
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

    def addPlayerToGame(String username, int chips) {
        if(isCorrectGameStatus(ADD_PLAYER_TO_GAME_STATUSES)) {
            game.players.add(new Player(username: username, chips: chips))
        } else {
            false
        }
    }

    private adjustPlayerChips(Player winner, int multiplier = 1) {
        def adjustment = game.stake * multiplier
        game.players.find {it == winner}.chips += adjustment
        game.players.findAll{it != winner}.each { loser ->
            loser.chips -= adjustment
        }
    }

    private canPlayerCoverStake(Player player) {
        player.chips >= game.stake * 2
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

    private determineImmediateWin() {
        def winners = game.players.findAll{it.pointsInHand >= 49}
        def numOfWinners = winners.size()
        if(numOfWinners == 1) {
            adjustPlayerChips(winners.first(), 2)
            endRound()
        } else if(numOfWinners > 1) {
            endRound()
        } else {

        }
    }

    def endRound() {
        if(isCorrectGameStatus(END_ROUND_STATUSES)) {
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

    def findPlayerByUsername(String username) {
        game.players.find{it.username == username}
    }

    private def isCorrectGameStatus(List<GameStatus> correctStatuses) {
        game.status in correctStatuses
    }

    def removePlayerFromGame(String username) {
        game.players.remove(findPlayerByUsername(username))
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
            determineImmediateWin()
            true
        } else {
            false
        }
    }

    def startNewTurn() {
        if(isCorrectGameStatus(START_NEW_TURN_STATUSES)) {
            toggleTurnIndex()
            game.status = AWAITING_PLAYER_TURN
            true
        } else {
            false
        }
    }

    def takeTurn(String username) {
        if(isCorrectGameStatus(TAKE_TURN_STATUSES)
                && game.players.indexOf(findPlayerByUsername(username)) == game.playerTurnIndex) {

        } else {
            false
        }
    }

    private toggleTurnIndex() {
        if(game.playerTurnIndex < game.players.size()) {
            game.playerTurnIndex++
        } else {
            game.playerTurnIndex = 0
        }
    }


}
