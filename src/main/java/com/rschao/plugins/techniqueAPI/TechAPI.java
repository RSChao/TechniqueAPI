package com.rschao.plugins.techniqueAPI;

import com.rschao.plugins.techniqueAPI.tech.register.TechRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class TechAPI extends JavaPlugin {

    public static final Logger LOGGER = Logger.getLogger("TechniqueAPI");
    @Override
    public void onEnable() {
        // Plugin startup logic
        LOGGER.info("TechniqueAPI enabled!");
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            TechRegistry.summarizeTechniques();
        }, 20L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        LOGGER.info("TechniqueAPI disabled!");
    }
}
