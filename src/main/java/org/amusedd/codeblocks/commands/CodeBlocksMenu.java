package org.amusedd.codeblocks.commands;

import org.amusedd.codeblocks.menu.CodeBlocksBaseMenu;
import org.bukkit.entity.Player;

@CommandInfo(name = "codeblocks", playerOnly = true, devOnly = false)
public class CodeBlocksMenu extends PluginCommand {
    @Override
    public void execute(Player player, String[] args) {
        new CodeBlocksBaseMenu(player).open();
    }
}
