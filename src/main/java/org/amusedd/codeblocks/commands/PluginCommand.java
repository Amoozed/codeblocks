package org.amusedd.codeblocks.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PluginCommand implements CommandExecutor {
    private final CommandInfo commandInfo;

    public PluginCommand() {
        commandInfo = getClass().getDeclaredAnnotation(CommandInfo.class);
        Objects.requireNonNull(commandInfo, "Commands must have CommandInfo annotations");
    }

    public CommandInfo getCommandInfo() {
        return commandInfo;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println("Command " + label + " ran");
        if(!(commandInfo.permissions().isEmpty())) {
            if(!sender.hasPermission(commandInfo.permissions()) || (commandInfo.devOnly() && !sender.isOp())) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to execute this command.");
                return true;
            }
        }
        if(commandInfo.playerOnly()) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only in-game players can execute this command.");
                return true;
            }
            execute((Player) sender, args);
            return true;
        }
        execute(sender, args);
        return true;

    }

    public void execute(Player player, String[] args) {}
    public void execute(CommandSender sender, String[] args) {}
}
