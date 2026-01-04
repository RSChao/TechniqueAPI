package com.rschao.plugins.techniqueAPI.tech.selectors;

import com.rschao.plugins.techniqueAPI.tech.context.TechniqueContext;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class TargetSelectors {

    public static TargetSelector self() {
        return ctx -> List.of(ctx.caster());
    }

    public static TargetSelector radial(double radius) {
        return radial(radius, e -> true);
    }

    public static TargetSelector radial(double radius, Predicate<LivingEntity> filter) {
        return ctx -> {
            Location loc = ctx.caster().getLocation();
            return loc.getWorld().getLivingEntities().stream()
                    .filter(e -> e.getLocation().distanceSquared(loc) <= radius * radius)
                    .filter(filter)
                    .collect(Collectors.toList());
        };
    }

    public static TargetSelector closest(double radius, Predicate<LivingEntity> filter) {
        return ctx -> ctx.caster().getWorld().getLivingEntities().stream()
                .filter(e -> !e.equals(ctx.caster()))
                .filter(filter)
                .filter(e -> e.getLocation().distanceSquared(ctx.caster().getLocation()) <= radius * radius)
                .min(Comparator.comparingDouble(e -> e.getLocation().distanceSquared(ctx.caster().getLocation())))
                .map(List::of)
                .orElse(List.of());
    }
}
