package org.amusedd.codeblocks;

import org.amusedd.codeblocks.util.values.ValueWrapper;
import org.bukkit.plugin.java.JavaPlugin;

public final class CodeBlocks extends JavaPlugin {
    static CodeBlocks plugin;
    CodeBlocksUtility utility;
    @Override
    public void onEnable() {
        plugin = this;
        utility = new CodeBlocksUtility();
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static CodeBlocks getPlugin(){
        return plugin;
    }

    public static CodeBlocksUtility getUtility(){
        return plugin.utility;
    }
}
