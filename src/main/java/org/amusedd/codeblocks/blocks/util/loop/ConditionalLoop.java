package org.amusedd.codeblocks.blocks.util.loop;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.blocks.functions.FunctionBlock;
import org.amusedd.codeblocks.blocks.util.ConditionalValueBlock;
import org.amusedd.codeblocks.gui.ConditionalGUI;
import org.amusedd.codeblocks.gui.EditVariablesGUI;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.input.ValueBlockData;
import org.amusedd.codeblocks.input.ValueSet;
import org.amusedd.codeblocks.input.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConditionalLoop extends CodeBlockContainer {
    ValueSet set;


    public ConditionalLoop(ValueSet name, ArrayList<CodeBlock> codeBlocks) {
        super(name, codeBlocks);
        this.set = name;
        item = new ItemBuilder(Material.CHAIN).addLore(ChatColor.GREEN + "Right Click to Edit Required Values").build();
    }

    public ConditionalLoop(){
        getValueSet();
    }

    @Override
    public ValueSet getValueSet() {
        if(set == null) {
            set = super.getValueSet();
            set.addValueBlock("conditionalBlock", new ValueBlock(new ValueBlockData(Material.RED_WOOL, "Conditional Block", ValueType.BOOLEAN, getContainer().getName() + ":" + getContainer().indexOf(this) +  ":conditionalBlock")));
        }
        return set;
    }

    @Override
    public HashMap<String, ValueBlock> getVariableScope() {
        return null;
    }


    @Override
    public void onContainerFinish() {
        ConditionalValueBlock cvb = (ConditionalValueBlock) getValueSet().getValueBlock("conditionalBlock");
        if(cvb.evaluate()) {
            super.run();
        } else{
            getContainer().nextBlock();
        }
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.CHAIN).setName("Conditional Loop").build();
    }

    public static ConditionalLoop deserialize(Map<String, Object> data) {
        ArrayList<CodeBlock> lmap = (ArrayList<CodeBlock>) data.get("blocks");
        ItemStack item = (ItemStack) data.get("block");
        ValueSet name = (ValueSet) data.get("name");
        ConditionalLoop fin = new ConditionalLoop(name, lmap);
        return fin;
    }


    @Override
    public void onGUIRightClick(Player player, GUI gui, InventoryClickEvent event) {
        new EditVariablesGUI(player, set).open();
    }

    public ConditionalValueBlock getConditionalBlock() {
        return (ConditionalValueBlock) getValueSet().getValueBlock("conditionalBlock");
    }
}
