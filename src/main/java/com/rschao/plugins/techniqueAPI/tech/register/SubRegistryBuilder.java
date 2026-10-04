package com.rschao.plugins.techniqueAPI.tech.register;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Function;

public class SubRegistryBuilder {



    private final String regId;
    private String configFilePath;
    private String configSection;
    private boolean usesAwakening;
    private Function<Player, List<String>> groupIDGetter;

    public SubRegistryBuilder(String id){
        this.regId = id;
    }

    public SubRegistryBuilder setConfigFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
        return this;
    }

    public SubRegistryBuilder setConfigSection(String configSection) {
        this.configSection = configSection;
        return this;
    }

    public SubRegistryBuilder setUsesAwakening(boolean usesAwakening) {
        this.usesAwakening = usesAwakening;
        return this;
    }

    public SubRegistryBuilder setGroupIDGetter(Function<Player, List<String>> groupIDGetter) {
        this.groupIDGetter = groupIDGetter;
        return this;
    }

    public TechniqueSubRegistry build() {
        TechniqueSubRegistry subRegistry = new TechniqueSubRegistry(regId, configFilePath, configSection, usesAwakening, groupIDGetter);
        TechRegistry.registerSubRegistry(subRegistry);
        return subRegistry;
    }

}
