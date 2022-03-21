package org.amusedd.codeblocks.blocks.executables.containers;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.CodeBlockInfo;
import org.amusedd.codeblocks.blocks.executables.ExecutableCodeBlock;
import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.blocks.value.ValueSetBlock;
import org.amusedd.codeblocks.blocks.value.VariableBlock;
import org.amusedd.codeblocks.util.fake.EventType;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@CodeBlockInfo(viewName = "Event Function", description = "A function that is called when an event is triggered", viewMaterial = Material.BEACON, creatable = false)
public class EventFunctionBlock extends CodeBlockContainer implements ValueHolder {

    CodeBlockContainer container;
    ValueSetBlock valueSetBlock;

    public EventFunctionBlock(ValueSetBlock valueSetBlock, Map<String, Object> map) {
        super(map);
        this.valueSetBlock = valueSetBlock;
    }

    public EventFunctionBlock(){
        HashMap<String, ValueBlock> map = new HashMap<>();
        map.put("name", new ValueBlock("Name of Event Function", Material.NAME_TAG, String.class, null));
        map.put("event_type", new ValueBlock("Activation Event", Material.BEACON, EventType.class, null));
        addVariable("event", new VariableBlock("Event", Event.class, null), true);
        this.valueSetBlock = new ValueSetBlock(map);
    }

    @Override
    public CodeBlockContainer getContainer() {
        return container;
    }

    public String getName(){
        return valueSetBlock.get("name").getValue() == null ? "Undefined Event Function" : valueSetBlock.get("name").getValue().toString();
    }

    public EventType getEventType(){
        return (EventType) valueSetBlock.get("event_type").getValue();
    }

    @Override
    public void setContainer(CodeBlockContainer container) {
        this.container = container;
    }

    @Override
    public ValueSetBlock getValueSet() {
        return valueSetBlock;
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.BEACON).setName(ChatColor.WHITE + getName()).build();
    }

    public static EventFunctionBlock deserialize(Map<String, Object> map){
        return new EventFunctionBlock((ValueSetBlock) map.get("valueset"), map);
    }

    @Override
    public void onCreate(CodeBlockContainer container) {
        CodeBlocks.getPlugin().getEventStorage().addEventFunction(this);
    }
}
