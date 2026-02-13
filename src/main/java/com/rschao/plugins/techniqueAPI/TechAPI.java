package com.rschao.plugins.techniqueAPI;

import com.rschao.plugins.techniqueAPI.command.Commands;
import com.rschao.plugins.techniqueAPI.tech.register.TechRegistry;
import com.rschao.plugins.techniqueAPI.tech.register.TechniqueNameManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class TechAPI extends JavaPlugin {
    public static TechAPI INSTANCE;
    public static final Logger LOGGER = Logger.getLogger("TechniqueAPI");
    @Override
    public void onEnable() {
        INSTANCE = this;
        TechniqueNameManager.loadAll();
        Commands.describeTechnique.register(INSTANCE);
        Commands.techName.register(INSTANCE);
        // Plugin startup logic
        LOGGER.info("TechniqueAPI enabled!");
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, TechRegistry::summarizeTechniques, 20L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        LOGGER.info("TechniqueAPI disabled!");
        TechniqueNameManager.saveAll();
    }
}
