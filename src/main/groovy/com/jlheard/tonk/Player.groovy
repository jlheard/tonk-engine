package com.jlheard.tonk

import org.apache.commons.lang.RandomStringUtils

/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/21/15
 * Time: 12:51 AM
 */
class Player {

    String username = RandomStringUtils.randomAlphabetic(5)
    Hand hand = new Hand()
    Integer chips
    boolean isDealer = false

    def getPointsInHand() {
        hand?.value?.points?.sum()
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Player)) return false

        Player player = (Player) o

        if (username != player.username) return false

        return true
    }

    int hashCode() {
        return username.hashCode()
    }


    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                '}';
    }
}
