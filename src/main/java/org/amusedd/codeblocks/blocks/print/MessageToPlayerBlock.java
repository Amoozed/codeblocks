package org.amusedd.codeblocks.blocks.print;

import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.input.ValueSet;
import org.amusedd.codeblocks.input.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class MessageToPlayerBlock extends MessageBlock{

    ValueSet set;

    public MessageToPlayerBlock(ValueBlock text, ValueBlock player) {
        super(text);
        if(player.getValue() != null) getValueSet().getValueBlock("player").setValue(player.getValue());
    }



    @Override
    protected void print(String text) {

    }

    @Override
    public boolean canRun() {
        return set.isComplete();
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(super.getBaseItem()).setName("Message to player").build();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("player", getValueSet().getValueBlock("player"));
        return data;
    }

    public static MessageToPlayerBlock deserialize(Map<String, Object> data) {
        ValueBlock text = (ValueBlock) data.get("text");
        ValueBlock player = (ValueBlock) data.get("player");
        return new MessageToPlayerBlock(text, player);
    }

    @Override
    public ValueSet getValueSet() {
        if(set == null) {
            set = super.getValueSet();
            set.addValueBlock("player", new ValueBlock(ValueType.PLAYER));
        }
        return set;
    }
}
