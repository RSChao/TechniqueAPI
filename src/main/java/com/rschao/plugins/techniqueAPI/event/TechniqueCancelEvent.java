package com.rschao.plugins.techniqueAPI.event;

import com.rschao.plugins.techniqueAPI.tech.Technique;
import com.rschao.plugins.techniqueAPI.tech.cancel.CancellationToken;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TechniqueCancelEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;
    private final Player player;
    private final Technique technique;
    private final CancellationToken token;

    public TechniqueCancelEvent(Player player, Technique technique, CancellationToken token) {
        this.player = player;
        this.technique = technique;
        this.cancelled = false;
        this.token = token;
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

    public CancellationToken getToken() {
        return token;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
