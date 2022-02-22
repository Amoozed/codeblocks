package org.amusedd.codeblocks.events;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Cod;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EventBlockUtility implements Listener {
    public ArrayList<String> eventNames;

    public EventBlockUtility() {
        this.eventNames = new ArrayList<>();

        Reflections reflections = new Reflections("org.bukkit");// change to also find custom events
        Set<Class<? extends Event>> eventClasses = reflections.getSubTypesOf(Event.class).stream().
                filter(clazz -> Arrays.stream(clazz.getDeclaredFields())
                        .anyMatch(field -> field.getType().getName().endsWith("HandlerList")))
                .collect(Collectors.toSet());
        CodeBlocksPlugin.getInstance().getLogger().info("Found " + eventClasses.size() + " available events!");
        CodeBlocksPlugin.getInstance().getLogger()
                .info(eventClasses.stream().map(Class::getName).collect(Collectors.joining(", ")));
        for(String ignore : ignored) {
            eventClasses.removeIf(clazz -> clazz.getSimpleName().equals(ignore));
        }
        eventClasses.forEach(eventClass -> {
            eventNames.add(eventClass.getSimpleName());
        });

        System.out.println("Hehe: " + eventNames.size());

        // register events
        EventExecutor eventExecutor = (listener, event) -> OnEvent(event);
        eventClasses.forEach(clazz -> CodeBlocksPlugin.getInstance().getServer().getPluginManager()
                .registerEvent(clazz, this, EventPriority.MONITOR, eventExecutor, CodeBlocksPlugin.getInstance()));
    }

    private final String[] ignored = {"VehicleBlockCollisionEvent", "EntityAirChangeEvent",
            "VehicleUpdateEvent", "ChunkUnloadEvent", "ChunkLoadEvent", "StriderTemperatureChangeEvent", "PlayerStatisticIncrementEvent"};

    public void OnEvent(Event event) {
        if (Arrays.stream(ignored).anyMatch(ignored -> event.getEventName().equals(ignored))) {
            return;
        }
        Bukkit.getLogger().info(event.getEventName() + " was called!");
        CodeBlocksPlugin.getInstance().getBlockStorage().runEventBlock(event);
    }

    public ArrayList<String> getEventNames() {
        return eventNames;
    }



}

