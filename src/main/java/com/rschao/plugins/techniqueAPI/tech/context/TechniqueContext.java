package com.rschao.plugins.techniqueAPI.tech.context;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public final class TechniqueContext {

    private final Player caster;
    private final ItemStack sourceItem;
    private final long startTime;

    private Collection<LivingEntity> targets = List.of();
    private final Map<TechKey<?>, Object> data = new HashMap<>();

    public TechniqueContext(Player caster, ItemStack sourceItem) {
        this.caster = caster;
        this.sourceItem = sourceItem;
        this.startTime = System.currentTimeMillis();
    }

    public TechniqueContext(Player caster) {
        this.caster = caster;
        this.sourceItem = null;
        this.startTime = System.currentTimeMillis();
    }

    public Player caster() {
        return caster;
    }

    public ItemStack sourceItem() {
        return sourceItem;
    }

    public long startTime() {
        return startTime;
    }

    public void setTargets(Collection<LivingEntity> targets) {
        this.targets = targets;
    }

    public Collection<LivingEntity> targets() {
        return targets;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(TechKey<T> key) {
        return (T) data.get(key);
    }

    public <T> void set(TechKey<T> key, T value) {
        data.put(key, value);
    }
}
