package com.ilucah.dimensionlevels.commands;

import com.ilucah.dimensionlevels.DimensionLevels;
import com.ilucah.dimensionlevels.api.UserData;
import com.ilucah.dimensionlevels.levels.obj.DimensionLevel;
import com.ilucah.dimensionlevels.particle.ParticleAnimation;
import com.ilucah.dimensionlevels.utils.color.IridiumColorAPI;
import com.ilucah.dimensionlevels.utils.xutils.NMS;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.List;

public class LevelupCommand implements CommandExecutor {

    private final DimensionLevels plugin;
    private final List<String> alreadyMax, notEnoughExperience, notEnoughMobsKilled, notEnoughBlocksBroken;
    private final List<String> notEnoughPlayTime, notEnoughMoney, notEnoughCoins, noLevelupPerm;
    private final DecimalFormat format;

    public LevelupCommand(DimensionLevels plugin) {
        this.plugin = plugin;
        this.alreadyMax = IridiumColorAPI.process(plugin.getFileModule().getMessagesYML().getStringList("max-level"));
        this.notEnoughExperience = plugin.getFileModule().getMessagesYML().getStringList("not-enough-experience");
        this.notEnoughMobsKilled = IridiumColorAPI.process(plugin.getFileModule().getMessagesYML().getStringList("not-enough-mobs-killed"));
        notEnoughBlocksBroken = IridiumColorAPI.process(plugin.getFileModule().getMessagesYML().getStringList("not-enough-blocks-broken"));
        notEnoughPlayTime = IridiumColorAPI.process(plugin.getFileModule().getMessagesYML().getStringList("not-enough-play-time"));
        notEnoughMoney = IridiumColorAPI.process(plugin.getFileModule().getMessagesYML().getStringList("not-enough-money"));
        notEnoughCoins = IridiumColorAPI.process(plugin.getFileModule().getMessagesYML().getStringList("not-enough-coins"));
        noLevelupPerm = IridiumColorAPI.process(plugin.getFileModule().getMessagesYML().getStringList("no-levelup-permission"));
        this.format = new DecimalFormat("#,###");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only a player can execute this command!");
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("dimensionlevels.levelup")) {
            noLevelupPerm.iterator().forEachRemaining(m -> player.sendMessage(m));
            return true;
        }
        if (!plugin.getHandler().getStorage().hasData(player.getUniqueId()))
            plugin.getHandler().getStorage().createDefaults(player.getUniqueId());
        UserData pData = plugin.getHandler().getStorage().getUserData(player.getUniqueId());
        if (plugin.getHandler().getLevelManager().getLevel((int) pData.getLevel() + 1) == null) {
            alreadyMax.iterator().forEachRemaining(m -> player.sendMessage(m));
            return true;
        }
        DimensionLevel level = plugin.getHandler().getLevelManager().getLevel((int) pData.getLevel() + 1);
        if (level.getExperienceRequired() > pData.getExperience()) {
            notEnoughExperience.iterator().forEachRemaining(m -> player.sendMessage(IridiumColorAPI.process(m.replace("{experience}", format.format(pData.getExperience())).replace("{required-experience}", format.format(level.getExperienceRequired())))));
            return true;
        }
        if (!level.getRequirement().getMobsKilledRequirement().isEmpty()) {
            for (EntityType entityType : level.getRequirement().getMobsKilledRequirement().keySet()) {
                if (pData.getMobsKilled(entityType) < level.getRequirement().getMobsKilledRequirement().get(entityType)) {
                    notEnoughMobsKilled.iterator().forEachRemaining(m -> player.sendMessage(m));
                    return true;
                }
            }
        }
        if (!level.getRequirement().getBlockRequirement().isEmpty()) {
            for (Material material : level.getRequirement().getBlockRequirement().keySet()) {
                if (pData.getBlocksBroken(material) < level.getRequirement().getBlockRequirement().get(material)) {
                    notEnoughBlocksBroken.iterator().forEachRemaining(m -> player.sendMessage(m));
                    return true;
                }
            }
        }
        if (level.getRequirement().getPlayTime() > pData.getSecondsPlayed()) {
            notEnoughPlayTime.iterator().forEachRemaining(m -> player.sendMessage(m));
            return true;
        }
        if (plugin.getHandler().getEconomyService().isUsingVaultHook()) {
            if (plugin.getHandler().getEconomyService().getVaultHook().getBalance(player) < level.getRequirement().getMoney()) {
                notEnoughMoney.iterator().forEachRemaining(m -> player.sendMessage(m));
                return true;
            }
        }
        if (plugin.getHandler().getEconomyService().isUsingSuperMobCoinsHook()) {
            if (plugin.getHandler().getEconomyService().getSuperMobCoinHook().getMobCoins(player) < level.getRequirement().getCoins()) {
                notEnoughCoins.iterator().forEachRemaining(m -> player.sendMessage(m));
                return true;
            }
        }
        if (level.isSendingTitle()) {
            try {
                player.sendTitle(level.getTitle()[0], level.getTitle()[1]);
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
        return true;
    }
}
