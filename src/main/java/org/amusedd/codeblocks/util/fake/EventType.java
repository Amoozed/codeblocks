package org.amusedd.codeblocks.util.fake;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.Map;

public class EventType implements ConfigurationSerializable {
    Event event;
    String name;

    public EventType(Event event) {
        this.event = event;
        this.name = event.getEventName();
    }

    public EventType(String name){
        this.name = name;
    }

    public Event getEvent() {
        return event;
    }

    public String getName() {
        return name;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        return map;
    }

    public static EventType deserialize(Map<String, Object> map){
        return new EventType((String)map.get("name"));
    }

    @Override
    public String toString() {
        return name;
    }
}
