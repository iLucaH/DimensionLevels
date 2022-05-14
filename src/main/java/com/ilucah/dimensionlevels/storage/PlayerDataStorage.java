package com.ilucah.dimensionlevels.storage;

import com.ilucah.dimensionlevels.DimensionLevels;
import com.ilucah.dimensionlevels.api.UserData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// https://pastebin.com/ZjCYe2WR
public class PlayerDataStorage {

    private final SQLiteConnection dataSource;

    private ConcurrentHashMap<UUID, UserData> dataMap = new ConcurrentHashMap<>();

    public PlayerDataStorage(DimensionLevels plugin) {
        dataSource = new SQLiteConnection(new File(plugin.getDataFolder(), "data.db"));
    }

    public void load(UUID uuid) {
        System.out.println("load");
        if (dataMap.containsKey(uuid))
            return; // already loaded
        Optional<SerializableUserData> userData = dataSource.getUserDataFromDB(uuid);
        if (userData.isPresent()) {
            System.out.println("a");
            dataMap.put(uuid, userData.get().craftUserData());
        } else {
            System.out.println("b");
            dataMap.put(uuid, new UserData(uuid));
        }
    }

    public void unload(UUID uuid) {
        System.out.println("unload");
        if (!dataMap.containsKey(uuid))
            return; // already unloaded or doesn't exist
        save(uuid);
        dataMap.remove(uuid);
    }

    public void unloadAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            unload(player.getUniqueId());
        }
        dataMap.clear();
    }

    public void saveAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            save(player.getUniqueId());
        }
    }

    public void save(UUID uuid) {
        if (!dataSource.exists(uuid.toString()))
            dataSource.submitUserData(dataMap.get(uuid).craftSerializableUserData());
        else
            dataSource.setUserDataInDB(dataMap.get(uuid).craftSerializableUserData());
    }

    public ConcurrentHashMap<UUID, UserData> getDataMap() {
        return this.dataMap;
    }

    public UserData getUserData(UUID uuid) {
        Optional<UserData> data = Optional.ofNullable(dataMap.get(uuid));
        if (!data.isPresent())
            load(uuid);
        return dataMap.get(uuid);
    }

    public boolean hasData(UUID uuid) {
        return dataMap.containsKey(uuid);
    }

    public void createDefaults(UUID uuid) {
        dataMap.put(uuid, new UserData(uuid));
    }
}