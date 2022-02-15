package org.amusedd.codeblocks.blocks.util;

import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.input.ConditionalType;
import org.amusedd.codeblocks.input.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class ConditionalBlock extends ValueBlock {
    ConditionalType type;
    ValueBlock a;
    ValueBlock b;

    public ConditionalBlock(ConditionalType type, ValueBlock a, ValueBlock b) {
        super(ValueType.BOOLEAN);
        this.type = type;
        this.a = a;
        this.b = b;
        setTag("conditional_type", type.name(), PersistentDataType.STRING);
    }

    @Override
    public boolean canRun() {
        return super.canRun() && a.canRun() && b.canRun();
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.REDSTONE_BLOCK).setName("Conditional Block").build();
    }

    public boolean evaluate(){
        if(type == ConditionalType.EQUALS){
            return a.getValue().equals(b.getValue());
        } else if(type == ConditionalType.NOT_EQUALS){
            return !a.getValue().equals(b.getValue());
        } else{
            if(a.getValue() instanceof Number){
                switch(type){
                    case GREATER_THAN:
                        return ((Number) a.getValue()).doubleValue() > ((Number) b.getValue()).doubleValue();
                    case GREATER_THAN_EQUALS:
                        return ((Number) a.getValue()).doubleValue() >= ((Number) b.getValue()).doubleValue();
                    case LESS_THAN:
                        return ((Number) a.getValue()).doubleValue() < ((Number) b.getValue()).doubleValue();
                    case LESS_THAN_EQUALS:
                        return ((Number) a.getValue()).doubleValue() <= ((Number) b.getValue()).doubleValue();
                }
            }
        }
        return false;
    }

    public ValueBlock getA(){
        return a;
    }

    public ValueBlock getB(){
        return b;
    }


    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("conditional_type", type.name());
        data.put("value_a", a);
        data.put("value_b", b);
        return data;
    }

    public static ConditionalBlock deserialize(Map<String, Object> data){
        ConditionalType type = ConditionalType.valueOf((String) data.get("conditional_type"));
        ValueBlock a = (ValueBlock) data.get("value_a");
        ValueBlock b = (ValueBlock) data.get("value_b");
        return new ConditionalBlock(type, a, b);
    }
}
