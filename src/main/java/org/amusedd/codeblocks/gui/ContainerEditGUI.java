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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class ContainerEditGUI extends GUI {

    ItemStack addCodeBlock;
    private CodeBlockContainer container;

    {
        addCodeBlock = new ItemBuilder(Material.EMERALD).setName("Add CodeBlock").addLore("Click to add a codeblock").build();
    }

    public ContainerEditGUI(Player player, CodeBlockContainer container) {
        super(player);
        this.container = container;
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
        if (item.equals(addCodeBlock)) {
            new CodeBlocksSelection(getOwner(), this).open();
        } else {
            CodeBlock codeBlock = container.getCodeBlock(event.getSlot());
            if (event.getClick().isRightClick()) {
                codeBlock.onGUIRightClick(getOwner(), this);
            } else if (event.getClick().isLeftClick()) {
                codeBlock.onGUILeftClick(getOwner(), this);
            }
        }
    }

    @Override
    public void onPlayerGUISelection(ItemStack item, InventoryClickEvent event) {
        System.out.println("Clicked on " + item.getItemMeta().getDisplayName());
        if (item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(CodeBlocksPlugin.getInstance(), "createtype"), PersistentDataType.STRING)) {
            System.out.println("found tag");
            String type = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(CodeBlocksPlugin.getInstance(), "createtype"), PersistentDataType.STRING);
            try {
                Class<? extends CodeBlock> clazz = (Class<? extends CodeBlock>) Class.forName(type);
                CodeBlock codeBlock = clazz.getConstructor().newInstance();
                container.addCodeBlock(codeBlock);
                new EditVariablesGUI(getOwner(), codeBlock.getValueSet()).open();
            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPlayerTextResponse(ItemStack item, InventoryClickEvent event, String response) {
        super.onPlayerTextResponse(item, event, response);
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        ArrayList<CodeBlock> codeBlocks = container.getCodeBlocks();
        HashMap<Integer, ItemStack> items = new HashMap<>();
        if (codeBlocks != null) {
            for (int i = 0; i < codeBlocks.size(); i++) {
                items.put(i, codeBlocks.get(i).getItem());
            }
        }
        items.put(53, addCodeBlock);
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }
}
