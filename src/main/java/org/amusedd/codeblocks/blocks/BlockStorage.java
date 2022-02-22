package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.blocks.functions.EventFunctionBlock;
import org.amusedd.codeblocks.blocks.functions.FunctionBlock;
import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.bukkit.Bukkit;
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
                try{
                    CodeBlock block = (CodeBlock) memory.get(key);
                    if (block instanceof EventFunctionBlock) {
                        EventFunctionBlock eventBlock = (EventFunctionBlock) block;
                        if (eventBlocks.containsKey(eventBlock.getEventType())) {
                            eventBlocks.get(eventBlock.getEventType()).add(eventBlock);
                        } else {
                            ArrayList<EventFunctionBlock> blocks = new ArrayList<>();
                            blocks.add(eventBlock);
                            eventBlocks.put(eventBlock.getEventType(), blocks);
                        }
                    } else if (block instanceof FunctionBlock) {
                        FunctionBlock functionBlock = (FunctionBlock) block;
                        containers.put(functionBlock.getName(), functionBlock);
                    }
                } catch (Exception e){
                    Bukkit.getLogger().warning("Cannot load codeblock named \"" + key  + "\" due to " + e.getCause().toString());
                }
            }
        }
    }

    public void addEventBlock(EventFunctionBlock block) {
        String eventName = block.getEventType();
        if(eventName.equals("Undefined")) {
            Bukkit.getLogger().warning("Cannot add event block with undefined event type");
            return;
        }
        if (eventBlocks.containsKey(eventName)) {
            eventBlocks.get(eventName).add(block);
        } else {
            ArrayList<EventFunctionBlock> blocks = new ArrayList<>();
            blocks.add(block);
            eventBlocks.put(eventName, blocks);
        }
    }

    public void runEventBlock(Event event) {
        String eventName = event.getEventName();
        if (eventBlocks.containsKey(eventName)) {
            for (EventFunctionBlock block : eventBlocks.get(eventName)) {
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

    public void removeContainer(String name){
        containers.remove(name);
    }

    public ArrayList<EventFunctionBlock> getEventBlocks(){
        return eventBlocks.values().stream().reduce(new ArrayList<>(), (a, b) -> {
            a.addAll(b);
            return a;
        });
    }

    public EventFunctionBlock getEventBlock(String name){
        for(ArrayList<EventFunctionBlock> blocks : eventBlocks.values()){
            for(EventFunctionBlock block : blocks){
                if(block.getName().equals(name)) return block;
            }
        }
        Bukkit.getLogger().warning("Cannot find event block named \"" + name + "\"");
        return null;
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
}
