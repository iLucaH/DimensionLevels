package com.ilucah.dimensionlevels.economy.money;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

public class VaultHook {

    private Economy economy;

    public VaultHook(Plugin plugin) {
        if (setupEcon()) {
            plugin.getLogger().info("Successfully hooked into Vault as a money dependency!");
        }
    }

    private boolean setupEcon() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public double getBalance(OfflinePlayer player) {
        return economy.getBalance(player);
    }
}
