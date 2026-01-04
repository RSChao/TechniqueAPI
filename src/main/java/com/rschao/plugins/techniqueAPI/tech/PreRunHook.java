package com.rschao.plugins.techniqueAPI.tech;

import com.rschao.plugins.techniqueAPI.tech.context.TechniqueContext;

@FunctionalInterface
public interface PreRunHook {
    /**
     * @param ctx The technique context
     * @param tech The technique being executed
     * @return true to continue execution, false to cancel
     */
    boolean run(TechniqueContext ctx, Technique tech);
}
