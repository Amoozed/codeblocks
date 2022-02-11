package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.plugin.CodeBlocksPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

public class BlockStorage {
    CodeBlocksPlugin instance = CodeBlocksPlugin.getInstance();
    public void refreshBlocks(){
        FileConfiguration[] configs = instance.getDataManager().getAllFileConfigsInFolder("data");
        for(FileConfiguration config : configs){
            Map<String, Object> data = (Map<String, Object>) config.get("data");

        }
    }
}
