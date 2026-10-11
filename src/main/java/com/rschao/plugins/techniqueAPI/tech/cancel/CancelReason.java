package com.rschao.plugins.techniqueAPI.tech.cancel;

import java.io.Serializable;

public enum CancelReason implements Serializable {
    EXTERNAL,
    INTERRUPTED,
    INVALID_STATE,
    PLAYER_DEATH
}
