package com.ilucah.dimensionlevels.listener;

import com.ilucah.dimensionlevels.DimensionLevels;
import com.ilucah.dimensionlevels.api.UserData;
import com.ilucah.dimensionlevels.api.event.DimensionExpReceiveEvent;
import com.ilucah.dimensionlevels.api.event.DimensionLevelupEvent;
import com.ilucah.dimensionlevels.levels.model.DimensionLevel;
import com.ilucah.dimensionlevels.mob.model.DimensionMob;
import com.ilucah.dimensionlevels.particle.ParticleAnimation;
import com.ilucah.dimensionlevels.utils.xutils.NMS;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.concurrent.ThreadLocalRandom;

public class MobListener implements Listener {

    private DimensionLevels plugin;
    private boolean autolevelup;

    public MobListener(DimensionLevels plugin) {
        this.plugin = plugin;
        this.autolevelup = plugin.getFileModule().getConfigYML().getBoolean("auto-levelup");
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!(event.getEntity().getKiller() instanceof Player))
            return;
        if (!plugin.getHandler().getMobManager().isLevelMob(event.getEntityType()))
            return;
        DimensionMob mob = plugin.getHandler().getMobManager().getDimensionMob(event.getEntityType());;
        if (ThreadLocalRandom.current().nextInt(100) + 1 > mob.getObtainChance())
            return;
        Player player = event.getEntity().getKiller();
        UserData playerData = plugin.getHandler().getStorage().getUserData(player.getUniqueId());
        if (mob.getLevelRequired() > playerData.getLevel())
            return;
        if (playerData.hasToggledEffects() == false) {
            if (mob.isReceivingSound())
                player.playSound(player.getLocation(), mob.getReceiveSound(), 1, 1);
            if (mob.isSendingMessages())
                mob.getMessages().iterator().forEachRemaining(s -> player.sendMessage(s));
            if (mob.isSendingTitle()) {
                try {
                    player.sendTitle(mob.getTitle()[0], mob.getTitle()[1], 10, 35, 10);
                } catch (NoSuchMethodError e) {
                    NMS.sendTitle(player, 10, 35, 10, mob.getTitle()[0], mob.getTitle()[1]);
                }
            }
        }
        long amountToGive = (long) ((double) mob.getExperience() * playerData.getMultiplier());
        plugin.getHandler().getStorage().getUserData(player.getUniqueId()).addExperience(amountToGive);
        Bukkit.getPluginManager().callEvent(new DimensionExpReceiveEvent(player, amountToGive));
        if (!autolevelup)
            return;
        if (!player.hasPermission("dimensionlevels.levelup")) {
            return;
        }
        if (plugin.getHandler().getLevelManager().getLevel((int) playerData.getLevel() + 1) == null)
            return;
        DimensionLevel level = plugin.getHandler().getLevelManager().getLevel((int) playerData.getLevel() + 1);
        if (playerData.getExperience() < level.getExperienceRequired())
            return;
        if (!level.getRequirement().getMobsKilledRequirement().isEmpty()) {
            for (EntityType entityType : level.getRequirement().getMobsKilledRequirement().keySet()) {
                if (playerData.getMobsKilled(entityType) < level.getRequirement().getMobsKilledRequirement().get(entityType)) {
                    return;
                }
            }
        }
        if (!level.getRequirement().getBlockRequirement().isEmpty()) {
            for (Material material : level.getRequirement().getBlockRequirement().keySet()) {
                if (playerData.getBlocksBroken(material) < level.getRequirement().getBlockRequirement().get(material)) {
                    return;
                }
            }
        }
        if (level.getRequirement().getPlayTime() > playerData.getSecondsPlayed()) {
            return;
        }
        if (plugin.getHandler().getEconomyService().isUsingVaultHook()) {
            if (plugin.getHandler().getEconomyService().getVaultHook().getBalance(player) < level.getRequirement().getMoney())
                return;
        }
        if (plugin.getHandler().getEconomyService().isUsingSuperMobCoinsHook()) {
            if (plugin.getHandler().getEconomyService().getSuperMobCoinHook().getMobCoins(player) < level.getRequirement().getCoins())
                return;
        }
        if (level.isSendingTitle()) {
            try {
                player.sendTitle(level.getTitle()[0], level.getTitle()[1], 10, 35, 10);
            } catch (NoSuchMethodError e) {
                NMS.sendTitle(player, 10, 35, 10, level.getTitle()[0], level.getTitle()[1]);
            }
        }
        if (level.isPlayingSound())
            player.playSound(player.getLocation(), level.getSound(), 1, 1);
        if (level.isReceivingMessages())
            level.getMessages().iterator().forEachRemaining(s -> player.sendMessage(s));
        if (level.isUsingParticles())
            ParticleAnimation.frostLord(plugin, player, level.getParticle());
        level.getCommands().iterator().forEachRemaining(c -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c.replace("{player}", player.getName()).replace("{level}", String.valueOf(level.getLevel()))));
        plugin.getHandler().getStorage().getUserData(player.getUniqueId()).addLevel(1);
        Bukkit.getPluginManager().callEvent(new DimensionLevelupEvent(player, level));
    }

}