package com.rschao.plugins.techniqueAPI.command;

import com.rschao.plugins.techniqueAPI.TechAPI;
import com.rschao.plugins.techniqueAPI.item.Items;
import com.rschao.plugins.techniqueAPI.tech.feedback.hotbarMessage;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.StringArgument;

import java.util.List;

public class Commands {

    public static CommandAPICommand command = new CommandAPICommand("channeler")
            .withPermission("techapi.channeler")
            .executesPlayer((player, args) -> {
                // Assuming Items.espada_uno is a valid ItemStack
                player.getInventory().addItem(Items.abyss("Technique Channeler"));
                hotbarMessage.sendHotbarMessage(player, "You have been given a channeler!");
            });
    public static CommandAPICommand desc = new CommandAPICommand("descripter")
            .withPermission("techapi.channeler")
            .executesPlayer((player, args) -> {
                // Assuming Items.espada_uno is a valid ItemStack
                player.getInventory().addItem(Items.description("Technique Descriptor"));
                hotbarMessage.sendHotbarMessage(player, "You have been given a channeler!");
            });
    public static CommandAPICommand container = new CommandAPICommand("container")
            .withPermission("techapi.channeler")
            .withArguments(new StringArgument("id"))
            .executesPlayer((player, args) -> {
                // Assuming Items.espada_uno is a valid ItemStack
                player.getInventory().addItem(Items.abyssContainer((String) args.get(0)));
                hotbarMessage.sendHotbarMessage(player, "You have been given a container with id " + args.get(0)+ "!");
            });
    public static CommandAPICommand withdraw = new CommandAPICommand("abysswithdraw")
            .withArguments(new IntegerArgument("number", 1, 4))
            .executesPlayer((player, args) -> {
                int amount = (int) args.getOrDefault(0, 0);
                List<String> ids = TechAPI.INSTANCE.getConfig().getStringList(player.getName() + ".groupids");
                if(ids.size() < amount) {
                    hotbarMessage.sendHotbarMessage(player, "You do not posses so many groups.");
                    return;
                }
                String groupId = ids.get(amount - 1);
                player.getInventory().addItem(Items.abyssContainer(groupId));
                ids.remove(amount - 1);
                TechAPI.INSTANCE.getConfig().set(player.getName() + ".groupids", ids);
                TechAPI.INSTANCE.saveConfig();
                TechAPI.INSTANCE.reloadConfig();
                hotbarMessage.sendHotbarMessage(player, "You have withdrawn a group (ID: " + groupId + ") from your list.");
            });
}
