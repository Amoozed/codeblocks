package org.amusedd.codeblocks.blocks.print;

import org.amusedd.codeblocks.blocks.ValueBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MessageToPlayerBlock extends MessageBlock{

    ValueBlock<Player> player;

    public MessageToPlayerBlock(String text, ValueBlock<Player> player) {
        super(text);
        this.player = player;
    }

    @Override
    public ItemStack getGUIItem() {
        return null;
    }

    @Override
    protected void print(String text) {
        player.get().sendMessage(text);
    }
}
