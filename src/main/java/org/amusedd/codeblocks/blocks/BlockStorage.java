package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.blocks.functions.EventFunctionBlock;
import org.amusedd.codeblocks.blocks.functions.FunctionBlock;
import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlockStorage {
    CodeBlocksPlugin instance = CodeBlocksPlugin.getInstance();
    HashMap<String, ArrayList<EventFunctionBlock>> eventBlocks = new HashMap<>();
    HashMap<String, CodeBlockContainer> containers = new HashMap<>();
    HashMap<String, ItemStack> previewBlocks = new HashMap<>();

    public void refreshBlocks() {
        FileConfiguration[] config = instance.getDataManager().getAllFileConfigsInFolder("data");
        if(config == null || config.length == 0) return;
        for (FileConfiguration file : config) {
            MemorySection memory = (MemorySection) file.get("data");
            for (String key : memory.getKeys(false)) {
                CodeBlock block = (CodeBlock) memory.get(key);
                if (block instanceof FunctionBlock) {
                    FunctionBlock functionBlock = (FunctionBlock) block;
                    containers.put(functionBlock.getName(), functionBlock);
                }
            }
            /*
                       for (String key : data.keySet()) {
                Map<String, Object> blockData = (Map<String, Object>) data.get(key);
                CodeBlock block = deserializeCodeBlock(blockData);
                if (block instanceof FunctionBlock) {
                    FunctionBlock functionBlock = (FunctionBlock) block;
                    functionBlocks.put(functionBlock.getName(), functionBlock);
                }
            }
             */
        }
    }

    public void runEventBlock(Event event) {
        String eventName = event.getEventName();
        if (eventBlocks.containsKey(eventName)) {
            for (EventFunctionBlock block : eventBlocks.get(eventName)) {
                //Class<? extends Event> eventClass = (Class<? extends Event>) Class.forName(eventName);
                block.onEvent(event);
            }
        }

    }

    public void addFunctionBlock(FunctionBlock block) {
        containers.put(block.getName(), block);
    }

    public void addPreviewBlock(String name, ItemStack item) {
        previewBlocks.put(name, item);
    }

    public ArrayList<ItemStack> getPreviewBlocks(){
        return new ArrayList<>(previewBlocks.values());
    }

    public CodeBlockContainer getContainerBlock(String name){
        return containers.get(name);
    }

    public ArrayList<CodeBlockContainer> getContainers() {
        return new ArrayList<>(containers.values());
    }

    public void prepareSave(){
        Map<String, Object> data = new HashMap<>();
        for(CodeBlockContainer block : containers.values()){
            data.put(block.getName(), block);
        }
        for(String event : eventBlocks.keySet()){
            for(EventFunctionBlock block : eventBlocks.get(event)){
                data.put(block.getName(), block);
            }
        }
        instance.getDataManager().directSave("data", "data", data);
    }

    /*
    CodeBlock deserializeCodeBlock(Map<String, Object> blockData){
        try{
            if (blockData.containsKey("type")) {
                String type = (String) blockData.get("type");
                Class<? extends CodeBlock> clazz = (Class<? extends CodeBlock>) Class.forName(type);
                Method m = clazz.getMethod("deserialize", Map.class);
                CodeBlock block = (CodeBlock) m.invoke(clazz, blockData);
                return block;
            }
        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }

     */
}
