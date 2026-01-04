package com.rschao.plugins.techniqueAPI.tech.util;

import com.rschao.plugins.techniqueAPI.tech.PreRunHook;
import com.rschao.plugins.techniqueAPI.tech.Technique;
import com.rschao.plugins.techniqueAPI.tech.context.TechniqueContext;

public class PreRunUtil {
    public static PreRunHook alwaysTrue() {
        return new PreRunHook() {
            @Override
            public boolean run(TechniqueContext ctx, Technique tech) {
                return true;
            }
        };

    }
}
