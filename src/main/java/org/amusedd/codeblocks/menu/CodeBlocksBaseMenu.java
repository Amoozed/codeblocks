package org.amusedd.codeblocks.menu;

import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CodeBlocksBaseMenu extends Menu{
    ItemStack standaloneFunctions;
    {
        standaloneFunctions = new ItemBuilder(Material.COMMAND_BLOCK).setName(ChatColor.WHITE + "Standalone Functions").addLore(ChatColor.GRAY + "Click to open the standalone functions page.").build();
    }
    ItemStack eventFunctions;
    {
        eventFunctions = new ItemBuilder(Material.BELL).setName(ChatColor.WHITE + "Event Functions").addLore(ChatColor.GRAY + "Click to open the event functions page.").build();
    }
    ItemStack developerMenu;
    {
        developerMenu = new ItemBuilder(Material.BOOK).setName(ChatColor.RED + "Developer Menu").addLore(ChatColor.GRAY + "Click to open the developer menu.").build();
    }
    public CodeBlocksBaseMenu(Player player) {
        super(player);
    }

    @Override
    public String getName() {
        return "CodeBlocks";
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        if(item.equals(standaloneFunctions)){
            new StandaloneFunctionsMenu(getOwner()).open();
        }  else if(item.equals(eventFunctions)){
            new EventFunctionsMenu(getOwner()).open();
        } else if(item.equals(developerMenu)){
            new DeveloperMenu(getOwner()).open();
        }
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = new HashMap<>();
        items.put(0, standaloneFunctions);
        items.put(1, eventFunctions);
        items.put(2, developerMenu);
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }
}
