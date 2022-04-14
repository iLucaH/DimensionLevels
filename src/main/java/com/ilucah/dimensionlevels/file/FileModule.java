package com.ilucah.dimensionlevels.file;

import com.ilucah.dimensionlevels.DimensionLevels;
import org.bukkit.configuration.file.FileConfiguration;

public class FileModule {

    private FileConfiguration messagesYML;
    private FileConfiguration mobsYML;
    private FileConfiguration configYML;
    private FileConfiguration menusYML;
    private FileConfiguration levelsYML;

    private YamlLoader dataLoader;

    public FileModule(DimensionLevels plugin) {
        this.messagesYML = new YamlLoader("messages", plugin).load();
        this.configYML = new YamlLoader("config", plugin).load();
        this.menusYML = new YamlLoader("menus", plugin).load();
        this.mobsYML = new YamlLoader("mobs", plugin).load();
        this.levelsYML = new YamlLoader("levels", plugin).load();

        this.dataLoader = new YamlLoader("pData", plugin);
    }

    public FileConfiguration getMessagesYML() {
        return messagesYML;
    }

    public void setMessagesYML(FileConfiguration messagesYML) {
        this.messagesYML = messagesYML;
    }

    public FileConfiguration getMobsYML() {
        return mobsYML;
    }

    public void setMobsYML(FileConfiguration mobsYML) {
        this.mobsYML = mobsYML;
    }

    public FileConfiguration getConfigYML() {
        return configYML;
    }

    public void setConfigYML(FileConfiguration configYML) {
        this.configYML = configYML;
    }

    public FileConfiguration getMenusYML() {
        return menusYML;
    }

    public void setMenusYML(FileConfiguration menusYML) {
        this.menusYML = menusYML;
    }

    public FileConfiguration getLevelsYML() {
        return levelsYML;
    }

    public void setLevelsYML(FileConfiguration levelsYML) {
        this.levelsYML = levelsYML;
    }

    public YamlLoader getDataLoader() {
        return this.dataLoader;
    }
}
