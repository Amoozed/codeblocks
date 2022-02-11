package org.amusedd.codeblocks.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class CodeBlocksPlugin extends JavaPlugin {
    static CodeBlocksPlugin plugin;
    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static CodeBlocksPlugin getInstance(){
        return plugin;
    }
}
