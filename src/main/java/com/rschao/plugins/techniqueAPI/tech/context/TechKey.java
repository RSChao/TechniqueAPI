package com.rschao.plugins.techniqueAPI.tech.context;

public final class TechKey<T> {
    private final String name;

    public TechKey(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
