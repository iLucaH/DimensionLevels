package com.ilucah.dimensionlevels.placeholder;

import com.ilucah.dimensionlevels.DimensionLevels;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class Placeholders extends PlaceholderExpansion {

    private DimensionLevels plugin;

    public Placeholders(DimensionLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "dimensionlevels";
    }

    @Override
    public String getAuthor() {
        return "iLucaH";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getRequiredPlugin() {
        return "DimensionLevels";
    }

    @Override
    public boolean canRegister() {
        return (plugin = (DimensionLevels) Bukkit.getPluginManager().getPlugin(getRequiredPlugin())) != null;
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("level")) {
            return String.valueOf(plugin.getHandler().getStorage().getUserData(player.getUniqueId()).getLevel());
        }

        if (params.equalsIgnoreCase("exp")) {
            return String.valueOf(plugin.getHandler().getStorage().getUserData(player.getUniqueId()).getExperience());
        }

        if (params.equalsIgnoreCase("multiplier")) {
            return String.valueOf(plugin.getHandler().getStorage().getUserData(player.getUniqueId()).getMultiplier());
        }

        return null; // Placeholder is unknown by the Expansion
    }
}
