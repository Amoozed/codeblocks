package org.amusedd.codeblocks.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class GUI implements InventoryHolder {
    protected Inventory inventory;
    private final Player owner;

    public GUI(Player player) {
        this.owner = player;
    }

    public abstract String getName();

    public abstract int getRows();

    public abstract void itemClicked(ItemStack item, InventoryClickEvent event);

    public abstract HashMap<Integer, ItemStack> getItems();

    public abstract ItemStack blankSpot();

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void fillInventory() {
        HashMap<Integer, ItemStack> items = getItems();
        if (blankSpot() != null) {
            for (int i = 0; i < getRows() * 9; i++) {
                if (items.containsKey(i)) {
                    inventory.setItem(i, items.get(i));
                } else {
                    inventory.setItem(i, blankSpot());
                }
            }
        }
        for (int index : items.keySet()) {
            inventory.setItem(index, items.get(index));
        }
    }

    public void open() {
        inventory = Bukkit.createInventory(this, getRows() * 9, getName());
        fillInventory();
        owner.getPlayer().openInventory(inventory);
    }

    public Player getOwner() {
        return owner;
    }

    public void onPlayerSignResponse(ItemStack item, InventoryEvent event, ArrayList<String> responses) {
        System.out.println("No response needed.");
    }

    public void onPlayerConfirmation(ItemStack item, InventoryEvent event, boolean confirmed) {
        System.out.println("No confirmation needed.");
    }

}
