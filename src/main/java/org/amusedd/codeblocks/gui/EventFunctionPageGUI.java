package org.amusedd.codeblocks.gui;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.amusedd.codeblocks.blocks.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.blocks.functions.EventFunctionBlock;
import org.amusedd.codeblocks.blocks.functions.FunctionBlock;
import org.amusedd.codeblocks.input.ChatInput;
import org.amusedd.codeblocks.input.ValueBlockData;
import org.amusedd.codeblocks.input.ValueSet;
import org.amusedd.codeblocks.input.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class EventFunctionPageGUI extends GUI{

    ItemStack createFunction;
    {
        createFunction = new ItemBuilder(Material.EMERALD).setName("Create Event Function").build();
    }

    public EventFunctionPageGUI(Player player) {
        super(player);
    }

    @Override
    public String getName() {
        return "Event Functions";
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        if(item.equals(createFunction)) {
            EventFunctionBlock function = new EventFunctionBlock();
            new CreateWithVariablesGUI(getOwner(), function.getValueSet(), function).open();
        } else {
            EventFunctionBlock container = CodeBlocksPlugin.getInstance().getBlockStorage().getEventBlock(item.getItemMeta().getDisplayName());
            if(event.isLeftClick()){
                container.onGUILeftClick(getOwner(), this, event);
            } else if(event.isRightClick()){
                container.onGUIRightClick(getOwner(), this, event);
            }
        }
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        ArrayList<EventFunctionBlock> functions = CodeBlocksPlugin.getInstance().getBlockStorage().getEventBlocks();
        HashMap<Integer, ItemStack> items = new HashMap<>();
        items.put(53, createFunction);
        for(int i = 0; i < functions.size(); i++) {
            items.put(i, functions.get(i).getRefreshedItem());
        }
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }

    @Override
    public void onClose() {}
}
