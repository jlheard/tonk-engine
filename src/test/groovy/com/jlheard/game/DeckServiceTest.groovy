package com.jlheard.game

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

}
