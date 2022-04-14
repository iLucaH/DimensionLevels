package com.ilucah.dimensionlevels.utils.xutils;

import org.bukkit.Sound;

import java.util.HashMap;

public enum XStatistic {
    PLAY_ONE_MINUTE("PLAY_ONE_TICK");

    public static final XSound[] VALUES = XSound.values();
    private static final HashMap<XSound, Sound> CACHED_SEARCH = new HashMap<>();
    private final String[] legacy;

    XStatistic(String... legacy) {
        this.legacy = legacy;
    }
}
