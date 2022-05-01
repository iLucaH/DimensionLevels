package com.ilucah.dimensionlevels.api;

import com.ilucah.dimensionlevels.storage.SerializableUserData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

import java.util.UUID;

public class UserData {

    private final OfflinePlayer player;
    private final UUID uuid;
    private double multiplier;
    private boolean toggledEffects;
    private long level;

    private long experience;

    public UserData(UUID uuid) {
        this(uuid, 0L, 0L, 1D);
    }

    public UserData(UUID uuid, long level, long experience, double multiplier) {
        this.uuid = uuid;
        this.player = Bukkit.getOfflinePlayer(uuid);
        this.level = level;
        this.experience = experience;
        this.multiplier = multiplier;
        this.toggledEffects = false;
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public UUID getUUID() {
        return uuid;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public void addMultiplier(double multiplier) {
        this.multiplier += multiplier;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public void addLevel(long level) {
        this.level += level;
    }

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }

    public void addExperience(long experience) {
        this.experience += experience;
    }

    public int getMobsKilled(EntityType mob){
        return player.getStatistic(Statistic.KILL_ENTITY, mob);
    }

    public int getBlocksBroken(Material material) {
        return player.getStatistic(Statistic.MINE_BLOCK, material);
    }

    public int getSecondsPlayed() {
        int time;
        try {
            time = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
        } catch (Exception e) {
            time = (int) ((double) player.getStatistic(Statistic.valueOf("PLAYER_ONE_TICK")) / 20D);
        }
        return time;
    }

    public void setEffectsToggled(boolean value) {
        this.toggledEffects = value;
    }

    public boolean hasToggledEffects() {
        return toggledEffects;
    }

    public SerializableUserData craftSerializableUserData() {
        return new SerializableUserData(uuid.toString(), player.getName(), level, experience, multiplier);
    }
}