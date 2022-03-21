package org.amusedd.codeblocks.menu;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.executables.containers.StandaloneFunctionBlock;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class StandaloneFunctionsMenu extends Menu{
    ItemStack createStandaloneFunction;
    {
        createStandaloneFunction = new ItemBuilder(Material.EMERALD).setEnchanted(true).setName(ChatColor.GREEN + "Create Standalone Function").build();
    }
    ArrayList<StandaloneFunctionBlock> functions;

    public StandaloneFunctionsMenu(Player player) {
        super(player);
    }


    @Override
    public String getName() {
        return "Standalone Functions";
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        if(item.equals(createStandaloneFunction)) {
            StandaloneFunctionBlock function = new StandaloneFunctionBlock();
            function.create(getOwner(), null);
        }
        else {
            StandaloneFunctionBlock function = functions.get(event.getSlot());
            if(event.isLeftClick()) function.onGUIItemLeftClick(event);
            else if(event.isRightClick()) function.onGUIItemRightClick(event);
        }
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = new HashMap<>();
        functions = CodeBlocks.getPlugin().getFunctionStorage().getFunctions();
        for(int i = 0; i < functions.size(); i++) {
            items.put(i, functions.get(i).getGUIItem());
        }
        items.put(53, createStandaloneFunction);
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }
}
