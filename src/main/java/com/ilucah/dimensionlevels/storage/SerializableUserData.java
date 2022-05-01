package com.ilucah.dimensionlevels.storage;

import com.google.gson.Gson;
import com.ilucah.dimensionlevels.api.UserData;

import java.io.Serializable;
import java.util.UUID;

public class SerializableUserData implements Serializable {

    public static SerializableUserData fromString(String string) {
        return new Gson().fromJson(string, SerializableUserData.class);
    }

    private final String uuid, name;
    private final long level, experience;
    private final double multiplier;

    public SerializableUserData(String uuid, String name, long level, long experience, double multiplier) {
        this.uuid = uuid;
        this.level = level;
        this.experience = experience;
        this.multiplier = multiplier;
        this.name = name;
    }

    public UserData craftUserData() {
        return new UserData(UUID.fromString(uuid), level, experience, multiplier);
    }

    public String serialize() {
        return new Gson().toJson(this);
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public long getLevel() {
        return level;
    }

    public long getExperience() {
        return experience;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
