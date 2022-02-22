package org.amusedd.codeblocks.blocks.functions;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.gui.CreateWithVariablesGUI;
import org.amusedd.codeblocks.gui.EditVariablesGUI;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.values.ValueBlockData;
import org.amusedd.codeblocks.values.ValueSet;
import org.amusedd.codeblocks.values.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Map;

public class EventFunctionBlock extends FunctionBlock {
    Event event;
    ValueSet set;

    public EventFunctionBlock(ValueSet name, ArrayList<CodeBlock> codeBlocks) {
        super(name, codeBlocks);
        this.set = name;
    }

    public EventFunctionBlock(){

    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.COMMAND_BLOCK_MINECART).setName(getName()).addLore(ChatColor.GRAY + "Called on: " + ChatColor.WHITE + getEventType()).build();
    }

    @Override
    public void run() {
        System.out.println("Cannot execute event function block directly!");
    }

    public void onEvent(Event event) {
        this.event = event;
        super.run();
    }


    @Override
    public ValueSet getValueSet() {
        if(set == null) {
            set = super.getValueSet();
            set.addValueBlock("event_type", new ValueBlock(new ValueBlockData(Material.MINECART, "Event Type", ValueType.EVENT_TYPE, null)));
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
    public void onGUIRightClick(Player player, GUI gui, InventoryClickEvent event) {
        new EditVariablesGUI(player, set).open();
    }

    public String getEventType(){
       Object eventType = getValueSet().getValueBlock("event_type").getData().getValue();
       System.out.println(getValueSet().getValueBlock("event_type").getData().getValue());
       return (eventType == null) ? "Undefined" : "" + eventType;
    }

    public static EventFunctionBlock deserialize(Map<String, Object> map) {
        return new EventFunctionBlock((ValueSet) map.get("valueset"), (ArrayList<CodeBlock>) map.get("blocks"));
    }

    @Override
    public void onVariableCreation(Player player, CreateWithVariablesGUI gui, InventoryClickEvent event) {
        ItemMeta meta = getItem().getItemMeta();
        meta.setDisplayName((String) getValueSet().getValueBlock("name").getData().getValue());
        getItem().setItemMeta(meta);
        CodeBlocksPlugin.getInstance().getBlockStorage().addEventBlock(this);
    }
}
