package org.amusedd.codeblocks.blocks.value;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.executables.ExecutableCodeBlock;
import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.util.ViewData;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.amusedd.codeblocks.util.values.ValueBlockData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SetVariableBlock extends CodeBlock implements ExecutableCodeBlock, ValueHolder {
    ValueSetBlock valueSetBlock;
    CodeBlockContainer container;

    public SetVariableBlock(ValueSetBlock valueSetBlock) {
        this.valueSetBlock = valueSetBlock;
    }

    public SetVariableBlock(){
        HashMap<String, ValueBlock> valueBlocks = new HashMap<String, ValueBlock>();
        valueBlocks.put("variable", new ValueBlock(new ValueBlockData(new ViewData("Name of Variable", Material.NAME_TAG), String.class, null)));
        valueBlocks.put("value", new ValueBlock(new ValueBlockData(new ViewData("Value", Material.DIAMOND), Object.class, null, false)));
        this.valueSetBlock = new ValueSetBlock(valueBlocks);
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.EMERALD_BLOCK).setName("Set Variable").build();
    }

    @Override
    public boolean run() {
        return true;
    }

    @Override
    public CodeBlockContainer getContainer() {
        return container;
    }

    @Override
    public void setContainer(CodeBlockContainer container) {
        this.container = container;
    }

    @Override
    public ValueSetBlock getValueSet() {
        return valueSetBlock;
    }

    public static SetVariableBlock deserialize(Map<String, Object> data){
        return new SetVariableBlock((ValueSetBlock) data.get("valueset"));
    }
}
