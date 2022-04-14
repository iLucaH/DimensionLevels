package com.ilucah.dimensionlevels.mob;

import com.ilucah.dimensionlevels.DimensionLevels;
import com.ilucah.dimensionlevels.mob.model.DimensionMob;
import com.ilucah.dimensionlevels.utils.color.IridiumColorAPI;
import com.ilucah.dimensionlevels.utils.xutils.XSound;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MobManager {

    private final DimensionLevels plugin;
    private final ConcurrentHashMap<EntityType, DimensionMob> mobs;

    public MobManager(DimensionLevels plugin) {
        this.plugin = plugin;
        mobs = new ConcurrentHashMap<>();
        loadMobs();
    }

    // 10 11 70
    public void loadMobs() {
        FileConfiguration config = plugin.getFileModule().getMobsYML();
        for (String mob : config.getConfigurationSection("mobs").getKeys(false)) {
            long experience = config.getLong("mobs." + mob + ".experience");
            int chance = config.getInt("mobs." + mob + ".chance");
            List<String> messages = null;
            if (config.getBoolean("mobs." + mob + ".messages.enabled"))
                messages = IridiumColorAPI.process(config.getStringList("mobs." + mob + ".messages.messages"));
            Sound sound = null;
            if (config.getBoolean("mobs." + mob + ".sound.enabled"))
                sound = XSound.valueOf(config.getString("mobs." + mob + ".sound.sound")).parseSound();
            String[] title = null;
            if (config.getBoolean("mobs." + mob + ".title.enabled"))
                title = new String[] {IridiumColorAPI.process(config.getString("mobs." + mob + ".title.line1")), IridiumColorAPI.process(config.getString("mobs." + mob + ".title.line2"))};
            int levelRequired = config.getInt("mobs." + mob + ".level-required");
            DimensionMob dimensionMob = new DimensionMob(mob, chance, experience, messages, sound, title, levelRequired);
            mobs.put(dimensionMob.getMob(), dimensionMob);
        }
    }

    public boolean isLevelMob(EntityType mobType) {
        return mobs.containsKey(mobType);
    }

    public DimensionMob getDimensionMob(EntityType mobType) {
        return mobs.get(mobType);
    }
}
