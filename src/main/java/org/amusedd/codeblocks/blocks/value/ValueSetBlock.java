package org.amusedd.codeblocks.blocks.value;

import org.amusedd.codeblocks.util.values.ValueBlockData;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;

public class ValueSetBlock extends ValueBlock {
    HashMap<String, ValueBlock> values;
    public ValueSetBlock(String name, HashMap<String, ValueBlock> values) {
        super(name, Material.EMERALD_BLOCK, ValueSetBlock.class, null);
        this.values = values;
    }

    public ValueSetBlock(String name){
        this(name, new HashMap<String, ValueBlock>());
    }

    public void add(String name, ValueBlock value) {
        values.put(name, value);
    }

    public ValueBlock get(String name) {
        return values.get(name);
    }

    public ArrayList<ValueBlock> getValues() {
        return new ArrayList<ValueBlock>(values.values());
    }

}
