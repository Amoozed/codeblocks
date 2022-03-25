package org.amusedd.codeblocks.menu;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.executables.containers.StandaloneFunctionBlock;
import org.amusedd.codeblocks.blocks.value.VariableBlock;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class DeveloperMenu extends Menu{
    ItemStack reloadPlugin;
    {
        reloadPlugin = new ItemBuilder(Material.ACTIVATOR_RAIL).setName(ChatColor.WHITE + "Reload Plugin").addLore(ChatColor.GRAY + "Reload the Plugin").addLore(ChatColor.GRAY + "Use when compiling new version").addLore(ChatColor.GRAY + "Will call onDisable(), hence the auto-save").build();
    }
    ItemStack purgeAndReload;
    {
        purgeAndReload = new ItemBuilder(Material.TNT).setName(ChatColor.WHITE + "Purge and Reload").addLore(ChatColor.GRAY + "Purge the data folder and queuedSaves and reloads the plugin").addLore(ChatColor.GRAY + "Use if errors occur").addLore(ChatColor.GRAY + "Will call onDisable(), hence the auto-save").build();
    }
    ItemStack createBasicFunction;
    {
        createBasicFunction = new ItemBuilder(Material.COMMAND_BLOCK).setName(ChatColor.WHITE + "Create Basic Function").addLore(ChatColor.GRAY + "Creates a basic function").addLore(ChatColor.GRAY + "Will automatically create a new function with a randomized name").addLore(ChatColor.GRAY + "Contains a print to server block and a variable").build();
    }
    public DeveloperMenu(Player player) {
        super(player);
    }

    @Override
    public String getName() {
        return "Developer Menu";
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        if(item.equals(reloadPlugin)){
            forceClose();
            Bukkit.getServer().dispatchCommand(event.getWhoClicked(), "plugman reload CodeBlocks");
        } else if(item.equals(purgeAndReload)){
            forceClose();
            CodeBlocks.getPlugin().getDataManager().purge();
            Bukkit.getServer().dispatchCommand(event.getWhoClicked(), "plugman reload CodeBlocks");
        } else if(item.equals(createBasicFunction)){
            // Create a randomized name and put it in a string
            String name = "";
            for(int i = 0; i < 5; i++) name += (char)((int)(Math.random() * 26) + 97);
            VariableBlock var = new VariableBlock("Testing Variable", String.class, "test", false);
            HashMap<String, VariableBlock> vars = new HashMap<>();
            vars.put("test", var);
            StandaloneFunctionBlock func = new StandaloneFunctionBlock(name, new ArrayList<>(), vars);
            CodeBlocks.getPlugin().getFunctionStorage().addFunction(func);
            new StandaloneFunctionsMenu((Player) event.getWhoClicked()).open();
        }
        else{
            super.itemClicked(item, event);
        }
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = new HashMap<>();
        items.put(0, reloadPlugin);
        items.put(1, purgeAndReload);
        items.put(2, createBasicFunction);
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }

    @Override
    public int getRows() {
        return 6;
    }
}
