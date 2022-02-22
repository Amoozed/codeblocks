package org.amusedd.codeblocks.blocks.util.loop;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.gui.ContainerEditGUI;
import org.amusedd.codeblocks.gui.EditVariablesGUI;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.input.ValueBlockData;
import org.amusedd.codeblocks.input.ValueSet;
import org.amusedd.codeblocks.input.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;

public class NumericLoop extends CodeBlockContainer {
    HashMap<String, ValueBlock> variableScope = new HashMap<>();
    ValueSet set;
    int iterations = 1;

    public NumericLoop(ValueSet name, ArrayList<CodeBlock> codeBlocks) {
        super(name, codeBlocks);
        this.set = name;
        //if(getValueSet().getValueBlock("amount") != null && getValueSet().getValueBlock("amount").getData().getValue() != null) getValueSet().getValueBlock("amount").getData().setValue(amount.getData().getValue());
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

    public static NumericLoop deserialize(Map<String, Object> data) {
        ArrayList<CodeBlock> lmap = (ArrayList<CodeBlock>) data.get("blocks");
        //ItemStack item = (ItemStack) data.get("block");
        //ValueBlock name = (ValueBlock) data.get("name");
        //ValueBlock amount = (ValueBlock) data.get("amount");
        return new NumericLoop((ValueSet) data.get("valueset"), lmap);
    }

    @Override
    public void onGUIRightClick(Player player, GUI gui, InventoryClickEvent event) {
        new EditVariablesGUI(player, set).open();
    }

    @Override
    public void run() {
        iterations = 1;
        super.run();
    }

    @Override
    public void onContainerFinish() {
        System.out.println("finished :(");
        System.out.println("Iteration: " + iterations + " of " + getValueSet().getValueBlock("amount").getData().getValue());
        if(iterations < (int)getValueSet().getValueBlock("amount").getData().getValue()) {
            iterations++;
            super.run();
        } else{
            getContainer().nextBlock();
        }
    }

    @Override
    public ValueSet getValueSet() {
        if(set == null) {
            set = super.getValueSet();
            System.out.println("tis season of null");
            set.addValueBlock("amount", new ValueBlock(new ValueBlockData(Material.GOLDEN_PICKAXE, "Amount", ValueType.INTEGER,  null)));
        }
        return set;
    }
}
