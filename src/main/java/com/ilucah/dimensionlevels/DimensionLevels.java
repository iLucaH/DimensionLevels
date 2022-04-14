package com.ilucah.dimensionlevels;

import com.ilucah.dimensionlevels.api.DimensionLevelsAPI;
import com.ilucah.dimensionlevels.commands.LevelCommand;
import com.ilucah.dimensionlevels.commands.LevelsCommand;
import com.ilucah.dimensionlevels.commands.LevelupCommand;
import com.ilucah.dimensionlevels.file.FileModule;
import com.ilucah.dimensionlevels.handler.Handler;
import com.ilucah.dimensionlevels.listener.CreateDefaultListener;
import com.ilucah.dimensionlevels.listener.MobListener;
import com.ilucah.dimensionlevels.placeholder.Placeholders;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

public final class DimensionLevels extends JavaPlugin {

    private static DimensionLevels instance;

    private FileModule fileModule;
    private Handler handler;

    private DimensionLevelsAPI api;

    @Override
    public void onEnable() {
        instance = this;
        fileModule = new FileModule(this);
        handler = new Handler(this);
        handler.getStorage().load();

        getServer().getPluginManager().registerEvents(new CreateDefaultListener(this), this);
        getServer().getPluginManager().registerEvents(new MobListener(this), this);
        getCommand("level").setExecutor(new LevelCommand(this));
        getCommand("levelup").setExecutor(new LevelupCommand(this));
        getCommand("levels").setExecutor(new LevelsCommand(this));
        savePeriodically();

        api = new DimensionLevelsAPI(this);

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            if (new Placeholders(this).register())
                getLogger().info("Registered a PlaceholderAPI hook");
            else
                getLogger().warning("Failed to register a PlaceholderAPI hook");
        }
    }

    @Override
    public void onDisable() {
        try {
            handler.getStorage().save();
            getLogger().warning("Saved data.");
        } catch (IOException e) {
            getLogger().warning("Failed to save data.");
        }
    }

    public void savePeriodically() {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    handler.getStorage().save();
                    System.out.println("Saved data file - DimensionLevels");
                } catch (IOException e) {
                    System.out.println("Failed to save data file - DimensionLevels. Cancelling future save tasks.");
                    e.printStackTrace();
                    cancel();
                }
            }
        }.runTaskTimerAsynchronously(this, 7450, 7450);
    }

    public Handler getHandler() {
        return handler;
    }

    public FileModule getFileModule() {
        return fileModule;
    }

    public DimensionLevelsAPI getApi() {
        return api;
    }

    public static DimensionLevels getInstance() {
        return instance;
    }
}
