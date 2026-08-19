package com.rschao.plugins.techniqueAPI.tech;

import com.rschao.plugins.techniqueAPI.tech.context.TechniqueContext;

import java.io.Serializable;

public interface PreRunHook extends Serializable{
    /**
     * @param ctx The technique context
     * @param tech The technique being executed
     * @return true to continue execution, false to cancel
     */
    boolean run(TechniqueContext ctx, Technique tech);
}
