package com.rschao.plugins.techniqueAPI.tech.cancel;

public final class SimpleCancellationToken implements CancellationToken {

    private boolean cancelled = false;
    private CancelReason reason;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public CancelReason getReason() {
        return reason;
    }

    @Override
    public void cancel(CancelReason reason) {
        if (!cancelled) {
            this.cancelled = true;
            this.reason = reason;
        }
    }
}
