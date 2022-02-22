package org.amusedd.codeblocks.blocks.print;

import org.amusedd.codeblocks.values.ValueSet;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class MessageToServerBlock extends MessageBlock{

    public MessageToServerBlock(ValueSet text) {
        super(text);
    }

    public MessageToServerBlock(){}

    @Override
    protected void print(String text) {
        Bukkit.broadcastMessage(text);
    }


    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(super.getBaseItem()).setName("Message to Server").build();
    }

    public static MessageToServerBlock deserialize(Map<String, Object> map) {
        return new MessageToServerBlock((ValueSet) map.get("valueset"));
    }
}
