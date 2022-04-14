package com.ilucah.dimensionlevels.api.event;

import com.ilucah.dimensionlevels.levels.model.DimensionLevel;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DimensionLevelupEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private DimensionLevel newLevel;
    private Player player;

    public DimensionLevelupEvent(Player player, DimensionLevel newLevel) {
        this.player = player;
        this.newLevel = newLevel;
    }

    public DimensionLevel getNewLevel() {
        return newLevel;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
