package org.amusedd.codeblocks.blocks.print;

import org.amusedd.codeblocks.values.ValueSet;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class MessageToConsoleBlock extends MessageBlock{

    public MessageToConsoleBlock(ValueSet text) {
        super(text);
    }
    public MessageToConsoleBlock(){
    }

    @Override
    protected void print(String text) {
        Bukkit.getConsoleSender().sendMessage(text);
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(super.getBaseItem()).setName("Message to console").build();
    }

    public static MessageToConsoleBlock deserialize(Map<String, Object> map) {
        return new MessageToConsoleBlock((ValueSet) map.get("valueset"));
    }

}
