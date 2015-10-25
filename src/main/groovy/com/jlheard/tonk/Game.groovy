package com.jlheard.tonk


/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/21/15
 * Time: 9:06 PM
 */
//TODO: eventually make this a domain class?
class Game {

    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", status=" + status +
                '}';
    }

    private String id = UUID.randomUUID()

    Integer stake = 0

    Deck deck = new Deck()
    Deck stock = new Deck()
    Deck discardPile = new Deck()

    GameStatus status = GameStatus.INITIATED

    LinkedList<Player> players = []



}
