package org.amusedd.codeblocks.gui;

import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;


import java.util.HashMap;

public abstract class PaginatedGUI extends GUI {
    int pages;
    int currentPage;
    protected ItemStack nextPage;
    {
        new ItemBuilder(ChatColor.GREEN + "" + ChatColor.BOLD + "Next Page", Material.ARROW).build();
    }
    protected ItemStack lastPage;
    {
        new ItemBuilder(ChatColor.RED + "" + ChatColor.BOLD + "Last Page", Material.ARROW).build();
    }

    public PaginatedGUI(Player player) {
        super(player);
        nextPage = new ItemStack(Material.ARROW);
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = getPage(currentPage);
        items.put(45, nextPage);
        items.put(53, lastPage);
        for(int i = 46; i < 53; i++){
            items.put(i, new ItemBuilder("", Material.GRAY_STAINED_GLASS_PANE).build());
        }
        return items;
    }

    public abstract HashMap<Integer, ItemStack> getPage(int page);

    public abstract int getPages();

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        boolean cancelled = true;
        if(item.equals(nextPage)) {
            currentPage = (currentPage + 1 > getPages()) ? 1 : currentPage + 1;
        } else if(item.equals(lastPage)) {
            currentPage = (currentPage - 1 < 1) ? getPages() : currentPage - 1;
        } else{
            cancelled = false;
        }
        event.setCancelled(cancelled);
        if(cancelled) {
            open();
        }
    }
}
