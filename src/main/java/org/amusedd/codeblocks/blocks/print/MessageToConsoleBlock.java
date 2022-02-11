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
    public ItemStack getGUIItem() {
        return new ItemBuilder(Material.FURNACE).setName("Message to console").build();
    }

    @Override
    protected void print(String text) {
        Bukkit.getConsoleSender().sendMessage(text);
    }
}
