package com.rschao.plugins.techniqueAPI.tech.register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.rschao.plugins.techniqueAPI.tech.Technique;
import org.bukkit.entity.Player;

public class TechRegistry {
    private static final Map<String, List<Technique>> fruitTechniques = new HashMap<>();
    /**
     * Registers a technique for a specific fruit.
     * This method allows you to add a technique to a fruit's list of techniques.
     * @param fruitId the unique identifier of the fruit
     * @param technique the technique to be registered
     */
    public static void registerTechnique(String fruitId, Technique technique) {
        fruitTechniques.computeIfAbsent(fruitId, k -> new ArrayList<>()).add(technique);
        LogTechnique(technique, true);
    }
    /**
     * Retrieves all normal techniques (non-ultimate) for a specific fruit.
     * @param fruitId the unique identifier of the fruit
     * @return a list of normal techniques for the specified fruit
     */
    public static List<Technique> getNormalTechniques(String fruitId) {
        return fruitTechniques.getOrDefault(fruitId, List.of())
            .stream().filter(t -> !t.getMeta().isUltimate()).toList();
    }
    /**
     * Retrieves all ultimate techniques for a specific fruit.
     * @param fruitId the unique identifier of the fruit
     * @return a list of ultimate techniques for the specified fruit
     */

    public static List<Technique> getUltimateTechniques(String fruitId) {
        return fruitTechniques.getOrDefault(fruitId, List.of())
            .stream().filter(t -> t.getMeta().isUltimate()).toList();
    }
    /**
     * Retrieves all techniques (both normal and ultimate) for a specific fruit.
     * @param fruitId the unique identifier of the fruit
     * @return a list of all techniques for the specified fruit
     */
    //a method to get all techniques (normal and ultimates) from a fruit
    public static List<Technique> getAllTechniques(String fruitId) {
        return fruitTechniques.getOrDefault(fruitId, List.of());
    }
    /**
     * Unregisters a technique from a specific fruit.
     * This method allows you to remove a technique from a fruit's list of techniques.
     * @param fruitId the unique identifier of the fruit
     * @param technique the technique to be unregistered
     */
    public static void unregisterTechnique(String fruitId, Technique technique) {
        List<Technique> techniques = fruitTechniques.get(fruitId);
        if (techniques != null) {
            techniques.remove(technique);
            if (techniques.isEmpty()) {
                fruitTechniques.remove(fruitId);
                LogTechnique(technique, false);
            }
        }
    }

    /**
     * Clears all registered techniques for all fruits.
     * This method removes all techniques from the registry.
     */
    public static void clearAllTechniques() {
        fruitTechniques.clear();
    }

    /**
     * Retrieves a technique by its unique identifier.
     * This method searches through all registered techniques and returns the one with the specified ID.
     * @param id the unique identifier of the technique
     * @return the technique with the specified ID, or null if not found
     */
    public static Technique getById(String id) {
        for (List<Technique> techniques : fruitTechniques.values()) {
            for (Technique technique : techniques) {
                if (technique.getId().equals(id)) {
                    return technique;
                }
            }
        }
        return null;
    }

    public static Technique getByName(String name) {
        for (List<Technique> techniques : fruitTechniques.values()) {
            for (Technique technique : techniques) {
                if (technique.getDisplayName().equalsIgnoreCase(name)) {
                    return technique;
                }
            }
        }
        return null;
    }

    protected static void LogTechnique(Technique t, boolean isRegistering){
        Logger l = Logger.getLogger("TechAPI");

        if(isRegistering) l.info("Registered Technique: " + t.getDisplayName() + " (ID: " + t.getId() + ", Ultimate: " + t.getMeta().isUltimate() + ")");
        else l.info("Unregistered Technique: " + t.getDisplayName() + " (ID: " + t.getId() + ", Ultimate: " + t.getMeta().isUltimate() + ")");
    }
    /**
     * Summarizes the registered techniques for all fruits.
     * This method logs a summary of the number of techniques, normal techniques, and ultimate techniques for each registered group.
     */
    public static void summarizeTechniques(){
        Logger l = Logger.getLogger("TechAPI");
        l.info("=== Technique Summary ===");
        for(String fruitId : fruitTechniques.keySet()){
            l.info("ID: " + fruitId);
            l.info("    Techniques: " + fruitTechniques.get(fruitId).size());
            l.info("    Non-Ultis: " + getNormalTechniques(fruitId).size());
            l.info("    Ultis: " + getUltimateTechniques(fruitId).size());
        }
        l.info("=== End of Summary ===");
    }
    /**
     * Summarizes the registered techniques for a specific fruit and sends the summary to a player.
     * @param p the player to send the summary to
     * @param fruitId the unique identifier of the fruit
     */
    public static void summarizeGroupTechniques(Player p, String fruitId){
        if(!fruitTechniques.containsKey(fruitId)){
            p.sendMessage("No techniques registered for group ID: " + fruitId);
            return;
        }
        p.sendMessage("=== Group Technique Summary ===");
        p.sendMessage("ID: " + fruitId);
        for(Technique technique : fruitTechniques.get(fruitId)){
            p.sendMessage("    ID: " + technique.getId());
            p.sendMessage("    Name " + technique.getDisplayName());
            p.sendMessage("    Ultimate: " + technique.getMeta().isUltimate());
            p.sendMessage("    Description: ");
            for(String line : technique.getMeta().getDescription()){
                p.sendMessage("        " + line);
            }
            p.sendMessage("    Cooldown: " + technique.getMeta().getCooldownMillis());
        }
        p.sendMessage("=== End of Summary ===");
    }
    /**
     * Retrieves a list of all registered fruit IDs.
     * @return a list of registered fruit IDs
     */
    public static List<String> getRegisteredFruitIds() {
        return new ArrayList<>(fruitTechniques.keySet());
    }



}
