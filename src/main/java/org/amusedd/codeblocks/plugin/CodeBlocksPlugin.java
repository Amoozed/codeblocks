package org.amusedd.codeblocks.plugin;

import org.amusedd.codeblocks.data.DataManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CodeBlocksPlugin extends JavaPlugin {
    static CodeBlocksPlugin plugin;

    private DataManager data;

    @Override
    public void onEnable() {
        // Plugin startup logic
        data = new DataManager();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static CodeBlocksPlugin getInstance(){
        return plugin;
    }

    public DataManager getDataManager() {
        return data;
    }
}
