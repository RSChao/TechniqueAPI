package com.rschao.plugins.techniqueAPI.tech;

import java.util.List;

public final class TechniqueMeta {

    private final boolean ultimate;
    private final long cooldownMillis;
    private final List<String> description;

    public TechniqueMeta(boolean ultimate, long cooldownMillis, List<String> description) {
        this.ultimate = ultimate;
        this.cooldownMillis = cooldownMillis;
        this.description = description;
    }

    public boolean isUltimate() {
        return ultimate;
    }

    public long getCooldownMillis() {
        return cooldownMillis;
    }

    public List<String> getDescription() {
        return description;
    }
}