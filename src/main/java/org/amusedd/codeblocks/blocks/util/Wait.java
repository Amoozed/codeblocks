package org.amusedd.codeblocks.blocks.util;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.gui.EditVariablesGUI;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.input.ValueBlockData;
import org.amusedd.codeblocks.input.ValueSet;
import org.amusedd.codeblocks.input.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Wait extends CodeBlock {

    ValueSet set;

    public Wait() {
        item = new ItemBuilder(item).addLore(ChatColor.WHITE + "Seconds: " + ChatColor.GREEN + "Undefined").build();
    }

    public Wait(ValueSet set) {
        this.set = set;
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.CLOCK).setName("Wait").build();
    }


    @Override
    public void onGUIRightClick(Player player, GUI gui, InventoryClickEvent event) {
        new EditVariablesGUI(player, getValueSet()).open();
    }

    @Override
    public void onGUILeftClick(Player player, GUI gui, InventoryClickEvent event) {
        getValueSet().getValueBlock("seconds").onGUIRightClick(player, gui, event);
    }

    @Override
    public ValueSet getValueSet() {
        if(set == null) {
            set = super.getValueSet();
            set.addValueBlock("seconds", new ValueBlock(new ValueBlockData(Material.CLOCK, "Seconds", ValueType.INTEGER, null)));
        }
        return set;
    }

    @Override
    public void run() {
        Bukkit.getScheduler().runTaskLater(CodeBlocksPlugin.getInstance(), new Runnable() {
            @Override
            public void run() {
                Wait.super.run();
            }
        }, (int) getValueSet().getValueBlock("seconds").getData().getValue());
    }

    public static Wait deserialize(Map<String, Object> map) {
        return new Wait((ValueSet) map.get("valueset"));
    }
}
