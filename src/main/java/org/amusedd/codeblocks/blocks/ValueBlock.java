package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.input.ValueType;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ValueBlock<T> extends CodeBlock {

    private T value;

    public ValueBlock(){

    }

    public ValueBlock(T value){
        this.value = value;
    }


    public void set(T t) { this.value = t; }
    public T get() { return value; }

    @Override
    public ItemStack getGUIItem() {
        return null;
    }

    @Override
    public void execute() {
        super.execute();
        System.out.println("Executing value block, if you see this, something went wrong");
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("value_type", value.getClass().getSimpleName());
        data.put("value", value);
        return data;
    }

}
