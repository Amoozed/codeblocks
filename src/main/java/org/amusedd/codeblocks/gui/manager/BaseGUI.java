package org.amusedd.codeblocks.gui.manager;

import org.amusedd.codeblocks.gui.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BaseGUI extends GUI {
    public BaseGUI(Player player) {
        super(player);
    }

    @Override
    public String getName() {
        return "CodeBlocks Menu";
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {

    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        return null;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }
}
