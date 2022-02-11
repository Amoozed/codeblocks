package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.input.ValueType;
import org.bukkit.entity.Cod;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class ValueBlock<T> extends CodeBlock {

    private T value;
    private CodeBlock scope;

    public ValueBlock(CodeBlock scope){
        this.scope = scope;
    }

    public ValueBlock(T value){
        this.value = value;
        setTag("value_type", value.getClass().getSimpleName(), PersistentDataType.STRING);
        setTag("value", value, PersistentDataType.STRING);
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
        return null;
    }
}
