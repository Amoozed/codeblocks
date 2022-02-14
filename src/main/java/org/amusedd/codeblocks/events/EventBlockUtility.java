package org.amusedd.codeblocks.events;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EventBlockUtility implements Listener {
    CodeBlocksPlugin plugin;

    public EventBlockUtility(CodeBlocksPlugin plugin) {
        this.plugin = plugin;

        Reflections reflections = new Reflections("org.bukkit");// change to also find custom events
        Set<Class<? extends Event>> eventClasses = reflections.getSubTypesOf(Event.class).stream().
                filter(clazz -> Arrays.stream(clazz.getDeclaredFields())
                        .anyMatch(field -> field.getType().getName().endsWith("HandlerList")))
                .collect(Collectors.toSet());
        plugin.getLogger().info("Found " + eventClasses.size() + " available events!");
        plugin.getLogger()
                .info(eventClasses.stream().map(Class::getName).collect(Collectors.joining(", ")));

        // register events
        EventExecutor eventExecutor = (listener, event) -> OnEvent(event);
        eventClasses.forEach(clazz -> plugin.getServer().getPluginManager()
                .registerEvent(clazz, this, EventPriority.MONITOR, eventExecutor, plugin));
    }

    private final String[] ignored = {"VehicleBlockCollisionEvent", "EntityAirChangeEvent",
            "VehicleUpdateEvent", "ChunkUnloadEvent", "ChunkLoadEvent"};

    public void OnEvent(Event event) {
        if (Arrays.stream(ignored).anyMatch(ignored -> event.getEventName().equals(ignored))) {
            return;
        }
        plugin.getLogger().info(event.getEventName() + " was called!");
    }



}

