package org.amusedd.codeblocks.blocks.util;

import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.input.ConditionalType;
import org.amusedd.codeblocks.input.ValueSet;
import org.amusedd.codeblocks.input.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class ConditionalBlock extends ValueBlock {
    ValueSet set;

    public ConditionalBlock(ValueBlock type, ValueBlock a, ValueBlock b) {
        super(ValueType.BOOLEAN);
        if(type.getValue() != null) getValueSet().setValue("conditional_type", type.getValue());
        if(a.getValue() != null) getValueSet().setValue("a", a.getValue());
        if(b.getValue() != null) getValueSet().setValue("b", b.getValue());
        setTag("conditional_type", type.getValue(), PersistentDataType.STRING);
    }



    @Override
    public ValueSet getValueSet() {
        if(set == null){
            set = new ValueSet();
            set.addValueBlock("a", new ValueBlock(ValueType.ANY));
            set.addValueBlock("b", new ValueBlock(ValueType.ANY));
            set.addValueBlock("conditional_type", new ValueBlock(ValueType.CONDITIONAL));
        }
        return set;
    }


    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.REDSTONE_BLOCK).setName("Conditional Block").build();
    }

    public boolean evaluate(){
        ValueBlock a = getA();
        ValueBlock b = getB();
        ConditionalType type = (ConditionalType) getValueSet().getValueBlock("conditional_type").getValue();
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
        return set.getValueBlock("a");
    }

    public ValueBlock getB(){
        return set.getValueBlock("b");
    }


    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("conditional_type", getValueSet().getValueBlock("conditional_type").getValue());
        data.put("value_a", getA());
        data.put("value_b", getB());
        return data;
    }

    public static ConditionalBlock deserialize(Map<String, Object> data){
        ValueBlock type = (ValueBlock) data.get("conditional_type");
        ValueBlock a = (ValueBlock) data.get("value_a");
        ValueBlock b = (ValueBlock) data.get("value_b");
        return new ConditionalBlock(type, a, b);
    }
}
