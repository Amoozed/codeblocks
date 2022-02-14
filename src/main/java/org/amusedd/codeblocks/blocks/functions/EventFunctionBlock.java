package org.amusedd.codeblocks.blocks.functions;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class EventFunctionBlock extends FunctionBlock {
    Event event;

    public EventFunctionBlock(String name, ArrayList<CodeBlock> codeBlocks, String eventType) {
        super(name, codeBlocks);
        setTag("event", getEventType(), PersistentDataType.STRING);
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

    public String getEventType(){
       return event.getEventName();
    }

    @Override
    public Map<String, Object> serialize() {
        return super.serialize();
    }

    public static EventFunctionBlock deserialize(Map<String, Object> map) {
        ArrayList<CodeBlock> codeBlocks = new ArrayList<>();
        LinkedHashMap lmap = (LinkedHashMap) map.get("blocks");
        for (Object o : lmap.values()) {
            codeBlocks.add((CodeBlock) o);
        }
        ItemStack block = (ItemStack) map.get("block");
        String name = (String) block.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(CodeBlocksPlugin.getInstance(), "name"), PersistentDataType.STRING);
        String eventType = (String) block.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(CodeBlocksPlugin.getInstance(), "event"), PersistentDataType.STRING);
        return new EventFunctionBlock(name, codeBlocks, eventType);
    }

}
