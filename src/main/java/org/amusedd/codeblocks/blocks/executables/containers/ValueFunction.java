package org.amusedd.codeblocks.blocks.executables.containers;

import org.amusedd.codeblocks.blocks.executables.ExecutableCodeBlock;
import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.blocks.value.ValueSetBlock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ValueFunction extends CodeBlockContainer implements ValueHolder {
    ValueSetBlock value;

    public ValueFunction(ValueSetBlock value, Map<String, Object> map) {
        super(map);
        this.value = value;
    }

    public ValueFunction() {
        HashMap<String, ValueBlock> values = new HashMap<>();
        values.put("return", new ValueBlock("Value to Return", Material.BEACON, Object.class, null));
        value = new ValueSetBlock(values);
    }

    @Override
    public CodeBlockContainer getContainer() {
        return null;
    }

    @Override
    public void setContainer(CodeBlockContainer container) {

    }

    @Override
    public void finishExecution() {
        super.finishExecution();
    }

    @Override
    public ValueSetBlock getValueSet() {
        return null;
    }

    @Override
    public ItemStack getBaseItem() {
        //TODO: Implement
        return null;
    }
}
