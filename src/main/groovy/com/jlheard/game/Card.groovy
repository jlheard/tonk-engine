package com.jlheard.game

/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/20/15
 * Time: 11:23 PM
 */
class Card {

    public enum Suit {
        CLUB, DIAMOND, HEART, SPADE
    }

    public enum Value {
        ACE(1, 1), TWO(2, 2), THREE(3, 3), FOUR(4, 4), FIVE(5, 5), SIX(6, 6), SEVEN(7, 7), EIGHT(8, 8), NINE(9, 9),
        TEN(10, 10), JACK(11, 10), QUEEN(12, 10), KING(13, 10)

        int rank
        int points

        Value(int rank, int points) {
            this.rank = rank
            this.points = points
        }
    }

    Suit suit
    Value value

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Card)) return false

        Card card = (Card) o

        if (value != card.value) return false
        if (suit != card.suit) return false

        return true
    }

    int hashCode() {
        int result
        result = suit.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }
}
