package org.amusedd.codeblocks.menu;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.executables.containers.StandaloneFunctionBlock;
import org.amusedd.codeblocks.blocks.value.VariableBlock;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class VariableMenu extends Menu{
    ItemStack createVariable;
    {
        createVariable = new ItemBuilder(Material.EMERALD).setEnchanted(true).setName(ChatColor.GREEN + "Add Preset Variable").build();
    }
    CodeBlockContainer container;
    ArrayList<VariableBlock> variables;

    public VariableMenu(Player player, CodeBlockContainer container) {
        super(player);
        this.container = container;
    }


    @Override
    public String getName() {
        return "Variables";
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        if(item.equals(createVariable)) {
            VariableBlock variableBlock = new VariableBlock();
            variableBlock.create(getOwner(), container);
        }
        else {
            VariableBlock variableBlock = variables.get(event.getSlot());
            if(event.isLeftClick()) variableBlock.onGUIItemLeftClick(event);
            else if(event.isRightClick()) variableBlock.onGUIItemRightClick(event);
        }
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = new HashMap<>();
        variables = container.getAllVariables();
        System.out.println("Variables: " + variables);
        for(int i = 0; i < variables.size(); i++) {
            items.put(i, variables.get(i).getGUIItem());
        }
        items.put(53, createVariable);
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }
}
