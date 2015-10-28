package com.jlheard.service

import com.jlheard.tonk.Deck
import com.jlheard.tonk.Game
import com.jlheard.tonk.GameStatus
import com.jlheard.tonk.Player
import groovy.mock.interceptor.StubFor

import static com.jlheard.service.GameService.ADD_PLAYER_TO_GAME_STATUSES
import static com.jlheard.service.GameService.DETERMINE_PLAYER_TO_DEAL_STATUSES
import static com.jlheard.service.GameService.START_GAME_STATUSES
import static com.jlheard.service.GameService.START_NEW_ROUND_STATUSES


/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/25/15
 * Time: 12:38 PM
 */
class GameServiceTest extends GroovyTestCase {

    final static PLAYER_1 = new Player(username: "Player 1")
    final static PLAYER_2 = new Player(username: "Player 2")

    GameService gameService

    @Override
    void setUp() {
        gameService = new GameService()
        gameService.deckService = new DeckService()
        gameService.dealService = new DealService()
    }

    @Override
    void tearDown() {
        gameService = null
    }

    void testGameCreation() {
        int stake = 50

        gameService.createNewGame(50)

        assert GameStatus.NEW == gameService.game.status
        assert stake == gameService.game.stake
    }

    void testAddPlayerToGameHappyPath() {
        gameService.createNewGame(0)

        assert gameService.addPlayerToGame(PLAYER_1.username, 5000)
        assert gameService.addPlayerToGame(PLAYER_2.username, 2000)

        assert [PLAYER_1, PLAYER_2] == gameService.game.players
    }

    void testAddPlayerToGameCorrectStatus() {
        ADD_PLAYER_TO_GAME_STATUSES.each {
            gameService.createNewGame(0)
            gameService.game.status = it
            assert gameService.addPlayerToGame(PLAYER_1)
        }
    }

    void testAddPlayerToGameIncorrectStatus() {
        (GameStatus.values() - ADD_PLAYER_TO_GAME_STATUSES).each {
            gameService.createNewGame(0)
            gameService.game.status = it

            assert !gameService.addPlayerToGame(PLAYER_1)
        }
    }

    void testDeterminePlayerToDealHappyPath() {
        gameService.createNewGame(0)

        assert gameService.addPlayerToGame(PLAYER_1)
        assert gameService.addPlayerToGame(PLAYER_2)

        def orderedDeck = new Deck()
        orderedDeck.populate()

        def deckServiceStub = new StubFor(DeckService)

        deckServiceStub.demand.getShuffledDeck(1) {}
        deckServiceStub.demand.cutDeck(1) {Deck d ->}

        def dealServiceStub = new StubFor(DealService)

        dealServiceStub.demand.dealCards(1) {Game game, int toDeal ->
            assert game == gameService.game
            assert toDeal == 1
        }

        def dealService = dealServiceStub.proxyDelegateInstance()
        def deckService = deckServiceStub.proxyDelegateInstance()

        gameService.dealService = dealService
        gameService.deckService = deckService

        assert gameService.determinePlayerToDeal()

        dealServiceStub.verify(dealService)
        deckServiceStub.verify(deckService)

        assert gameService.game.players.first().isDealer
    }

    void testDeterminePlayerToDealCorrectStatus() {
        DETERMINE_PLAYER_TO_DEAL_STATUSES.each {
            gameService.createNewGame(0)
            gameService.game.status = it
            assert gameService.determinePlayerToDeal()
        }
    }

    void testDeterminePlayerToDealIncorrectStatus() {
        (GameStatus.values() - DETERMINE_PLAYER_TO_DEAL_STATUSES).each {
            gameService.createNewGame(0)
            gameService.game.status = it
            assert !gameService.determinePlayerToDeal()
        }
    }

    void testEstablishNewDealer() {
        gameService.createNewGame(0)
        gameService.addPlayerToGame(PLAYER_2)
        gameService.addPlayerToGame(PLAYER_1)
        assert gameService.game.players.first().isDealer
    }

    void testShuffleDeckHappyPath() {
        GameService gs = new GameService()
        gs.createNewGame(0)

        def deckServiceStub = new StubFor(DeckService)

        deckServiceStub.demand.getShuffledDeck(1) {}

        def deckService = deckServiceStub.proxyDelegateInstance()

        gs.deckService = deckService

        gs.shuffleDeck()

        deckServiceStub.verify(deckService)

        assert gs.game.stock.empty
        assert gs.game.discardPile.empty
    }

    void testStartGameWithCorrectStatus() {
        START_GAME_STATUSES.each {
            gameService.createNewGame(0)
            gameService.game.status = it
            assert gameService.startGame()
            assert gameService.game.status == GameStatus.IN_PROGRESS
        }
    }

    void testStartGameWithIncorrectStatus() {
        (GameStatus.values() - START_GAME_STATUSES).each {
            gameService.createNewGame(0)
            gameService.game.status = it
            assert !gameService.startGame()
            assert gameService.game.status == it
        }
    }

    void testStartNewRoundHappyPath() {
        def gs = new GameService()
        gs.createNewGame(0)
        gs.startGame()

        def deckServiceStub = new StubFor(DeckService)

        deckServiceStub.demand.getShuffledDeck(1) {}
        deckServiceStub.demand.cutDeck(1) {Deck d -> assert d == gs.game.deck}

        def dealServiceStub = new StubFor(DealService)

        dealServiceStub.demand.dealCards(1) {Game g, int cardsToDeal ->
            assert g == gs.game
            assert cardsToDeal == GameService.NUMBER_OF_CARDS_TO_DEAL
        }

        def deckService = deckServiceStub.proxyDelegateInstance()
        def dealService = dealServiceStub.proxyDelegateInstance()

        gs.deckService = deckService
        gs.dealService = dealService

        assert gs.startNewRound()

        deckServiceStub.verify(deckService)
        dealServiceStub.verify(dealService)

        assert gs.game.status == GameStatus.ROUND_IN_PROGRESS

    }

}
