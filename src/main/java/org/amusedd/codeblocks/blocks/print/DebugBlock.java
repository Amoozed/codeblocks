package org.amusedd.codeblocks.blocks.print;

import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.input.ValueType;
import org.bukkit.Bukkit;

public class DebugBlock extends MessageBlock {

    public DebugBlock() {
        super(new ValueBlock(ValueType.STRING, "Debug block executed successfully"));
    }

    @Override
    protected void print(String text) {
        Bukkit.broadcastMessage(text);
    }
}
