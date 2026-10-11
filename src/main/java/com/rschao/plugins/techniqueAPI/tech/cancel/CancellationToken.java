package com.rschao.plugins.techniqueAPI.tech.cancel;

import java.io.Serializable;

public interface CancellationToken extends Serializable {
    boolean isCancelled();
    CancelReason getReason();
    void cancel(CancelReason reason);
}
