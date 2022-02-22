package org.amusedd.codeblocks.gui;

import org.amusedd.codeblocks.blocks.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.functions.FunctionBlock;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

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
            FunctionBlock block = new FunctionBlock();
            new CreateWithVariablesGUI(getOwner(), block.getValueSet(), block).open();
        } else {
            CodeBlockContainer container = CodeBlocksPlugin.getInstance().getBlockStorage().getContainers().get(event.getSlot());
            if(event.isLeftClick()){
                container.onGUILeftClick(getOwner(), this, event);
            } else if(event.isRightClick()){
                container.onGUIRightClick(getOwner(), this, event);
            } else if(event.getClick().equals(ClickType.MIDDLE)){
                CodeBlocksPlugin.getInstance().getBlockStorage().removeContainer(container.getName());
                open();
            }
        }
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        ArrayList<CodeBlockContainer> functions = CodeBlocksPlugin.getInstance().getBlockStorage().getContainers();
        HashMap<Integer, ItemStack> items = new HashMap<>();
        items.put(53, createFunction);
        for(int i = 0; i < functions.size(); i++) {
            items.put(i, functions.get(i).getRefreshedItem());
        }
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }

    /*
    @Override
    public void onPlayerTextResponse(ItemStack item, InventoryClickEvent event, String response) {
        if(item.equals(createFunction)) {
            Bukkit.broadcastMessage("Name: " + response);
            ValueSet set = new ValueSet();
            set.addValueBlock("name", new ValueBlock(new ValueBlockData(Material.NAME_TAG, "Name", ValueType.STRING, response)));
            FunctionBlock function = new FunctionBlock(set, new ArrayList<>());
            ItemMeta meta = function.getItem().getItemMeta();
            meta.setDisplayName(response);
            function.getItem().setItemMeta(meta);
            CodeBlocksPlugin.getInstance().getBlockStorage().addFunctionBlock(function);
            open();
        }
    }
    */

    @Override
    public void onClose() {}
}
