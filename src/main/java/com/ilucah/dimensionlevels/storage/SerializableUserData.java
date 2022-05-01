package com.ilucah.dimensionlevels.storage;

import com.ilucah.dimensionlevels.api.UserData;

import java.io.Serializable;
import java.util.UUID;

public class SerializableUserData implements Serializable {

    private final String uuid;
    private final long level, experience;
    private final double multiplier;

    public SerializableUserData(String uuid, long level, long experience, double multiplier) {
        this.uuid = uuid;
        this.level = level;
        this.experience = experience;
        this.multiplier = multiplier;
    }

    public UserData craftUserData() {
        return new UserData(UUID.fromString(uuid), level, experience, multiplier);
    }
}
