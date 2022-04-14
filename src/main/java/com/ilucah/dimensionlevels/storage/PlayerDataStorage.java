package com.ilucah.dimensionlevels.storage;

import com.ilucah.dimensionlevels.DimensionLevels;
import com.ilucah.dimensionlevels.api.UserData;
import com.ilucah.dimensionlevels.file.YamlLoader;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerDataStorage {

    private YamlLoader dataLoader;
    private FileConfiguration dataConfiguration;
    private ConcurrentHashMap<UUID, UserData> dataMap;

    public PlayerDataStorage(DimensionLevels plugin) {
        this.dataLoader = plugin.getFileModule().getDataLoader();
        dataConfiguration = dataLoader.load();
    }

    public void load() {
        dataMap = new ConcurrentHashMap<>();
        if (dataConfiguration.getConfigurationSection("pdata") == null)
            return;
        for (String k : dataConfiguration.getConfigurationSection("pdata").getKeys(false)) {
            UUID uuid = UUID.fromString(k);
            long level = dataConfiguration.getLong("pdata." + k + ".level");
            long experience = dataConfiguration.getLong("pdata." + k + ".experience");
            double multiplier = dataConfiguration.getDouble("pdata." + k + ".multiplier");
            dataMap.put(uuid, new UserData(uuid, level, experience, multiplier));
        }
    }

    public void save() throws IOException {
        for (UUID uuid : dataMap.keySet()) {
            UserData user = dataMap.get(uuid);
            dataConfiguration.set("pdata." + uuid + ".level", user.getLevel());;
            dataConfiguration.set("pdata." + uuid + ".experience", user.getExperience());
            dataConfiguration.set("pdata." + uuid + ".multiplier", user.getMultiplier());
        }
        dataLoader.save(dataConfiguration);
    }

    public ConcurrentHashMap<UUID, UserData> getDataMap() {
        return this.dataMap;
    }

    public UserData getUserData(UUID uuid) {
        return dataMap.get(uuid);
    }

    public boolean hasData(UUID uuid) {
        return (dataMap.containsKey(uuid));
    }

    public void createDefaults(UUID uuid) {
        dataMap.put(uuid, new UserData(uuid));
    }
}