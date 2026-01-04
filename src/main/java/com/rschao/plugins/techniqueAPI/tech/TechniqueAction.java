package com.rschao.plugins.techniqueAPI.tech;

import com.rschao.plugins.techniqueAPI.tech.cancel.CancellationToken;
import com.rschao.plugins.techniqueAPI.tech.context.TechniqueContext;

public interface TechniqueAction {

    void execute(TechniqueContext context, CancellationToken token);
}
