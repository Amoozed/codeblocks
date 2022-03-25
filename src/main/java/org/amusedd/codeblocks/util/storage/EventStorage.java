package org.amusedd.codeblocks.util.storage;

import org.amusedd.codeblocks.CodeBlockConstants;
import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.executables.containers.EventFunctionBlock;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class EventStorage implements Listener {
    ArrayList<String> eventNames;

    HashMap<String, ArrayList<String>> eventClassifications;

    HashMap<String, ArrayList<EventFunctionBlock>> eventBlocks;

    //Block, enchantment, entity, hanging, inventory, player, raid, server, vehicle, weather, world, other


    public EventStorage() {
        eventClassifications = new HashMap<>();
        this.eventNames = new ArrayList<>();
        eventClassifications.put("block", new ArrayList<>());
        eventClassifications.put("enchantment", new ArrayList<>());
        eventClassifications.put("entity", new ArrayList<>());
        eventClassifications.put("hanging", new ArrayList<>());
        eventClassifications.put("inventory", new ArrayList<>());
        eventClassifications.put("player", new ArrayList<>());
        eventClassifications.put("raid", new ArrayList<>());
        eventClassifications.put("server", new ArrayList<>());
        eventClassifications.put("vehicle", new ArrayList<>());
        eventClassifications.put("weather", new ArrayList<>());
        eventClassifications.put("world", new ArrayList<>());
        eventClassifications.put("other", new ArrayList<>());
        Reflections reflections = new Reflections("org.bukkit");// change to also find custom events
        Set<Class<? extends Event>> eventClasses = reflections.getSubTypesOf(Event.class).stream().
                filter(clazz -> Arrays.stream(clazz.getDeclaredFields())
                        .anyMatch(field -> field.getType().getName().endsWith("HandlerList")))
                .collect(Collectors.toSet());
        for(String ignore : ignored) {
            eventClasses.removeIf(clazz -> clazz.getSimpleName().equals(ignore) || Modifier.isAbstract(clazz.getModifiers()));
        }
        eventClasses.forEach(eventClass -> {
            //Get package name after "org.bukkit.event."
            String packageName = eventClass.getPackageName().split("\\.")[3];
            ArrayList<String> eventList;
            if (eventClassifications.containsKey(packageName)) {
                eventList = eventClassifications.get(packageName);
            } else {
                eventList = eventClassifications.get("other");
            }
            eventList.add(eventClass.getSimpleName());
            eventNames.add(eventClass.getSimpleName());
        });
        // register events
        EventExecutor eventExecutor = (listener, event) -> onEvent(event);
        eventClasses.forEach(clazz -> CodeBlocks.getPlugin().getServer().getPluginManager()
                .registerEvent(clazz, this, EventPriority.MONITOR, eventExecutor, CodeBlocks.getPlugin()));
        eventBlocks = new HashMap<>();
        FileConfiguration[] configs = CodeBlocks.getPlugin().getDataManager().getAllFileConfigsInFolder(CodeBlockConstants.CODEBLOCK_EVENT_SAVE_PATH);
        if(configs == null || configs.length == 0) {
            CodeBlocks.getPlugin().getLogger().info("No Event functions found in " + CodeBlockConstants.CODEBLOCK_EVENT_SAVE_PATH);
            return;
        }
        for (FileConfiguration config : configs) {
            try {
                EventFunctionBlock function = (EventFunctionBlock) config.get("data");
                addEventFunction(function);
            } catch (Exception e) {
                CodeBlocks.getPlugin().getLogger().warning("Failed to load event function from file " + config.getName());
                e.printStackTrace();
            }
        }
    }

    private final String[] ignored = {"VehicleBlockCollisionEvent", "EntityAirChangeEvent",
            "VehicleUpdateEvent", "ChunkUnloadEvent", "ChunkLoadEvent", "StriderTemperatureChangeEvent", "PlayerStatisticIncrementEvent"};

    public void onEvent(Event event) {
        if (Arrays.stream(ignored).anyMatch(ignored -> event.getEventName().equals(ignored))) {
            return;
        }
        if(eventBlocks.containsKey(event.getEventName())) {
            Bukkit.getLogger().info(event.getEventName() + " was called!");
            for(EventFunctionBlock eventFunctionBlock : eventBlocks.get(event.getEventName())) {
                //eventFunctionBlock.getValueSet().add("event", new ValueBlock("Event Callback", Material.BEACON, event.getClass(), event));
                eventFunctionBlock.execute();
            }
        }
    }

    public ArrayList<String> getEventNames() {
        return eventNames;
    }

    public String getEventClassification(String eventName) {
        for(String key : eventClassifications.keySet()) {
            if(eventClassifications.get(key).contains(eventName)) {
                return key;
            }
        }
        return null;
    }

    public EventFunctionBlock getEventBlock(String name) {
        for(String key : eventBlocks.keySet()) {
            ArrayList<EventFunctionBlock> blocks = eventBlocks.get(key);
            for(EventFunctionBlock block : blocks) {
                if(block.getCodeBlockType().equals(name)){
                    return block;
                }
            }
        }
        return null;
    }

    //Return all EventFunctionBlock
    public ArrayList<EventFunctionBlock> getEventBlocks() {
        ArrayList<EventFunctionBlock> blocks = new ArrayList<>();
        for(String key : eventBlocks.keySet()) {
            ArrayList<EventFunctionBlock> events = eventBlocks.get(key);
            blocks.addAll(events);
        }
        return blocks;
    }

    public void addEventFunction(EventFunctionBlock eventFunctionBlock) {
        if(!eventBlocks.containsKey(eventFunctionBlock.getEventType().getName())) {
            eventBlocks.put(eventFunctionBlock.getEventType().getName(), new ArrayList<>());
        }
        eventBlocks.get(eventFunctionBlock.getEventType().getName()).add(eventFunctionBlock);
        CodeBlocks.getPlugin().getDataManager().queueSave(CodeBlockConstants.CODEBLOCK_EVENT_SAVE_PATH, eventFunctionBlock.getName(), eventFunctionBlock, "cb");
    }
}
