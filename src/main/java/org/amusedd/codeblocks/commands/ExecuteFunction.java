package org.amusedd.codeblocks.commands;

import org.amusedd.codeblocks.CodeBlocks;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "executefunction", playerOnly = false, devOnly = false)
public class ExecuteFunction extends PluginCommand{
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("/executefunction <name of function>");
            return;
        }
        String function = args[0];
        CodeBlocks.getPlugin().getFunctionStorage().getFunction(function).execute();
    }
}
