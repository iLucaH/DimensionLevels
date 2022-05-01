package com.ilucah.dimensionlevels.listener;

import com.ilucah.dimensionlevels.DimensionLevels;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CreateDefaultListener implements Listener {

    public DimensionLevels plugin;

    public CreateDefaultListener(DimensionLevels plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!plugin.getHandler().getStorage().hasData(event.getPlayer().getUniqueId()))
            plugin.getHandler().getStorage().load(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        plugin.getHandler().getStorage().save(event.getPlayer().getUniqueId());
    }
}
