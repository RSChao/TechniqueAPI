package com.rschao.plugins.techniqueAPI.tech.selectors;

import com.rschao.plugins.techniqueAPI.tech.context.TechniqueContext;
import org.bukkit.entity.LivingEntity;

import java.io.Serializable;
import java.util.Collection;

@FunctionalInterface
public interface TargetSelector extends Serializable {
    Collection<LivingEntity> select(TechniqueContext context);
}