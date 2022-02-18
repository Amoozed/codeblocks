package org.amusedd.codeblocks.blocks.util;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.gui.EditVariablesGUI;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.input.ValueSet;
import org.amusedd.codeblocks.input.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Wait extends CodeBlock {

    ValueSet set;

    public Wait() {
        item = new ItemBuilder(item).addLore(ChatColor.WHITE + "Seconds: " + ChatColor.GREEN + "Undefined").build();
    }

    public Wait(ValueBlock seconds) {
        if(seconds != null && seconds.getValue() != null) getValueSet().getValueBlock("seconds").setValue(seconds.getValue());
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.CLOCK).setName("Wait").build();
    }


    @Override
    public void onGUIRightClick(Player player, GUI gui) {
        new EditVariablesGUI(player, getValueSet()).open();
    }

    @Override
    public void onGUILeftClick(Player player, GUI gui) {
        getValueSet().getValueBlock("seconds").onGUIRightClick(player, gui);
    }

    @Override
    public ValueSet getValueSet() {
        if(set == null) {
            set = super.getValueSet();
            set.addValueBlock("seconds", new ValueBlock(ValueType.INTEGER));
        }
        return set;
    }

    @Override
    public boolean run() {
        Bukkit.getScheduler().runTaskLater(CodeBlocksPlugin.getInstance(), new Runnable() {
            @Override
            public void run() {
                setFinishedExecution(true);
            }
        }, (int) getValueSet().getValueBlock("seconds").getValue());
        return false;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("seconds", getValueSet().getValueBlock("seconds"));
        return map;
    }



    public static Wait deserialize(Map<String, Object> map) {
        return new Wait((ValueBlock) map.get("seconds"));
    }
}
