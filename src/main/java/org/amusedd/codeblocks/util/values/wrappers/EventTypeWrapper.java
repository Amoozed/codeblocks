package org.amusedd.codeblocks.util.values.wrappers;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.util.ViewData;
import org.amusedd.codeblocks.util.fake.EventType;
import org.amusedd.codeblocks.util.values.Wrapper;
import org.bukkit.Material;

import java.util.ArrayList;

public class EventTypeWrapper implements Wrapper<EventType> {

    @Override
    public ValueBlock wrap(EventType value) {
        return new ValueBlock(new ViewData("Event to Activate", Material.BEACON, new ArrayList<>()), EventType.class, value.getName());
    }

    @Override
    public ValueBlock wrap(String value) {
        return new ValueBlock(new ViewData("Event to Activate", Material.BEACON, new ArrayList<>()), EventType.class, value);
    }

    @Override
    public String unwrapToString(EventType value) {
        return value.getName();
    }

    @Override
    public EventType unwrap(ValueBlock value) {
        return new EventType(value.getRawValue() + "");
    }

    @Override
    public Class getType() {
        return EventType.class;
    }

    @Override
    public boolean isOfType(Object value) {
        if (value instanceof String) {
            return CodeBlocks.getPlugin().getEventStorage().getEventNames().contains((String) value);
        }
        return value instanceof EventType;
    }
}
