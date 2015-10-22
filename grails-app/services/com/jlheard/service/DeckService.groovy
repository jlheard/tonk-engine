package com.jlheard.service

import com.jlheard.tonk.Deck
import com.jlheard.utils.RandomNumberUtils

/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/21/15
 * Time: 1:25 AM
 */
class DeckService {


    def Deck getShuffledDeck() {
        def deck = new Deck()
        deck.populate()
        Collections.shuffle(deck)
        return deck
    }

    def Deck cutDeck(Deck deck) {
        def cutIndex = RandomNumberUtils.getRandomInt(20, 32)
        def topCut = deck.take(cutIndex)
        deck.removeAll(topCut)
        deck.addAll(topCut)
        return deck
    }

}
