package com.rschao.plugins.techniqueAPI.tech.register;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import com.rschao.plugins.techniqueAPI.TechAPI;
import com.rschao.plugins.techniqueAPI.tech.util.FileUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import com.rschao.plugins.techniqueAPI.tech.Technique;

public final class TechniqueNameManager {
    private static final Map<UUID, Map<String, String>> CUSTOM_NAMES = new ConcurrentHashMap<>();
    private static volatile boolean dirty = false;
    private static final Object SAVE_LOCK = new Object();

    private TechniqueNameManager() {}

    public static void setCustomName(Player player, String techniqueId, String customName) {
        if (player == null || techniqueId == null || techniqueId.isBlank()) {
            TechAPI.LOGGER.warning("Invalid parameters for setCustomName");
            return;
        }
        if (customName == null || customName.isBlank()) {
            clearCustomName(player, techniqueId);
            return;
        }
        CUSTOM_NAMES
                .computeIfAbsent(player.getUniqueId(), k -> new ConcurrentHashMap<>())
                .put(techniqueId, customName);
        dirty = true;
    }

    public static void clearCustomName(Player player, String techniqueId) {
        Map<String, String> map = CUSTOM_NAMES.get(player.getUniqueId());
        if (map != null) {
            map.remove(techniqueId);
            if (map.isEmpty()) CUSTOM_NAMES.remove(player.getUniqueId());
            dirty = true;
        }
    }

    public static void clearAllForPlayer(Player player) {
        CUSTOM_NAMES.remove(player.getUniqueId());
        dirty = true;
    }

    public static String getDisplayName(Player viewer, Technique technique) {
        Map<String, String> map = CUSTOM_NAMES.get(viewer.getUniqueId());
        if (map != null) {
            String name = map.get(technique.getId());
            if (name != null && !name.isBlank()) return name;
        }
        return technique.getDisplayName();
    }

    public static boolean hasCustomName(Player player, String techniqueId) {
        Map<String, String> map = CUSTOM_NAMES.get(player.getUniqueId());
        return map != null && map.containsKey(techniqueId);
    }

    public static void saveAll() {
        if (!dirty) {
            return; // No changes to save
        }
        synchronized (SAVE_LOCK) {
            if (!dirty) {
                return; // Double-check pattern
            }
            try {
                FileConfiguration conf = FileUtil.loadYml();
                if (conf == null) {
                    TechAPI.LOGGER.warning("Failed to save custom technique names: config is null");
                    return;
                }
                // Convert UUID keys to strings for YAML compatibility
                Map<String, Map<String, String>> data = new ConcurrentHashMap<>();
                CUSTOM_NAMES.forEach((uuid, names) -> {
                    if (!names.isEmpty()) {
                        data.put(uuid.toString(), new ConcurrentHashMap<>(names));
                    }
                });
                conf.set("TechniqueNames", data.isEmpty() ? null : data);
                FileUtil.saveYml(conf);
                dirty = false;
                TechAPI.LOGGER.info("Saved " + data.size() + " players' custom technique names");
            } catch (Exception e) {
                TechAPI.LOGGER.log(Level.SEVERE, "Error saving custom technique names", e);
            }
        }
    }

    public static void loadAll() {
        try {
            FileConfiguration conf = FileUtil.loadYml();
            if (conf == null) {
                TechAPI.LOGGER.warning("Failed to load custom technique names: config is null");
                return;
            }
            Object rawData = conf.get("TechniqueNames");
            if (rawData == null) {
                return;
            }

            // Handle both Map and ConfigurationSection
            Map<String, Map<String, String>> data = new ConcurrentHashMap<>();
            if (rawData instanceof Map) {
                data = (Map<String, Map<String, String>>) rawData;
            } else if (rawData instanceof org.bukkit.configuration.ConfigurationSection) {
                org.bukkit.configuration.ConfigurationSection section = (org.bukkit.configuration.ConfigurationSection) rawData;
                for (String key : section.getKeys(false)) {
                    Object value = section.get(key);
                    if (value instanceof org.bukkit.configuration.ConfigurationSection) {
                        Map<String, String> innerMap = new ConcurrentHashMap<>();
                        for (String innerKey : ((org.bukkit.configuration.ConfigurationSection) value).getKeys(false)) {
                            innerMap.put(innerKey, ((org.bukkit.configuration.ConfigurationSection) value).getString(innerKey));
                        }
                        data.put(key, innerMap);
                    }
                }
            }

            int loadedCount = 0;
            for (Map.Entry<String, Map<String, String>> entry : data.entrySet()) {
                try {
                    UUID uuid = UUID.fromString(entry.getKey());
                    Map<String, String> names = entry.getValue();
                    if (names != null && !names.isEmpty()) {
                        CUSTOM_NAMES.put(uuid, new ConcurrentHashMap<>(names));
                        loadedCount++;
                    }
                } catch (IllegalArgumentException e) {
                    TechAPI.LOGGER.warning("Invalid UUID string in TechniqueNames config: " + entry.getKey());
                }
            }
            TechAPI.LOGGER.info("Loaded custom technique names for " + loadedCount + " players");
            dirty = false;
        } catch (Exception e) {
            TechAPI.LOGGER.log(Level.SEVERE, "Error loading custom technique names", e);
        }
    }
}
