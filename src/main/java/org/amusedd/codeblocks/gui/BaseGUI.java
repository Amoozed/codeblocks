package org.amusedd.codeblocks.gui;

import org.amusedd.codeblocks.gui.FunctionPageGUI;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BaseGUI extends GUI {

    ItemStack functionPage;
    {
        functionPage = new ItemBuilder(Material.CHAIN_COMMAND_BLOCK).setName("§aFunctions").build();
    }
    ItemStack eventPage;
    {
        eventPage = new ItemBuilder(Material.CHEST).setName("§aEvents").build();
    }

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
        if (item.equals(functionPage)) {
            new FunctionPageGUI((Player) event.getWhoClicked()).open();
        }
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = new HashMap<>();
        items.put(0, functionPage);
        items.put(1, eventPage);
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }
}
