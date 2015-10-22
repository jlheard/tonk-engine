package com.jlheard.utils

/**
 * Created with IntelliJ IDEA.
 * User: jheard
 * Date: 10/21/15
 * Time: 11:12 PM
 */
class RandomNumberUtils {

    public static final RANDOM = new Random()

    static int getRandomInt(int min, int max) {
        RANDOM.nextInt(max - min) + min
    }

}
