package org.amusedd.codeblocks.blocks.print;

import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MessageToConsoleBlock extends MessageBlock{

    public MessageToConsoleBlock(String text) {
        super(text);
    }


    @Override
    protected void print(String text) {
        Bukkit.getConsoleSender().sendMessage(text);
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(super.getBaseItem()).setName("Message to console").build();
    }


}
