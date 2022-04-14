package com.ilucah.dimensionlevels.economy.coin;

import me.swanis.mobcoins.MobCoinsAPI;
import org.bukkit.OfflinePlayer;

public class SuperMobCoinHook {

    public SuperMobCoinHook() {}

    public long getMobCoins(OfflinePlayer player) {
        return MobCoinsAPI.getProfileManager().getProfile(player.getUniqueId()).getMobCoins();
    }

}
