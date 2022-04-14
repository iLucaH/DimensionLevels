package com.ilucah.dimensionlevels.levels;

import com.ilucah.dimensionlevels.DimensionLevels;
import com.ilucah.dimensionlevels.levels.model.DimensionLevel;
import com.ilucah.dimensionlevels.levels.model.LevelRequirement;
import com.ilucah.dimensionlevels.utils.color.IridiumColorAPI;
import com.ilucah.dimensionlevels.utils.xutils.XSound;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class LevelManager {

    private final DimensionLevels plugin;
    private boolean particleSupport;

    private ConcurrentHashMap<Integer, DimensionLevel> levels;

    public LevelManager(DimensionLevels plugin) {
        this.plugin = plugin;
        particleSupport = plugin.getFileModule().getConfigYML().getBoolean("particle-support");
        levels = new ConcurrentHashMap<>();
        try {
            if (load()) {
                plugin.getLogger().info("Loaded experience levels.");
            } else {
                plugin.getLogger().warning("Failed to load any experience levels. Function disabled.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            plugin.getLogger().warning("Failed to load any experience levels. Function disabled.");
        }
    }

    private boolean load() {
        FileConfiguration config = plugin.getFileModule().getLevelsYML();
        if (config.getConfigurationSection("levels") == null)
            return false;
        for (String levelAsString : config.getConfigurationSection("levels").getKeys(false)) {
            String path = "levels." + levelAsString + ".";
            try {
                if (isValidInteger(levelAsString)) {
                    int levelInt = Integer.valueOf(levelAsString);
                    long experienceRequired = config.getLong(path + "experience-required");
                    List<String> messages = null;
                    if (config.getBoolean(path + "messages.enabled"))
                        messages = IridiumColorAPI.process(config.getStringList(path + "messages.messages"));
                    Sound sound = null;
                    if (config.getBoolean(path + "sound.enabled"))
                        sound = XSound.valueOf(config.getString(path + "sound.sound")).parseSound();
                    String[] title = null;
                    if (config.getBoolean(path + "title.enabled"))
                        title = new String[]{IridiumColorAPI.process(config.getString(path + "title.line1")), IridiumColorAPI.process(config.getString(path + "title.line2"))};
                    List<String> commands = config.getStringList(path + "commands");
                    ParticleEffect particle = null;
                    if (particleSupport) {
                        if (config.getBoolean(path + "particles.enabled"))
                            particle = ParticleEffect.valueOf(config.getString(path + "particles.particle"));
                    }
                    LevelRequirement requirement = new LevelRequirement(
                            config.getInt(path + "requirements.play-time"),
                            config.getStringList(path + "requirements.blocks-mined"),
                            config.getStringList(path + "requirements.mobs-killed"),
                            config.getLong(path + "requirements.coins"),
                            config.getDouble(path + "requirements.money")
                    );
                    DimensionLevel level = new DimensionLevel(levelInt, experienceRequired, messages, sound, title, particle, requirement, commands);
                    levels.put(levelInt, level);
                } else {
                    sendPrint(levelAsString);
                    continue;
                }
            } catch (Exception e) {
                sendPrint(levelAsString);
                e.printStackTrace();
                continue;
            }
        }
        return true;
    }

    private boolean isTriCharInteger(String string) {
        if (string.length() > 2 && string.contains("-"))
            return true;
        return false;
    }

    private boolean isValidInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException exc) {
            return false;
        }
    }

    private int[] getIntSet(String string) {
        String[] values = string.split("-");
        int[] set = new int[]{Integer.valueOf(values[0]), Integer.valueOf(values[1])};
        return set;
    }

    private void sendPrint(String levelAsString) {
        plugin.getLogger().warning("Failed to load level: " + levelAsString + ".");
        plugin.getLogger().warning("This will effect all levels.");
    }

    public DimensionLevel getLevel(int i) {
        return levels.get(i);
    }

    // 21 9 4
    public ConcurrentHashMap<Integer, DimensionLevel> getLevels() {
        return levels;
    }

}
