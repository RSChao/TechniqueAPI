package com.rschao.plugins.techapi.tech;

import com.rschao.plugins.techapi.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.*;

public class PlayerTechniqueManager {
    // Map<playerUUID, Map<fruitId, techniqueIndex>>
    private static final Map<UUID, Map<String, Integer>> playerTechniques = new HashMap<>();
    private static final List<UUID> inmuneplayers = new ArrayList<>();

    public static int getCurrentTechnique(UUID playerId, String fruitId) {
        Map<String, Integer> techs = playerTechniques.get(playerId);
        if (techs == null) return 0; // default to first technique
        return techs.getOrDefault(fruitId, 0);
    }

    // Optional: set the current technique
    public static void setCurrentTechnique(UUID playerId, String fruitId, int techniqueIndex) {
        playerTechniques.computeIfAbsent(playerId, k -> new HashMap<>()).put(fruitId, techniqueIndex);
    }
    public static boolean isInmune(UUID playerId) {
        return inmuneplayers.contains(playerId);
    }
    public static void setInmune(UUID playerId, boolean inmune, int timeSeconds) {
        if (inmune) {
            if (!inmuneplayers.contains(playerId)) {
                inmuneplayers.add(playerId);
                if(timeSeconds <= 0) return;
                // Schedule removal after timeSeconds
                Bukkit.getScheduler().runTaskLater(Plugin.getPlugin(Plugin.class), new Runnable() {
                    @Override
                    public void run() {
                        inmuneplayers.remove(playerId);
                    }
                }, timeSeconds * 20L); // Convert seconds to ticks
            }
        }
    }
}