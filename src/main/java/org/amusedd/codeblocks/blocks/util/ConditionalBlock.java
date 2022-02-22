package org.amusedd.codeblocks.blocks.util;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.blocks.functions.FunctionBlock;
import org.amusedd.codeblocks.gui.ConditionalGUI;
import org.amusedd.codeblocks.gui.EditVariablesGUI;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.values.ValueBlockData;
import org.amusedd.codeblocks.values.ValueSet;
import org.amusedd.codeblocks.values.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConditionalBlock extends CodeBlockContainer {
    ValueSet set;

    private FunctionBlock trueFunction;
    private FunctionBlock falseFunction;


    public ConditionalBlock(ValueSet name, FunctionBlock trueFunction, FunctionBlock falseFunction) {
        super(name, new ArrayList<CodeBlock>(List.of(new FunctionBlock[]{trueFunction, falseFunction})));
        this.set = name;
        this.trueFunction = trueFunction;
        this.falseFunction = falseFunction;
        this.trueFunction.setContainer(this);
        this.falseFunction.setContainer(this);
        item = new ItemBuilder(Material.CHAIN).addLore(ChatColor.GREEN + "Right Click to Edit Required Values").build();
    }

    public ConditionalBlock(){
        getValueSet();
        ValueSet sets = new ValueSet();
        sets.addValueBlock("name", new ValueBlock(new ValueBlockData(Material.NAME_TAG, "True", ValueType.STRING, "True Function")));
        trueFunction = new FunctionBlock(sets, new ArrayList<>());
        ValueSet set1 = new ValueSet();
        set1.addValueBlock("name", new ValueBlock(new ValueBlockData(Material.NAME_TAG, "False", ValueType.STRING, "False Function")));
        falseFunction = new FunctionBlock(set1, new ArrayList<>());
        trueFunction.setContainer(this);
        falseFunction.setContainer(this);
    }

    @Override
    public ValueSet getValueSet() {
        if(set == null) {
            set = super.getValueSet();
            ValueSet cond = new ValueSet();
            cond.addValueBlock("a", new ValueBlock(new ValueBlockData(Material.BLUE_WOOL, "Value A", ValueType.INTEGER, null)));
            cond.addValueBlock("b", new ValueBlock(new ValueBlockData(Material.GREEN_WOOL, "Value B", ValueType.INTEGER, null)));
            cond.addValueBlock("conditional_type", new ValueBlock(new ValueBlockData(Material.OAK_FENCE_GATE, "Conditional Type", ValueType.CONDITIONAL, null)));
            set.addValueBlock("conditionalBlock", new ConditionalValueBlock(new ValueBlockData(Material.REDSTONE, "Conditional Block", ValueType.BOOLEAN, null), cond));
        }
        return set;
    }

    @Override
    public HashMap<String, ValueBlock> getVariableScope() {
        return null;
    }

    @Override
    public boolean isRunnable() {
        return super.isRunnable() && trueFunction != null && falseFunction != null;
    }

    @Override
    public void run() {
        ConditionalValueBlock valueBlock = (ConditionalValueBlock) getValueSet().getValueBlock("conditionalBlock");
        if(valueBlock.evaluate()){
            trueFunction.run();
        } else {
            falseFunction.run();
        }
    }

    @Override
    public void nextBlock() {
        getContainer().nextBlock();
    }

    @Override
    public void onGUILeftClick(Player player, GUI gui, InventoryClickEvent event) {
        new ConditionalGUI(player, this).open();
    }

    @Override
    public void onGUIRightClick(Player player, GUI gui, InventoryClickEvent event) {
        new EditVariablesGUI(player, set).open();
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.CHAIN).setName("Conditional Loop").build();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("trueFunction", trueFunction);
        data.put("falseFunction", falseFunction);
        return data;
    }

    public static ConditionalBlock deserialize(Map<String, Object> data) {
        ArrayList<CodeBlock> lmap = (ArrayList<CodeBlock>) data.get("blocks");
        ValueSet name = (ValueSet) data.get("name");
        FunctionBlock trueFunction = (FunctionBlock) data.get("trueFunction");
        FunctionBlock falseFunction = (FunctionBlock) data.get("falseFunction");
        ConditionalBlock fin = new ConditionalBlock(name, trueFunction, falseFunction);
        return fin;
    }



    public ConditionalValueBlock getConditionalBlock() {
        return (ConditionalValueBlock) getValueSet().getValueBlock("conditionalBlock");
    }

    public FunctionBlock getTrueFunction() {
        return trueFunction;
    }

    public FunctionBlock getFalseFunction() {
        return falseFunction;
    }
}
