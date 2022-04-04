package org.amusedd.codeblocks.blocks.executables.containers;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.CodeBlockInfo;
import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.blocks.value.ValueSet;
import org.amusedd.codeblocks.blocks.value.VariableBlock;
import org.amusedd.codeblocks.util.fake.EventType;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@CodeBlockInfo(viewName = "Event Function", description = "A function that is called when an event is triggered", viewMaterial = Material.BEACON, creatable = false)
public class EventFunctionBlock extends CodeBlockContainer implements ValueHolder {

    CodeBlockContainer container;
    ValueSet valueSet;

    public EventFunctionBlock(ValueSet valueSet, Map<String, Object> map) {
        super(map);
        this.valueSet = valueSet;
    }

    public EventFunctionBlock(){
        HashMap<String, ValueBlock> map = new HashMap<>();
        map.put("name", new ValueBlock("Name of Event Function", Material.NAME_TAG, String.class, null));
        map.put("event_type", new ValueBlock("Activation Event", Material.BEACON, EventType.class, null));
        addVariable("event", new VariableBlock("Event", Event.class, null, false), true);
        this.valueSet = new ValueSet(map);
    }

    @Override
    public CodeBlockContainer getContainer() {
        return container;
    }

    public String getName(){
        return valueSet.get("name").getValue() == null ? "Undefined Event Function" : valueSet.get("name").getValue().toString();
    }

    public EventType getEventType(){
        return (EventType) valueSet.get("event_type").getValue();
    }

    @Override
    public void setContainer(CodeBlockContainer container) {
        this.container = container;
    }

    @Override
    public ValueSet getValueSet() {
        return valueSet;
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.BEACON).setName(ChatColor.WHITE + getName()).build();
    }

    public static EventFunctionBlock deserialize(Map<String, Object> map){
        return new EventFunctionBlock((ValueSet) map.get("valueset"), map);
    }

    @Override
    public void onCreation(CodeBlockContainer container) {
        CodeBlocks.getPlugin().getEventStorage().addEventFunction(this);
    }
}
