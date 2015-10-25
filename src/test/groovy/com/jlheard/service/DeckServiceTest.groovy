package com.jlheard.service

import com.jlheard.utils.RandomNumberUtils
import groovy.mock.interceptor.StubFor

/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/21/15
 * Time: 1:33 AM
 */
class DeckServiceTest extends GroovyTestCase {

    void testGetShuffledDeck() {
        def deckService = new DeckService()
        def collectionsStub = new StubFor(Collections)

        collectionsStub.demand.shuffle(1){}

        collectionsStub.use {
            assert deckService.getShuffledDeck() != null
        }
    }

    void testCutDeck() {
        def deckService = new DeckService()
        def deck = deckService.getShuffledDeck()
        def cutIndex = RandomNumberUtils.getRandomInt(20, 32)
        def expectedFirstCard = deck[cutIndex]
        def expectedMiddleCard = deck.first

        def randomStub = new StubFor(RandomNumberUtils)

        randomStub.demand.getRandomInt(1) {int max, int min -> cutIndex}

        randomStub.use {
            deckService.cutDeck(deck)

            assert deck.size() == 52
            assert expectedFirstCard == deck.first
            assert expectedMiddleCard == deck[deck.size()-cutIndex]
        }


    }

}
