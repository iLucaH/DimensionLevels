package com.ilucah.dimensionlevels.commands;

import com.ilucah.dimensionlevels.DimensionLevels;
import com.ilucah.dimensionlevels.utils.color.IridiumColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.List;

public class LevelCommand implements CommandExecutor {

    private final DimensionLevels plugin;
    private List<String> messages, toggledOn, toggledOff;
    private DecimalFormat format;

    public LevelCommand(DimensionLevels plugin) {
        this.plugin = plugin;
        messages = IridiumColorAPI.process(plugin.getFileModule().getMessagesYML().getStringList("player-levels"));
        toggledOn = IridiumColorAPI.process(plugin.getFileModule().getMessagesYML().getStringList("enabled-effects"));
        toggledOff = IridiumColorAPI.process(plugin.getFileModule().getMessagesYML().getStringList("disabled-effects"));
        format = new DecimalFormat("#,###");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!plugin.getHandler().getStorage().hasData(player.getUniqueId()))
                    plugin.getHandler().getStorage().createDefaults(player.getUniqueId());
                long level = plugin.getHandler().getStorage().getUserData(player.getUniqueId()).getLevel();
                long experience = plugin.getHandler().getStorage().getUserData(player.getUniqueId()).getExperience();
                double multiplier = plugin.getHandler().getStorage().getUserData(player.getUniqueId()).getMultiplier();
                messages.iterator().forEachRemaining(m -> player.sendMessage(m.replace("{level}", format.format(level)).replace("{experience}", format.format(experience)).replace("{multi}", String.valueOf(multiplier))));
                return true;
            } else {
                sendWrongUsage(sender);
                return true;
            }
        } else if (args.length == 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!args[0].equalsIgnoreCase("toggle")) {
                    if (!plugin.getHandler().getStorage().hasData(player.getUniqueId()))
                        plugin.getHandler().getStorage().createDefaults(player.getUniqueId());
                    long level = plugin.getHandler().getStorage().getUserData(player.getUniqueId()).getLevel();
                    long experience = plugin.getHandler().getStorage().getUserData(player.getUniqueId()).getExperience();
                    double multiplier = plugin.getHandler().getStorage().getUserData(player.getUniqueId()).getMultiplier();
                    messages.iterator().forEachRemaining(m -> player.sendMessage(m.replace("{level}", format.format(level)).replace("{experience}", format.format(experience)).replace("{multi}", String.valueOf(multiplier))));
                    return true;
                } else {
                    if (plugin.getHandler().getStorage().getUserData(player.getUniqueId()).hasToggledEffects()) {
                        toggledOn.iterator().forEachRemaining(m -> player.sendMessage(m));
                        plugin.getHandler().getStorage().getUserData(player.getUniqueId()).setEffectsToggled(false);
                        return true;
                    } else {
                        toggledOff.iterator().forEachRemaining(m -> player.sendMessage(m));
                        plugin.getHandler().getStorage().getUserData(player.getUniqueId()).setEffectsToggled(true);
                        return true;
                    }
                }
            } else {
                sendWrongUsage(sender);
                return true;
            }
        } else if (args.length == 3) {
            if (sender.hasPermission("dimensionlevels.admin")) {
                if (args[0].equalsIgnoreCase("setlevel")) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                    handleLevel(sender, player, args[2], AdminArgument.SET);
                    return true;
                } else if (args[0].equalsIgnoreCase("addlevel")) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                    handleLevel(sender, player, args[2], AdminArgument.GIVE);
                    return true;
                } else if (args[0].equalsIgnoreCase("removelevel")) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                    handleLevel(sender, player, args[2], AdminArgument.REMOVE);
                    return true;
                } else if (args[0].equalsIgnoreCase("setexp")) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                    handleExperience(sender, player, args[2], AdminArgument.SET);
                    return true;
                } else if (args[0].equalsIgnoreCase("addexp")) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                    handleExperience(sender, player, args[2], AdminArgument.GIVE);
                    return true;
                } else if (args[0].equalsIgnoreCase("removeexp")) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                    handleExperience(sender, player, args[2], AdminArgument.REMOVE);
                    return true;
                } else if (args[0].equalsIgnoreCase("setmultiplier")) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                    handleMultiplier(sender, player, args[2], AdminArgument.SET);
                    return true;
                } else if (args[0].equalsIgnoreCase("addmultiplier")) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                    handleMultiplier(sender, player, args[2], AdminArgument.GIVE);
                    return true;
                } else if (args[0].equalsIgnoreCase("removemultiplier")) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                    handleMultiplier(sender, player, args[2], AdminArgument.REMOVE);
                    return true;
                } else {
                    sendWrongUsage(sender);
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");
                return true;
            }
        } else {
            if (sender.hasPermission("dimensionlevels.admin")) {
                sendWrongUsage(sender);
                return true;
            } else {
                if (!(sender instanceof Player)) {
                    sendWrongUsage(sender);
                    return true;
                }
                Player player = (Player) sender;
                if (!plugin.getHandler().getStorage().hasData(player.getUniqueId()))
                    plugin.getHandler().getStorage().createDefaults(player.getUniqueId());
                long level = plugin.getHandler().getStorage().getUserData(player.getUniqueId()).getLevel();
                long experience = plugin.getHandler().getStorage().getUserData(player.getUniqueId()).getExperience();
                double multiplier = plugin.getHandler().getStorage().getUserData(player.getUniqueId()).getMultiplier();
                messages.iterator().forEachRemaining(m -> player.sendMessage(m.replace("{level}", format.format(level)).replace("{experience}", format.format(experience)).replace("{multi}", String.valueOf(multiplier))));
                return true;
            }
        }
    }

    private void sendWrongUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Incorrect Usage:");
        sender.sendMessage(ChatColor.BLUE + "/level <addlevel:setlevel:removelevel> <name> <amount>");
        sender.sendMessage(ChatColor.BLUE + "/level <addexp:setexp:removeexp> <name> <amount>");
        sender.sendMessage(ChatColor.BLUE + "/level <addmultiplier:setmultiplier:removemultiplier> <name> <amount>");
    }

    private void handleLevel(CommandSender sender, OfflinePlayer player, String level, AdminArgument argument) {
        if (!player.isOnline()) {
            sender.sendMessage("Player is not online!");
            return;
        }
        if (argument == AdminArgument.GIVE) {
            int amount;
            try {
                amount = Integer.valueOf(level);
            } catch (NumberFormatException exception) {
                sender.sendMessage("Incorrect usage. Please enter a valid number!");
                return;
            }
            plugin.getHandler().getStorage().getUserData(player.getUniqueId()).addLevel(amount);
            sender.sendMessage("You added " + amount + " levels to " + player.getName() + "'s account.");
        } else if (argument == AdminArgument.SET) {
            int amount;
            try {
                amount = Integer.valueOf(level);
            } catch (NumberFormatException exception) {
                sender.sendMessage("Incorrect usage. Please enter a valid number!");
                return;
            }
            plugin.getHandler().getStorage().getUserData(player.getUniqueId()).setLevel(amount);
            sender.sendMessage("You set " + player.getName() + "'s level to " + amount + ".");
        } else {
            int amount = 1;
            try {
                amount = Integer.valueOf(level);
            } catch (NumberFormatException exception) {
                sender.sendMessage("Incorrect usage. Please enter a valid number!");
                return;
            }
            plugin.getHandler().getStorage().getUserData(player.getUniqueId()).setLevel(plugin.getHandler().getStorage().getUserData(player.getUniqueId()).getLevel() - amount);
            sender.sendMessage("You removed " + amount + " levels from " + player.getName() + "'s account.");
        }
    }

    private void handleExperience(CommandSender sender, OfflinePlayer player, String level, AdminArgument argument) {
        if (!player.isOnline()) {
            sender.sendMessage("Player is not online!");
            return;
        }
        if (argument == AdminArgument.GIVE) {
            int amount;
            try {
                amount = Integer.valueOf(level);
            } catch (NumberFormatException exception) {
                sender.sendMessage("Incorrect usage. Please enter a valid number!");
                return;
            }
            plugin.getHandler().getStorage().getUserData(player.getUniqueId()).addExperience(amount);
            sender.sendMessage("You added " + amount + " experience to " + player.getName() + "'s account.");
        } else if (argument == AdminArgument.SET) {
            int amount;
            try {
                amount = Integer.valueOf(level);
            } catch (NumberFormatException exception) {
                sender.sendMessage("Incorrect usage. Please enter a valid number!");
                return;
            }
            plugin.getHandler().getStorage().getUserData(player.getUniqueId()).setExperience(amount);
            sender.sendMessage("You set " + player.getName() + "'s experience to " + amount + ".");
        } else {
            int amount = 1;
            try {
                amount = Integer.valueOf(level);
            } catch (NumberFormatException exception) {
                sender.sendMessage("Incorrect usage. Please enter a valid number!");
                return;
            }
            plugin.getHandler().getStorage().getUserData(player.getUniqueId()).setExperience(plugin.getHandler().getStorage().getUserData(player.getUniqueId()).getExperience() - amount);
            sender.sendMessage("You removed " + amount + " experience from " + player.getName() + "'s account.");
        }
    }

    private void handleMultiplier(CommandSender sender, OfflinePlayer player, String level, AdminArgument argument) {
        if (!player.isOnline()) {
            sender.sendMessage("Player is not online!");
            return;
        }
        if (argument == AdminArgument.GIVE) {
            double amount;
            try {
                amount = Double.valueOf(level);
            } catch (NumberFormatException exception) {
                sender.sendMessage("Incorrect usage. Please enter a valid number!");
                return;
            }
            plugin.getHandler().getStorage().getUserData(player.getUniqueId()).addMultiplier(amount);
            sender.sendMessage("You added " + amount + " multiplier to " + player.getName() + "'s account.");
        } else if (argument == AdminArgument.SET) {
            double amount;
            try {
                amount = Double.valueOf(level);
            } catch (NumberFormatException exception) {
                sender.sendMessage("Incorrect usage. Please enter a valid number!");
                return;
            }
            // ADMIN F 2122
            plugin.getHandler().getStorage().getUserData(player.getUniqueId()).setMultiplier(amount);
            sender.sendMessage("You set " + amount + "'s multiplier to " + player.getName() + "'s account.");
        } else {
            double amount = 1;
            try {
                amount = Double.valueOf(level);
            } catch (NumberFormatException exception) {
                sender.sendMessage("Incorrect usage. Please enter a valid number!");
                return;
            }
            plugin.getHandler().getStorage().getUserData(player.getUniqueId()).setMultiplier(plugin.getHandler().getStorage().getUserData(player.getUniqueId()).getMultiplier() - amount);
            sender.sendMessage("You removed " + amount + " multiplier from " + player.getName() + "'s account.");
        }
    }

    private enum AdminArgument {
        GIVE, SET, REMOVE
    }
}
