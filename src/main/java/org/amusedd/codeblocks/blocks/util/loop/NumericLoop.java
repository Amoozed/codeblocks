package org.amusedd.codeblocks.blocks.util.loop;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.gui.ContainerEditGUI;
import org.amusedd.codeblocks.gui.EditVariablesGUI;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.input.ValueSet;
import org.amusedd.codeblocks.input.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;

public class NumericLoop extends CodeBlockContainer {
    HashMap<String, ValueBlock> variableScope = new HashMap<>();
    ValueSet set;
    int iterations;

    public NumericLoop(ValueBlock name, ArrayList<CodeBlock> codeBlocks, ValueBlock amount) {
        super(name, codeBlocks);
        if(amount.getValue() != null) getValueSet().getValueBlock("amount").setValue(amount.getValue());
    }

    public NumericLoop(){}

    @Override
    public HashMap<String, ValueBlock> getVariableScope() {
        return variableScope;
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.CHAIN_COMMAND_BLOCK).setName("Numeric Loop").build();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("amount", getValueSet().getValueBlock("amount"));
        return data;
    }


    public static NumericLoop deserialize(Map<String, Object> data) {
        ArrayList<CodeBlock> lmap = (ArrayList<CodeBlock>) data.get("blocks");
        ItemStack item = (ItemStack) data.get("block");
        ValueBlock name = (ValueBlock) data.get("name");
        ValueBlock amount = (ValueBlock) data.get("amount");
        NumericLoop fin = new NumericLoop(name, lmap, amount);
        return fin;
    }

    @Override
    public void onGUIRightClick(Player player, GUI gui) {
        new EditVariablesGUI(player, set).open();
    }

    @Override
    public void nextBlock() {
        if(isFinished()) {
            iterations++;
            if(iterations < (int)getValueSet().getValueBlock("amount").getValue()) {
                super.run();
            } else{
                getContainer().nextBlock();
            }
        } else {
            super.nextBlock();
        }
    }

    @Override
    public ValueSet getValueSet() {
        if(set == null) {
            set = super.getValueSet();
            set.addValueBlock("amount", new ValueBlock(ValueType.INTEGER));
        }
        return set;
    }
}
