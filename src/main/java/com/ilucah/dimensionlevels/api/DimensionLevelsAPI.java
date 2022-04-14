package com.ilucah.dimensionlevels.api;

import com.ilucah.dimensionlevels.DimensionLevels;
import com.ilucah.dimensionlevels.levels.model.DimensionLevel;
import com.ilucah.dimensionlevels.mob.model.DimensionMob;
import org.bukkit.entity.EntityType;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DimensionLevelsAPI {

    public static DimensionLevelsAPI getInstance() {
        return DimensionLevels.getInstance().getApi();
    }

    private final DimensionLevels plugin;

    public DimensionLevelsAPI(final DimensionLevels plugin) {
        this.plugin = plugin;
    }

    public UserData setPlayerData(UUID uuid) {
        return plugin.getHandler().getStorage().getUserData(uuid);
    }

    public void setLevel(UUID uuid, long level) {
        plugin.getHandler().getStorage().getUserData(uuid).setLevel(level);
    }

    public void addLevel(UUID uuid, long level) {
        plugin.getHandler().getStorage().getUserData(uuid).addLevel(level);
    }

    public void removeLevel(UUID uuid, long level) {
        plugin.getHandler().getStorage().getUserData(uuid).setLevel(plugin.getHandler().getStorage().getUserData(uuid).getLevel() - level);
    }

    public long getLevel(UUID uuid) {
        return plugin.getHandler().getStorage().getUserData(uuid).getLevel();
    }

    public long getExperience(UUID uuid) {
        return plugin.getHandler().getStorage().getUserData(uuid).getExperience();
    }

    public void setExperience(UUID uuid, long experience) {
        plugin.getHandler().getStorage().getUserData(uuid).setExperience(experience);
    }

    public void addExperience(UUID uuid, long experience) {
        plugin.getHandler().getStorage().getUserData(uuid).addExperience(experience);
    }

    public void removeExperience(UUID uuid, long experience) {
        plugin.getHandler().getStorage().getUserData(uuid).setExperience(plugin.getHandler().getStorage().getUserData(uuid).getExperience() - experience);
    }

    public Collection<UserData> getAllUserData() {
        return plugin.getHandler().getStorage().getDataMap().values();
    }

    public boolean hasUserData(UUID uuid) {
        return plugin.getHandler().getStorage().hasData(uuid);
    }

    public void createUserDataDefaults(UUID uuid) {
        plugin.getHandler().getStorage().createDefaults(uuid);
    }

    public double getMultiplier(UUID uuid) {
        return plugin.getHandler().getStorage().getUserData(uuid).getMultiplier();
    }

    public void setMultiplier(UUID uuid, double multiplier) {
        plugin.getHandler().getStorage().getUserData(uuid).setMultiplier(multiplier);
    }

    public void addMultiplier(UUID uuid, double multiplier) {
        plugin.getHandler().getStorage().getUserData(uuid).addMultiplier(multiplier);
    }

    // 3 HST SAGA
    public void removeMultiplier(UUID uuid, double multiplier) {
        plugin.getHandler().getStorage().getUserData(uuid).setMultiplier(plugin.getHandler().getStorage().getUserData(uuid).getMultiplier() - multiplier);
    }

    public DimensionLevel getLevel(int level) {
        return plugin.getHandler().getLevelManager().getLevel(level);
    }

    public DimensionMob getMob(EntityType mob) {
        return plugin.getHandler().getMobManager().getDimensionMob(mob);
    }

    public boolean isDimensionMob(EntityType mob) {
        return plugin.getHandler().getMobManager().isLevelMob(mob);
    }

    public ConcurrentHashMap<Integer, DimensionLevel> getAllLevels() {
        return plugin.getHandler().getLevelManager().getLevels();
    }
}
