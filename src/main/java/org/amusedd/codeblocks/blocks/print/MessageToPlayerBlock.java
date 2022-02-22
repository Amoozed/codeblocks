package org.amusedd.codeblocks.blocks.print;

import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.input.ValueBlockData;
import org.amusedd.codeblocks.input.ValueSet;
import org.amusedd.codeblocks.input.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class MessageToPlayerBlock extends MessageBlock{

    ValueSet set;

    public MessageToPlayerBlock(ValueSet text) {
        super(text);
    }

    public MessageToPlayerBlock(){}

    @Override
    protected void print(String text) {

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
        ValueSet set = (ValueSet) data.get("valueSet");
        return new MessageToPlayerBlock(set);
    }

    @Override
    public ValueSet getValueSet() {
        if(set == null) {
            set = super.getValueSet();
            set.addValueBlock("player", new ValueBlock(new ValueBlockData(Material.PLAYER_HEAD, "Player", ValueType.PLAYER, null)));
        }
        return set;
    }
}
