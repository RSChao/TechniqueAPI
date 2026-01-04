package com.rschao.plugins.techniqueAPI.tech.feedback;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class hotbarMessage {
    /**
     * Sends a message to the player's hotbar (action bar).
     * This method is used to display messages in the action bar, which appears above the player's hotbar.
     * @param player the player to whom the message will be sent
     * @param message the message to be displayed in the hotbar
     */
    public static void sendHotbarMessage(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(message));
    }
}
