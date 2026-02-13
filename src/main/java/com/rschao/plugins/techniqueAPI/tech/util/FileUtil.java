package com.rschao.plugins.techniqueAPI.tech.util;

import com.rschao.plugins.techniqueAPI.TechAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class FileUtil {
    /**
     * Loads a yml file from the plugin's data folder. If the file does not exist, it will be created.
     */
    public static FileConfiguration loadYml() {
        return TechAPI.INSTANCE.getConfig();
    }

    public static void saveYml(FileConfiguration config) {
        TechAPI.INSTANCE.saveConfig();
    }
}
