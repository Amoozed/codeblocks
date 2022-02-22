package org.amusedd.codeblocks.blocks.print;

import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.input.ValueBlockData;
import org.amusedd.codeblocks.input.ValueSet;
import org.amusedd.codeblocks.input.ValueType;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.Map;

public class DebugBlock extends MessageBlock {

    public DebugBlock(ValueSet valueSet) {
        super(valueSet);
    }

    @Override
    protected void print(String text) {
        Bukkit.broadcastMessage(text);
    }

    public static DebugBlock deserialize(Map<String, Object> data){
        return new DebugBlock((ValueSet) data.get("valueSet"));
    }

    @Override
    public ValueSet getValueSet() {
        return null;
    }
}
