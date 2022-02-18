package org.amusedd.codeblocks.blocks.functions;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.input.ValueSet;
import org.amusedd.codeblocks.input.ValueType;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class EventFunctionBlock extends FunctionBlock {
    Event event;
    ValueSet set;

    public EventFunctionBlock(ValueBlock name, LinkedHashMap codeBlocks, ValueBlock eventType) {
        super(name, codeBlocks);
    }

    public EventFunctionBlock(){

    }


    @Override
    public boolean run() {
        System.out.println("Cannot execute event function block directly!");
        return true;
    }

    public void onEvent(Event event) {
        this.event = event;
        super.execute();
    }


    @Override
    public ValueSet getValueSet() {
        if(set == null) {
            set = super.getValueSet();
            set.addValueBlock("event_type", new ValueBlock(ValueType.EVENT_TYPE));
        }
        return set;
    }
    public Event getEvent() {
        if(event == null){
            throw new IllegalStateException("Event has not been called yet!");
        } else {
            return event;
        }
    }


    @Override
    public void onGUIRightClick(Player player, GUI gui) {}

    public String getEventType(){
       return event.getEventName();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("event", set.getValueBlock("event_type").getValue());
        return data;
    }

    public static EventFunctionBlock deserialize(Map<String, Object> map) {
        LinkedHashMap lmap = (LinkedHashMap) map.get("blocks");
        ItemStack block = (ItemStack) map.get("block");
        ValueBlock name = (ValueBlock) map.get("name");
        ValueBlock eventType = (ValueBlock) map.get("eventType");
        return new EventFunctionBlock(name, lmap, eventType);
    }



}
