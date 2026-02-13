package com.rschao.plugins.techniqueAPI.command;

import com.rschao.plugins.techniqueAPI.TechAPI;
import com.rschao.plugins.techniqueAPI.tech.feedback.hotbarMessage;
import com.rschao.plugins.techniqueAPI.tech.register.TechRegistry;
import com.rschao.plugins.techniqueAPI.tech.register.TechniqueNameManager;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.StringArgument;

import java.util.List;

public class Commands {


    public static CommandAPICommand describeTechnique = new CommandAPICommand("describeTechnique")
            .withArguments(new StringArgument("id"))
            .executesPlayer((player, args) -> {
                // Assuming Items.espada_uno is a valid ItemStack
                String techniqueId = (String) args.get(0);
                TechRegistry.summarizeGroupTechniques(player, techniqueId);
            });

    public static CommandAPICommand setCustomName = new CommandAPICommand("set")
            .withArguments(new StringArgument("techniqueId"), new StringArgument("customName"))
            .executesPlayer((player, args) -> {
                String techniqueId = (String) args.get(0);
                String customName = (String) args.get(1);
                TechniqueNameManager.setCustomName(player, techniqueId, customName);
                hotbarMessage.sendHotbarMessage(player, "Custom name set for technique " + techniqueId);
            });
    public static CommandAPICommand removeCustomName = new CommandAPICommand("remove")
            .withArguments(new StringArgument("techniqueId"))
            .executesPlayer((player, args) -> {
                String techniqueId = (String) args.get(0);
                TechniqueNameManager.clearCustomName(player, techniqueId);
                hotbarMessage.sendHotbarMessage(player, "Custom name removed for technique " + techniqueId);
            });
    public static CommandAPICommand clearCustomNames = new CommandAPICommand("clear")
            .withArguments(new StringArgument("techniqueId"))
            .executesPlayer((player, args) -> {
                String techniqueId = (String) args.get(0);
                TechniqueNameManager.clearCustomName(player, techniqueId);
                hotbarMessage.sendHotbarMessage(player, "Custom name removed for technique " + techniqueId);
            });
    public static CommandAPICommand techName = new CommandAPICommand("techName")
            .withHelp("Custom tech names", "set <techniqueId> <customName> - Set a custom name for a technique, remove <techniqueId> - Remove the custom name for a technique, clear - Clear all custom names")
            .withSubcommand(setCustomName)
            .withSubcommand(removeCustomName)
            .withSubcommand(clearCustomNames)
            .executes((sender, args) -> {
                sender.sendMessage("Usage: /techName <set/remove/clear> [args]");
            });
}
