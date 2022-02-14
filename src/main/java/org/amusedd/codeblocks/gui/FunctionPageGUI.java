package org.amusedd.codeblocks.gui;

import org.amusedd.codeblocks.blocks.functions.FunctionBlock;
import org.amusedd.codeblocks.input.ChatInput;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionPageGUI extends GUI{

    ItemStack createFunction;
    {
        createFunction = new ItemBuilder(Material.EMERALD).setName("Create Function").build();
    }

    public FunctionPageGUI(Player player) {
        super(player);
    }

    @Override
    public String getName() {
        return "Functions";
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        if(item.equals(createFunction)) {
            new ChatInput("Name of function:", (Player) event.getWhoClicked(), event, this).awaitResponse();
            event.getWhoClicked().closeInventory();
        }
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        ArrayList<FunctionBlock> functions = CodeBlocksPlugin.getInstance().getBlockStorage().getFunctions();
        HashMap<Integer, ItemStack> items = new HashMap<>();
        items.put(53, createFunction);
        for(int i = 0; i < functions.size(); i++) {
            items.put(i, functions.get(i).getItem());
        }
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }

    @Override
    public void onPlayerTextResponse(ItemStack item, InventoryEvent event, String response) {
        if(item.equals(createFunction)) {
            FunctionBlock function = new FunctionBlock(response, new ArrayList<>());
            ItemMeta meta = function.getItem().getItemMeta();
            meta.setDisplayName(response);
            function.getItem().setItemMeta(meta);
            CodeBlocksPlugin.getInstance().getBlockStorage().addFunctionBlock(function);
            open();
        }
    }
}
