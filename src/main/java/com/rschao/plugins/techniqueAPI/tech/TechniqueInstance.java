package com.rschao.plugins.techniqueAPI.tech;

import com.rschao.plugins.techniqueAPI.tech.cancel.CancelReason;
import com.rschao.plugins.techniqueAPI.tech.cancel.CancellationToken;
import com.rschao.plugins.techniqueAPI.tech.cancel.SimpleCancellationToken;
import com.rschao.plugins.techniqueAPI.tech.context.TechniqueContext;

public final class TechniqueInstance {

    private final Technique technique;
    private final TechniqueContext context;
    private final CancellationToken token = new SimpleCancellationToken();

    public TechniqueInstance(Technique technique, TechniqueContext context) {
        this.technique = technique;
        this.context = context;
    }

    public void start() {
        context.setTargets(
                technique.getTargetSelector().select(context)
        );
        PreRunHook preRunHook = technique.getPreRunHook();
        if (preRunHook != null && !preRunHook.run(context, technique)) {
            // pre-run returned false → cancel execution
            context.caster().sendMessage("You cannot use this technique right now!");
            return;
        }

        if (!token.isCancelled()) {
            technique.getAction().execute(context, token);
        }
    }

    public void cancel(CancelReason reason) {
        token.cancel(reason);
    }

    public CancellationToken token() {
        return token;
    }
}
