package com.rschao.plugins.techniqueAPI.event;

import com.rschao.plugins.techniqueAPI.tech.Technique;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TechniqueRunEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;
    private final Player player;
    private final Technique technique;

    public TechniqueRunEvent(Player player, Technique technique) {
        this.player = player;
        this.technique = technique;
        this.cancelled = false;
    }
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public Player getPlayer() {
        return player;
    }

    public Technique getTechnique() {
        return technique;
    }


    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
