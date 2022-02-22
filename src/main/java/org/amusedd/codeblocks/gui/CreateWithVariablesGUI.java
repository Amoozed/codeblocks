package org.amusedd.codeblocks.gui;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.values.ValueSet;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CreateWithVariablesGUI extends EditVariablesGUI{

    ItemStack initialize;
    {
        initialize = new ItemBuilder(Material.COBBLESTONE).setName(ChatColor.GREEN + "" + ChatColor.BOLD + "Create").build();
    }

    CodeBlock codeBlock;
    ValueSet set;

    public CreateWithVariablesGUI(Player player, ValueSet valueSet, CodeBlock codeBlock) {
        super(player, valueSet);
        this.codeBlock = codeBlock;
        this.set = valueSet;
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = super.getItems();
        items.put(getRows() * 9 - 1, initialize);
        return items;
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        if(item.equals(initialize)){
            if(!set.isComplete()){
                getOwner().sendMessage(ChatColor.RED + "You must set all variables before creating the block!");
                return;
            }

            codeBlock.onVariableCreation(getOwner(), this, event);
            getOwner().closeInventory();
        } else {
            super.itemClicked(item, event);
        }
    }
}
