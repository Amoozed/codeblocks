package org.amusedd.codeblocks.blocks.print;

import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MessageToServerBlock extends MessageBlock{

    public MessageToServerBlock(String text) {
        super(text);
    }


    @Override
    protected void print(String text) {
        Bukkit.broadcastMessage(text);
    }


    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(super.getBaseItem()).setName("Message to Server").build();
    }
}
