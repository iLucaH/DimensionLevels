package com.ilucah.dimensionlevels.commands;

import com.ilucah.dimensionlevels.DimensionLevels;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelsCommand implements CommandExecutor {

    private final DimensionLevels plugin;

    public LevelsCommand(DimensionLevels plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.openInventory(plugin.getHandler().getMenuManager().getMenu());
            player.playSound(player.getLocation(), plugin.getHandler().getMenuManager().getOpenSound(), 1, 1);
            return true;
        }
        sender.sendMessage("Only a player can execute this command!");
        return true;
    }
}
