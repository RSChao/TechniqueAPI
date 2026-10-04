package com.rschao.plugins.techniqueAPI.tech.register;

import com.rschao.plugins.techniqueAPI.tech.Technique;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.rschao.plugins.techniqueAPI.tech.register.TechRegistry.LogTechnique;

public class TechniqueSubRegistry {

    private final String regId;
    private final String configFilePath;
    private final String configSection;
    private final boolean usesAwakening;
    private final Function<Player, List<String>> groupIDGetter;
    private final Map<String, List<Technique>> techniques = new java.util.HashMap<>();

    public TechniqueSubRegistry(String regId, String configFilePath, String configSection, boolean usesAwakening, Function<Player, List<String>> groupIDGetter) {
        this.regId = regId;
        this.configFilePath = configFilePath;
        this.configSection = configSection;
        this.usesAwakening = usesAwakening;
        this.groupIDGetter = groupIDGetter;
    }

    public String getRegId() {
        return regId;
    }

    public String getConfigFilePath() {
        return configFilePath;
    }

    public String getConfigSection() {
        return configSection;
    }

    public boolean usesAwakening() {
        return usesAwakening;
    }

    /**
     * Registers a technique for a specific fruit.
     * This method allows you to add a technique to a fruit's list of techniques.
     * @param fruitId the unique identifier of the fruit
     * @param technique the technique to be registered
     */
    public void registerTechnique(String fruitId, Technique technique) {
        List<Technique> techniques = this.techniques.get(fruitId);
        if (techniques != null) {
            if(techniques.contains(technique)) return;
        }
        this.techniques.computeIfAbsent(fruitId, k -> new ArrayList<>()).add(technique);
        TechRegistry.registerTechnique(fruitId, technique);
    }
    /**
     * Retrieves all normal techniques (non-ultimate) for a specific fruit.
     * @param fruitId the unique identifier of the fruit
     * @return a list of normal techniques for the specified fruit
     */
    public List<Technique> getNormalTechniques(String fruitId) {
        return techniques.getOrDefault(fruitId, List.of())
                .stream().filter(t -> !t.getMeta().isUltimate()).toList();
    }
    /**
     * Retrieves all ultimate techniques for a specific fruit.
     * @param fruitId the unique identifier of the fruit
     * @return a list of ultimate techniques for the specified fruit
     */

    public List<Technique> getUltimateTechniques(String fruitId) {
        return techniques.getOrDefault(fruitId, List.of())
                .stream().filter(t -> t.getMeta().isUltimate()).toList();
    }
    /**
     * Retrieves all techniques (both normal and ultimate) for a specific fruit.
     * @param fruitId the unique identifier of the fruit
     * @return a list of all techniques for the specified fruit
     */
    public List<Technique> getAllTechniques(String fruitId) {
        return techniques.getOrDefault(fruitId, List.of());
    }
    /**
     * Unregisters a technique from a specific fruit.
     * This method allows you to remove a technique from a fruit's list of techniques.
     * @param fruitId the unique identifier of the fruit
     * @param technique the technique to be unregistered
     */
    public void unregisterTechnique(String fruitId, Technique technique) {
        List<Technique> techniques = this.techniques.get(fruitId);
        if (techniques != null) {
            techniques.remove(technique);
            if (techniques.isEmpty()) {
                this.techniques.remove(fruitId);
                LogTechnique(technique, false);
            }
        }
    }

    /**
     * Clears all registered techniques for all fruits.
     * This method removes all techniques from the registry.
     */
    public void clearAllTechniques() {
        techniques.clear();
    }

    /**
     * Retrieves a technique by its unique identifier.
     * This method searches through all registered techniques and returns the one with the specified ID.
     * @param id the unique identifier of the technique
     * @return the technique with the specified ID, or null if not found
     */
    public Technique getById(String id) {
        for (List<Technique> techniques : techniques.values()) {
            for (Technique technique : techniques) {
                if (technique.getId().equals(id)) {
                    return technique;
                }
            }
        }
        return null;
    }

    public Technique getByName(String name) {
        for (List<Technique> techniques : techniques.values()) {
            for (Technique technique : techniques) {
                if (technique.getDisplayName().equalsIgnoreCase(name)) {
                    return technique;
                }
            }
        }
        return null;
    }

    public List<String> getGroupIDs(Player player) {
        if (groupIDGetter != null) {
            return groupIDGetter.apply(player);
        }
        return List.of();
    }
}
