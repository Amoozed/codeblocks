package org.amusedd.codeblocks.blocks.print;

import org.bukkit.Bukkit;

public class DebugBlock extends MessageBlock{

    public DebugBlock() {
        super("Block executed successfully");
    }

    @Override
    protected void print(String text) {
        Bukkit.broadcastMessage(text);
    }
}
