package org.amusedd.codeblocks.gui;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.CodeBlockContainer;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ContainerEditGUI extends GUI{

    private CodeBlockContainer container;

    ItemStack addCodeBlock;
    {
        addCodeBlock = new ItemBuilder(Material.EMERALD).setName("Add CodeBlock").addLore("Click to add a codeblock").build();
    }

    public ContainerEditGUI(Player player, CodeBlockContainer container) {
        super(player);
    }

    @Override
    public String getName() {
        return container.getName();
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        if(item.equals(addCodeBlock)){
            new CodeBlocksSelection(getOwner(), this).open();
        }
    }

    @Override
    public void onPlayerGUISelection(ItemStack item, InventoryEvent event) {
        String type = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(CodeBlocksPlugin.getInstance(), "type"), PersistentDataType.STRING);
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        ArrayList<CodeBlock> codeBlocks = container.getCodeBlocks();
        HashMap<Integer, ItemStack> items = new HashMap<>();
        for(int i = 0; i < codeBlocks.size(); i++){
            items.put(i, codeBlocks.get(i).getItem());
        }
        items.put(53, addCodeBlock);
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }
}
