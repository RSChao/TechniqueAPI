package com.rschao.plugins.techniqueAPI.tech.cooldown;

public class cooldownHelper {
    public static final int hour = 3600000;
    /**
     * Converts seconds to milliseconds.
     * This method takes an integer representing seconds and converts it to milliseconds.
     * @param seconds the number of seconds to convert
     * @return
     */
    public static int secondsToMiliseconds(int seconds) {
        return seconds * 1000;
    }
    /**
     * Converts minutes to milliseconds.
     * This method takes an integer representing minutes and converts it to milliseconds.
     * @param minutes the number of minutes to convert
     * @return
     */
    public static int minutesToMiliseconds(int minutes) {
        return minutes * 60 * 1000;
    }

}
