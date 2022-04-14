package com.ilucah.dimensionlevels.file;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class YamlLoader {

    private String yamlName;

    private File dataFolder;
    private JavaPlugin plugin;
    private Map<String, FileConfiguration> configs;

    public YamlLoader(String yamlName, JavaPlugin plugin, File dataFolder) {
        this.yamlName = yamlName;
        this.dataFolder = dataFolder;
        this.plugin = plugin;
        configs = new HashMap<>();
    }

    public YamlLoader(String yamlName, JavaPlugin plugin) {
        this(yamlName, plugin, plugin.getDataFolder());
    }

    public FileConfiguration load() {
        try {
            if(!dataFolder.exists())
                dataFolder.mkdirs();
            File file = new File(dataFolder, yamlName + ".yml");
            boolean isResource = plugin.getResource(yamlName + ".yml") != null;
            if(!file.exists()) {
                if(isResource) {
                    plugin.saveResource(yamlName + ".yml", false);
                } else {
                    file.createNewFile();
                }
            }
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            configs.put(yamlName, config);
            return config;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FileConfiguration loadMessageFile() {
        try {
            if(!dataFolder.exists())
                dataFolder.mkdirs();
            File file = new File(dataFolder + "/messages/", yamlName + ".yml");
            boolean isResource = plugin.getResource("messages/" + yamlName + ".yml") != null;
            if(!file.exists()) {
                if(isResource) {
                    plugin.saveResource("messages/" + yamlName + ".yml", false);
                } else {
                    file.createNewFile();
                }
            }
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            configs.put(yamlName, config);
            return config;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public FileConfiguration getConfig() {
        File file = new File(dataFolder + "/messages/", yamlName + ".yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public void save(FileConfiguration config) {
        try {
            File file = new File(dataFolder, yamlName + ".yml");
            config.save(file);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void reloadConfig() {
        if(!configs.containsKey(yamlName))
            return;
        try {
            FileConfiguration config = configs.get(yamlName);
            File file = new File(dataFolder, yamlName + ".yml");
            config.load(file);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}
