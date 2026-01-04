package com.rschao.plugins.techniqueAPI.tech.cancel;

public interface CancellationToken {
    boolean isCancelled();
    CancelReason getReason();
    void cancel(CancelReason reason);
}
