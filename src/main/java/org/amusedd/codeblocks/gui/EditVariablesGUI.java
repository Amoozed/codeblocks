package org.amusedd.codeblocks.gui;

import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.input.ChatInput;
import org.amusedd.codeblocks.input.ValueSet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class EditVariablesGUI extends GUI{

    private ArrayList<ValueBlock> valueBlocks;

    public EditVariablesGUI(Player player, ValueSet valueSet) {
        super(player);
        this.valueBlocks = valueSet.getValueBlocks();
    }

    public EditVariablesGUI(Player player, ArrayList<ValueBlock> valueBlocks) {
        super(player);
        this.valueBlocks = valueBlocks;
    }

    @Override
    public String getName() {
        return "Edit Variables";
    }

    @Override
    public int getRows() {
        return 1;
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        int slot = event.getSlot();
        if(slot < valueBlocks.size()){
            ValueBlock valueBlock = valueBlocks.get(slot);
            if(event.isRightClick()){
                valueBlock.onGUIRightClick(getOwner(), this);
            } else if(event.isLeftClick()){
                valueBlock.onGUILeftClick(getOwner(), this);
            }
            getOwner().closeInventory();
        }
    }

    @Override
    public void onPlayerTextResponse(ItemStack item, InventoryClickEvent event, String response) {
        int slot = event.getSlot();
        if(slot < valueBlocks.size()){
            ValueBlock valueBlock = valueBlocks.get(slot);
            if(valueBlock.getValueType().isOfType(response)){
                valueBlock.setValue(response);
                open();
            } else {
                getOwner().sendMessage("Invalid value");
                open();
            }
        }
    }

    @Override
    public void onPlayerGUISelection(ItemStack item, InventoryClickEvent event) {
        int slot = event.getSlot();
        if(slot < valueBlocks.size()){
            ValueBlock valueBlock = valueBlocks.get(slot);
            Bukkit.broadcastMessage(item.getItemMeta().getDisplayName());
            valueBlock.setValue(valueBlock.getValueType().getTypedValue(item.getItemMeta().getDisplayName()));
        }
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = new HashMap<>();
        for(int i = 0; i < valueBlocks.size(); i++){
            items.put(i, valueBlocks.get(i).getItem());
        }
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }
}
