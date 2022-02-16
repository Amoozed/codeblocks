package org.amusedd.codeblocks.blocks.functions;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.gui.GUI;
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
    ValueBlock eventType = new ValueBlock(ValueType.EVENT_TYPE);

    public EventFunctionBlock(String name, LinkedHashMap codeBlocks, ValueBlock eventType) {
        super(name, codeBlocks);
        this.eventType = eventType;
    }


    @Override
    public boolean canRun() {
        return super.canRun() && eventType.canRun();
    }

    @Override
    public void execute() {
        System.out.println("Cannot execute event function block directly!");
    }

    public void onEvent(Event event) {
        this.event = event;
        super.execute();
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
        data.put("event", eventType);
        return data;
    }

    public static EventFunctionBlock deserialize(Map<String, Object> map) {
        LinkedHashMap lmap = (LinkedHashMap) map.get("blocks");
        ItemStack block = (ItemStack) map.get("block");
        String name = (String) block.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(CodeBlocksPlugin.getInstance(), "name"), PersistentDataType.STRING);
        ValueBlock eventType = (ValueBlock) map.get("eventType");
        return new EventFunctionBlock(name, lmap, eventType);
    }

}
