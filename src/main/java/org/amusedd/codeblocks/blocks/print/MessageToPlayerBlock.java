package org.amusedd.codeblocks.blocks.print;

import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class MessageToPlayerBlock extends MessageBlock{

    ValueBlock player;

    public MessageToPlayerBlock(ValueBlock text, ValueBlock player) {
        super(text);
        this.player = player;
    }



    @Override
    protected void print(String text) {

    }

    @Override
    public boolean canRun() {
        return super.canRun() && player.canRun();
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(super.getBaseItem()).setName("Message to player").build();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("player", player.serialize());
        return data;
    }

    public static MessageToPlayerBlock deserialize(Map<String, Object> data) {
        ValueBlock text = (ValueBlock) data.get("text");
        ValueBlock player = (ValueBlock) data.get("player");
        return new MessageToPlayerBlock(text, player);
    }
}
