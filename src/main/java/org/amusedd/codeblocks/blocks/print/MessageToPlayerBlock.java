package org.amusedd.codeblocks.blocks.print;

import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MessageToPlayerBlock extends MessageBlock{

    ValueBlock player;

    public MessageToPlayerBlock(String text, ValueBlock player) {
        super(text);
        this.player = player;
    }



    @Override
    protected void print(String text) {

    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(super.getBaseItem()).setName("Message to player").build();
    }
}
