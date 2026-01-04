package com.rschao.plugins.techniqueAPI.tech;

import com.rschao.plugins.techniqueAPI.event.TechniquePreRunEvent;
import com.rschao.plugins.techniqueAPI.tech.context.TechniqueContext;
import com.rschao.plugins.techniqueAPI.tech.cooldown.CooldownManager;
import com.rschao.plugins.techniqueAPI.tech.feedback.hotbarMessage;
import com.rschao.plugins.techniqueAPI.tech.selectors.TargetSelector;
import org.bukkit.Bukkit;

public final class Technique {

    private final String id;
    private final String displayName;
    private final TechniqueMeta meta;
    private final TargetSelector targetSelector;
    private final TechniqueAction action;
    private final PreRunHook preRun;

    public Technique(
            String id,
            String displayName,
            TechniqueMeta meta,
            TargetSelector targetSelector,
            TechniqueAction action,
            PreRunHook preRun
    ) {
        this.id = id;
        this.displayName = displayName;
        this.meta = meta;
        this.targetSelector = targetSelector;
        this.action = action;
        this.preRun = preRun;
    }

    public void use(TechniqueContext ctx) {
        if (CooldownManager.isOnCooldown(ctx.caster(), getId())) {
            long remaining = CooldownManager.getRemaining(ctx.caster(), getId());
            hotbarMessage.sendHotbarMessage(ctx.caster(), "On cooldown! Wait " + (remaining/1000.0) + " seconds.");
            return;
        }
        TechniquePreRunEvent event = new TechniquePreRunEvent(ctx.caster(), this);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return; // Stop execution if cancelled
        TechniqueInstance instance = createInstance(ctx);
        instance.start();
    }

    public TechniqueInstance createInstance(TechniqueContext context) {
        return new TechniqueInstance(this, context);
    }

    public String getId() { return id; }
    public String getDisplayName() { return displayName; }
    public TechniqueMeta getMeta() { return meta; }
    public TargetSelector getTargetSelector() { return targetSelector; }
    public TechniqueAction getAction() { return action; }
    public PreRunHook getPreRunHook() { return preRun; }
}
