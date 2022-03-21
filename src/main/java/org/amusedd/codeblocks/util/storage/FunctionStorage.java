package org.amusedd.codeblocks.util.storage;

import org.amusedd.codeblocks.CodeBlockConstants;
import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.executables.containers.StandaloneFunctionBlock;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionStorage {
    HashMap<String, StandaloneFunctionBlock> functions;

    public FunctionStorage() {
        functions = new HashMap<>();
        FileConfiguration[] configs = CodeBlocks.getPlugin().getDataManager().getAllFileConfigsInFolder(CodeBlockConstants.CODEBLOCK_FUNCTION_SAVE_PATH);
        if(configs == null || configs.length == 0) {
            CodeBlocks.getPlugin().getLogger().info("No functions found in " + CodeBlockConstants.CODEBLOCK_FUNCTION_SAVE_PATH);
            return;
        }
        for (FileConfiguration config : configs) {
            try {
                StandaloneFunctionBlock function = (StandaloneFunctionBlock) config.get("data");
                addFunction(function);
            } catch (Exception e) {
                CodeBlocks.getPlugin().getLogger().warning("Failed to load function from file " + config.getName());
                e.printStackTrace();
            }
        }
    }

    public StandaloneFunctionBlock getFunction(String name) {
        return functions.get(name);
    }

    public void addFunction(StandaloneFunctionBlock function) {
        functions.put(function.getName(), function);
        CodeBlocks.getPlugin().getDataManager().queueSave(CodeBlockConstants.CODEBLOCK_FUNCTION_SAVE_PATH, function.getName(), function, "cb");
    }

    public void removeFunction(String name) {
        functions.remove(name);
    }

    public ArrayList<StandaloneFunctionBlock> getFunctions() {
        return new ArrayList<>(functions.values());
    }
}
