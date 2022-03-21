package org.amusedd.codeblocks.util.values;

import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.bukkit.Material;

public class AnyWrapper implements Wrapper<Object>{
    @Override
    public ValueBlock wrap(Object value) {
        return new ValueBlock(value.getClass().getSimpleName(), Material.STONE, Object.class, value);
    }

    @Override
    public ValueBlock wrap(String value) {
        return new ValueBlock(value.getClass().getSimpleName(), Material.STONE, Object.class, value);
    }

    @Override
    public String unwrapToString(Object value) {
        return value + "";
    }

    @Override
    public Object unwrap(ValueBlock value) {
        return value.getValue();
    }

    @Override
    public Class getType() {
        return Object.class;
    }

    @Override
    public boolean isOfType(Object value) {
        return true;
    }
}
