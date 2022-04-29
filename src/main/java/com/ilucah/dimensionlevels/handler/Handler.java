package com.ilucah.dimensionlevels.handler;

import com.ilucah.dimensionlevels.DimensionLevels;
import com.ilucah.dimensionlevels.economy.EconomyService;
import com.ilucah.dimensionlevels.levels.model.LevelManager;
import com.ilucah.dimensionlevels.menu.MenuManager;
import com.ilucah.dimensionlevels.mob.model.MobManager;
import com.ilucah.dimensionlevels.storage.PlayerDataStorage;

public class Handler {

    private final DimensionLevels plugin;
    private final PlayerDataStorage storage;
    private final MobManager mobManager;
    private final LevelManager levelManager;
    private final MenuManager menuManager;

    private final EconomyService economyService;

    public Handler(DimensionLevels plugin) {
        this.plugin = plugin;
        this.storage = new PlayerDataStorage(plugin);
        this.mobManager = new MobManager(plugin);
        this.levelManager = new LevelManager(plugin);
        this.menuManager = new MenuManager(plugin);

        this.economyService = new EconomyService(plugin);
    }

    public PlayerDataStorage getStorage() {
        return storage;
    }

    public MobManager getMobManager() {
        return mobManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public EconomyService getEconomyService() {
        return economyService;
    }

    // 20 RMA A WLA
    public MenuManager getMenuManager() { return menuManager; }

}
