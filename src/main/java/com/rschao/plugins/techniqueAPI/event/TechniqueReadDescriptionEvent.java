package com.rschao.plugins.techniqueAPI.event;

import com.rschao.plugins.techniqueAPI.tech.Technique;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TechniqueReadDescriptionEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final Technique technique;

    public TechniqueReadDescriptionEvent(Player player, Technique technique) {
        this.player = player;
        this.technique = technique;
        this.cancelled = false;
    }

    public Player getPlayer() {
        return player;
    }

    public Technique getTechnique() {
        return technique;
    }

    public List<String> getTechDesc(){
        return technique.getMeta().getDescription();
    }


    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
