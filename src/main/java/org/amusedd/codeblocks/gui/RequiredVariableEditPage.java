package org.amusedd.codeblocks.gui;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.input.ChatInput;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class RequiredVariableEditPage extends GUI{

    private ArrayList<ValueBlock> valueBlocks;
    public RequiredVariableEditPage(Player player, ArrayList<ValueBlock> valueBlocks) {
        super(player);
        this.valueBlocks = valueBlocks;
    }

    @Override
    public String getName() {
        return null;
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
            String prompt = "Please enter a value of type " + valueBlock.getValueType().name();
            new ChatInput(prompt, getOwner(), event, this).awaitResponse();
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
            }
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
