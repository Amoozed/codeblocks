package org.amusedd.codeblocks.blocks.print;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public abstract class MessageBlock extends CodeBlock {
    String text;

    public MessageBlock(String text) {
        this.text = text;
        setTag("text", text, PersistentDataType.STRING);
    }

    @Override
    public void execute() {
        print(text);
        super.execute();
    }

    protected abstract void print(String text);

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.PAPER).setName("Message Block").build();
    }
}
