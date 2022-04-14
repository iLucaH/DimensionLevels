package com.ilucah.dimensionlevels.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DimensionExpReceiveEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private long experience;

    public DimensionExpReceiveEvent(Player player, long experience) {
        this.player = player;
        this.experience = experience;
    }

    public Player getPlayer() {
        return player;
    }

    public long getExperience() {
        return experience;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
