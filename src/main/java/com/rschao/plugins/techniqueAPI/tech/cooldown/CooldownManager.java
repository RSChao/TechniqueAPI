package com.rschao.plugins.techniqueAPI.tech.cooldown;

import java.util.*;

import org.bukkit.entity.Player;

public class CooldownManager {
    private static final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();
    /**
     * Checks if a player is on cooldown for a specific technique.
     * This method checks if the player has a cooldown set for the given technique ID.
     * 
     * @param player the player to check
     * @param techniqueId the unique identifier of the technique
     * @return true if the player is on cooldown, false otherwise
     */
    public static boolean isOnCooldown(Player player, String techniqueId) {
        Map<String, Long> playerCooldowns = cooldowns.getOrDefault(player.getUniqueId(), new HashMap<>());
        long now = System.currentTimeMillis();
        if(player.hasPermission("techapi.cooldown.bypass")) return false;
        return playerCooldowns.getOrDefault(techniqueId, 0L) > now ;
    }
    /**
     * Sets a cooldown for a player on a specific technique.
     * This method sets the cooldown time for the given technique ID for the specified player.
     * 
     * @param player the player to set the cooldown for
     * @param techniqueId the unique identifier of the technique
     * @param cooldownMillis the duration of the cooldown in milliseconds
     */
    public static void setCooldown(Player player, String techniqueId, long cooldownMillis) {
        cooldowns.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                 .put(techniqueId, System.currentTimeMillis() + cooldownMillis);
    }
    /**
     * Gets the remaining cooldown time for a player on a specific technique.
     * This method returns the remaining cooldown time in milliseconds.
     * 
     * @param player the player to check
     * @param techniqueId the unique identifier of the technique
     * @return the remaining cooldown time in milliseconds, or 0 if not on cooldown
     */
    public static long getRemaining(Player player, String techniqueId) {
        Map<String, Long> playerCooldowns = cooldowns.getOrDefault(player.getUniqueId(), new HashMap<>());
        long now = System.currentTimeMillis();
        return Math.max(0, playerCooldowns.getOrDefault(techniqueId, 0L) - now);
    }
    /**
     * Gets the cooldown time for a player on a specific technique.
     * This method returns the cooldown time in milliseconds.
     * 
     * @param player the player to check
     * @param techniqueId the unique identifier of the technique
     * @return the cooldown time in milliseconds, or 0 if not set
     */
    public static void removeCooldown(Player player, String techniqueId) {
        Map<String, Long> playerCooldowns = cooldowns.get(player.getUniqueId());
        if (playerCooldowns != null) {
            playerCooldowns.remove(techniqueId);
            if (playerCooldowns.isEmpty()) {
                cooldowns.remove(player.getUniqueId());
            }
        }
    }

    /**
     * Removes all cooldowns for a player.
     * This method clears all cooldowns associated with the specified player.
     * 
     * @param player the player to remove all cooldowns for
     */
    public static void removeAllCooldowns(Player player) {
        cooldowns.remove(player.getUniqueId());
    }
}
