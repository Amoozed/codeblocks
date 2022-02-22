package org.amusedd.codeblocks.gui;

import org.amusedd.codeblocks.blocks.util.ConditionalBlock;
import org.amusedd.codeblocks.blocks.util.loop.ConditionalLoop;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ConditionalGUI extends GUI{

    ConditionalBlock loop;

    ItemStack trueBlock;
    {
        trueBlock = new ItemBuilder(Material.DIAMOND_BLOCK).setName("When True").build();
    }
    ItemStack falseBlock;
    {
        falseBlock = new ItemBuilder(Material.REDSTONE_BLOCK).setName("When False").build();
    }

    public ConditionalGUI(Player player, ConditionalBlock loop) {
        super(player);
        this.loop = loop;
    }

    @Override
    public String getName() {
        return "Conditional";
    }

    @Override
    public int getRows() {
        return 1;
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        if(item.equals(trueBlock)){
            new ContainerEditGUI(getOwner(), loop.getTrueFunction()).open();
        }
        if(item.equals(falseBlock)){
            new ContainerEditGUI(getOwner(), loop.getFalseFunction()).open();
        }
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = new HashMap<>();
        items.put(0, trueBlock);
        items.put(1, falseBlock);
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }

    @Override
    public void onClose() {}
}
