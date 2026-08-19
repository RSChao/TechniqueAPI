package com.rschao.plugins.techniqueAPI.tech;

import com.rschao.plugins.techniqueAPI.tech.cancel.CancellationToken;
import com.rschao.plugins.techniqueAPI.tech.context.TechniqueContext;

import java.io.Serializable;

@FunctionalInterface
public interface TechniqueAction extends Serializable {

    void execute(TechniqueContext context, CancellationToken token);
}
