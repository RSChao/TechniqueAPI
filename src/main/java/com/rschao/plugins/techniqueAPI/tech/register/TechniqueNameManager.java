package com.rschao.plugins.techniqueAPI.tech.register;

import java.io.File;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.rschao.plugins.techniqueAPI.tech.util.FileUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import com.rschao.plugins.techniqueAPI.tech.Technique;

public final class TechniqueNameManager {
    private static final Map<UUID, Map<String, String>> CUSTOM_NAMES = new ConcurrentHashMap<>();

    private TechniqueNameManager() {}

    public static void setCustomName(Player player, String techniqueId, String customName) {
        CUSTOM_NAMES
                .computeIfAbsent(player.getUniqueId(), k -> new ConcurrentHashMap<>())
                .put(techniqueId, customName);
    }

    public static void clearCustomName(Player player, String techniqueId) {
        Map<String, String> map = CUSTOM_NAMES.get(player.getUniqueId());
        if (map != null) {
            map.remove(techniqueId);
            if (map.isEmpty()) CUSTOM_NAMES.remove(player.getUniqueId());
        }
    }

    public static void clearAllForPlayer(Player player) {
        CUSTOM_NAMES.remove(player.getUniqueId());
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
        FileConfiguration conf = FileUtil.loadYml();
        if (conf == null) return;
        conf.set("TechniqueNames", CUSTOM_NAMES);
        FileUtil.saveYml(conf);
    }

    public static void loadAll() {
        FileConfiguration conf = FileUtil.loadYml();
        if (conf == null) return;
        Map<String, Map<String, String>> data = (Map<String, Map<String, String>>) conf.get("TechniqueNames");
        if (data != null) {
            data.forEach((uuidStr, names) -> {
                try {
                    UUID uuid = UUID.fromString(uuidStr);
                    CUSTOM_NAMES.put(uuid, new ConcurrentHashMap<>(names));
                } catch (IllegalArgumentException e) {
                    // Invalid UUID string, skip this entry
                }
            });
        }
        FileUtil.saveYml(conf);
    }
}
