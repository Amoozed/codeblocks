package org.amusedd.codeblocks.blocks.util;

import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ConditionalBlock<T> extends ValueBlock<T> {
    ConditionalType type;
    ValueBlock<T> a;
    ValueBlock<T> b;

    public ConditionalBlock(ValueBlock<T> a, ValueBlock<T> b, ConditionalType type) {
        this.a = a;
        this.b = b;
        this.type = type;
    }

    @Override
    public ItemStack getGUIItem() {
        return new ItemBuilder(Material.OAK_FENCE).setName("Conditional").build();
    }

    public boolean evaluate(){
        if(type == ConditionalType.EQUALS){
            return a.get().equals(b.get());
        } else if(type == ConditionalType.NOT_EQUALS){
            return !a.get().equals(b.get());
        } else{
            if(a.get() instanceof Number){
                switch(type){
                    case GREATER_THAN:
                        return ((Number) a.get()).doubleValue() > ((Number) b.get()).doubleValue();
                    case GREATER_THAN_EQUALS:
                        return ((Number) a.get()).doubleValue() >= ((Number) b.get()).doubleValue();
                    case LESS_THAN:
                        return ((Number) a.get()).doubleValue() < ((Number) b.get()).doubleValue();
                    case LESS_THAN_EQUALS:
                        return ((Number) a.get()).doubleValue() <= ((Number) b.get()).doubleValue();
                }
            }
        }
        return false;
    }

    public ValueBlock<T> getA(){
        return a;
    }

    public ValueBlock<T> getB(){
        return b;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("conditional_type", type);
        data.put("value_a", a);
        data.put("value_b", b);
        return data;
    }
}
