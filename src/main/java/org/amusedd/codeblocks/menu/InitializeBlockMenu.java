package org.amusedd.codeblocks.menu;

import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.value.ValueSetBlock;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class InitializeBlockMenu extends ViewValueMenu {

    ValueHolder creationCallback;
    CodeBlockContainer container;
    ItemStack createBlock;
    {
        createBlock = new ItemBuilder(Material.STONE).setName(ChatColor.WHITE + "Create Block").addLore(ChatColor.GRAY + "Click to add this CodeBlock to the current Container.").addLore(ChatColor.RED + "All required variables must be filled in.").build();
    }

    public InitializeBlockMenu(Player player, ValueSetBlock valueSetBlock, CodeBlockContainer container, ValueHolder creationCallback) {
        super(player, valueSetBlock);
        this.creationCallback = creationCallback;
        this.container = container;
    }

    @Override
    public String getName() {
        return "Initialize Block";
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        if(item.equals(createBlock)){
            if(getValueSetBlock().canRun()) {
                creationCallback.onCreate(container);
                getParent().open();
            } else {
                getOwner().sendMessage(ChatColor.RED + "This block cannot be created yet. Please set all required variables.");
            }
        } else {
            super.itemClicked(item, event);
        }
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = super.getItems();
        items.put(items.size(), createBlock);
        return items;
    }
}
