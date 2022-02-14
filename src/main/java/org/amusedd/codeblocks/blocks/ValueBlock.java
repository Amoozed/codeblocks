package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.input.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Cod;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ValueBlock extends CodeBlock {

    private Object value;

    public ValueBlock(Object value) {
        this.value = value;
    }

    public ValueBlock(){

    }

    public void setValue(Object t) { this.value = t; }
    public Object getValue() { return value; }


    @Override
    public void execute() {
        super.execute();
        System.out.println("Executing value block, if you see this, something went wrong");
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.DIAMOND).setName("Value Block").build();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("value", value);
        map.put("type", value.getClass().getSimpleName());
        return map;
    }

    public static ValueBlock deserialize(Map<String, Object> map)  {
        Object value = map.get("value");
        return new ValueBlock(value);
    }
}
