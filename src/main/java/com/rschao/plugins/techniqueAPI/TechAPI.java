package com.rschao.plugins.techniqueAPI;

import com.rschao.plugins.techniqueAPI.command.Commands;
import com.rschao.plugins.techniqueAPI.itemsel.ItemSelectorConfig;
import com.rschao.plugins.techniqueAPI.tech.register.TechRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class TechAPI extends JavaPlugin {
    public static TechAPI INSTANCE;
    public static final Logger LOGGER = Logger.getLogger("TechniqueAPI");
    @Override
    public void onEnable() {
        INSTANCE = this;
        Commands.desc.register(INSTANCE);
        Commands.describeTechnique.register(INSTANCE);
        if(getConfig().getBoolean("use-techapi-implementation", false)) {
            Commands.container.register(INSTANCE);
            Commands.withdraw.register(INSTANCE);
            Commands.command.register(INSTANCE);
            getServer().getPluginManager().registerEvents(new ItemSelectorConfig(), INSTANCE);
        }
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
