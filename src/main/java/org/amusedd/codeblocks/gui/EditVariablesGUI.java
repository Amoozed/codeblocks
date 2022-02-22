package org.amusedd.codeblocks.gui;

import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.values.ValueSet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class EditVariablesGUI extends GUI {

    boolean responseMode = false;
    private final ArrayList<ValueBlock> valueBlocks;

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
        if (slot < valueBlocks.size()) {
            responseMode = true;
            ValueBlock valueBlock = valueBlocks.get(slot);
            if (event.isRightClick()) {
                valueBlock.onGUIRightClick(getOwner(), this, event);
            } else if (event.isLeftClick()) {
                valueBlock.onGUILeftClick(getOwner(), this, event);
            }
            getOwner().closeInventory();
        }
    }

    @Override
    public void onPlayerTextResponse(ItemStack item, InventoryClickEvent event, String response) {
        int slot = event.getSlot();
        if (slot < valueBlocks.size()) {
            ValueBlock valueBlock = valueBlocks.get(slot);
            if (valueBlock.getData().getType().isOfType(response)) {
                valueBlock.getData().setValue(response);
                open();
            } else {
                getOwner().sendMessage("Invalid value");
                open();
            }
        }
    }

    @Override
    public void onPlayerGUISelection(ItemStack item, InventoryClickEvent event, int from) {
        ValueBlock valueBlock = valueBlocks.get(from);
        Bukkit.broadcastMessage(item.getItemMeta().getDisplayName());
        valueBlock.getData().setValue(valueBlock.getData().getType().getTypedValue(item.getItemMeta().getDisplayName()));
    }

    @Override
    public void onClose() {
        for (ValueBlock valueBlock : valueBlocks) {
            if (!responseMode && valueBlock.getData().isRequired() && valueBlock.getData().getValue() == null) {
                getOwner().sendMessage("You must set a value for " + valueBlock.getData().getName());
                open();
                return;
            } else {
                Bukkit.broadcastMessage("legal closing");
            }
        }
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        responseMode = false;
        HashMap<Integer, ItemStack> items = new HashMap<>();
        for (int i = 0; i < valueBlocks.size(); i++) {
            items.put(i, valueBlocks.get(i).getItem());
        }
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }
}
