package org.amusedd.codeblocks.menu;

import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class EditValueMenu extends Menu {
    static ItemStack keyboardEntry;
    static ItemStack variableEntry;
    static ItemStack functionEntry;

    static {
        keyboardEntry = new ItemBuilder()
                .setMaterial(Material.PAPER)
                .setName(ChatColor.WHITE + "Keyboard Entry")
                .addLore(ChatColor.GRAY + "Click to enter a value through the chat")
                .build();
        variableEntry = new ItemBuilder()
                .setMaterial(Material.DIAMOND)
                .setName(ChatColor.WHITE + "Variable Entry")
                .addLore(ChatColor.GRAY + "Click to tie this value to a variable")
                .build();
        functionEntry = new ItemBuilder()
                .setMaterial(Material.COMMAND_BLOCK)
                .setName(ChatColor.WHITE + "Function Entry")
                .addLore(ChatColor.GRAY + "Click to get this value from a evaluated function")
                .build();
    }

    public EditValueMenu(Player player) {
        super(player);
    }


    @Override
    public String getName() {
        return "Set Value";
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = new HashMap<>();
        items.put(0, keyboardEntry);
        items.put(1, variableEntry);
        items.put(2, functionEntry);
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }

    @Override
    public int getRows() {
        return 1;
    }
}
