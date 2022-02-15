package org.amusedd.codeblocks.blocks.print;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.input.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public abstract class MessageBlock extends CodeBlock {
    ValueBlock text;

    public MessageBlock(ValueBlock text) {
        this.text = text;
        if(text == null) {
            text = new ValueBlock(ValueType.STRING);
        }
    }

    @Override
    public void execute() {
        String print = (String) text.getValue();
        print(print);
        super.execute();
    }

    protected abstract void print(String text);

    @Override
    public boolean canRun() {
        return text.canRun();
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.PAPER).setName("Message Block").build();
    }

    @Override
    public void onGUIRightClick(Player player) {

    }

    @Override
    public void onGUILeftClick(Player player) {}
}
