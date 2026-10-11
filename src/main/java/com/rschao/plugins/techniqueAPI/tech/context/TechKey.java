package com.rschao.plugins.techniqueAPI.tech.context;

import java.io.Serializable;

public final class TechKey implements Serializable {
    private final String name;

    public TechKey(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
