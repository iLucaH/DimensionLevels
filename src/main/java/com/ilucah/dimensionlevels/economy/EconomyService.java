package com.ilucah.dimensionlevels.economy;

import com.ilucah.dimensionlevels.DimensionLevels;
import com.ilucah.dimensionlevels.economy.coin.SuperMobCoinHook;
import com.ilucah.dimensionlevels.economy.money.VaultHook;

public class EconomyService {

    private VaultHook vaultHook;
    private SuperMobCoinHook superMobCoinHook;
    private boolean isUsingVault, isUsingSuperMobCoins;

    public EconomyService(DimensionLevels plugin) {
        isUsingVault = plugin.getFileModule().getConfigYML().getBoolean("economy-dependencies.money.vault");
        isUsingSuperMobCoins = plugin.getFileModule().getConfigYML().getBoolean("economy-dependencies.coin.super-mob-coins");
        if (isUsingVault)
            vaultHook = new VaultHook(plugin);
        if (isUsingSuperMobCoins)
            superMobCoinHook = new SuperMobCoinHook();
    }

    public VaultHook getVaultHook() {
        return vaultHook;
    }

    public boolean isUsingVaultHook() {
        return isUsingVault;
    }

    public boolean isUsingSuperMobCoinsHook() {
        return isUsingSuperMobCoins;
    }

    public SuperMobCoinHook getSuperMobCoinHook() {
        return superMobCoinHook;
    }

}
