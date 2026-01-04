package com.rschao.plugins.techniqueAPI.tech.awakening;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Awakening {
    private static final Map<String, Set<String>> awakenedFruits = new HashMap<>();

    /**
     * Checks if a player has awakened a specific fruit.
     * This method checks if the player has the specified fruit ID in their set of awakened fruits.
     *
     * @param playerId the unique identifier of the player
     * @param fruitId  the unique identifier of the fruit
     * @return true if the player has awakened the fruit, false otherwise
     */
    public static boolean isAwakened(String playerId, String fruitId) {
        if(Bukkit.getPlayer(playerId).hasPermission("techapi.awakening.perma")) return true;
        return awakenedFruits.getOrDefault(playerId, Set.of()).contains(fruitId);
    }
    /**
     * Sets the awakened status of a fruit for a player.
     * This method adds or removes the specified fruit ID from the player's set of awakened fruits.
     *
     * @param playerId the unique identifier of the player
     * @param fruitId  the unique identifier of the fruit
     * @param awakened true to mark the fruit as awakened, false to unmark it
     */
    public static void setAwakened(String playerId, String fruitId, boolean awakened) {
        awakenedFruits.computeIfAbsent(playerId, k -> new HashSet<>());
        if (awakened) awakenedFruits.get(playerId).add(fruitId);
        else awakenedFruits.get(playerId).remove(fruitId);
    }
}
