package org.amusedd.codeblocks.menu;

import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class DeveloperMenu extends Menu{
    ItemStack reloadPlugin;
    {
        reloadPlugin = new ItemBuilder(Material.ACTIVATOR_RAIL).setName(ChatColor.WHITE + "Reload Plugin").addLore(ChatColor.GRAY + "Reload the Plugin").addLore(ChatColor.GRAY + "Use when compiling new version").addLore(ChatColor.GRAY + "Will call onDisable(), hence the auto-save").build();
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
        } else{
            super.itemClicked(item, event);
        }
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = new HashMap<>();
        items.put(0, reloadPlugin);
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
