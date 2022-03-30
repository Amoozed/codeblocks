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
        if(value instanceof Class){
            return ((Class) value).getSimpleName();
        } else if(value instanceof String){
            String s = (String) value;
            return s.split("\\.")[s.split("\\.").length - 1];
        }
        return value.toString();
    }

    @Override
    public Object unwrap(ValueBlock value) {
        return value.getCurrentValue();
    }

    @Override
    public Class getType() {
        return Object.class;
    }

    @Override
    public boolean isOfType(Object value) {
        return true;
    }

    @Override
    public boolean isOfType(ValueBlock value) {
        return true;
    }

    @Override
    public boolean isWrappedValue(ValueBlock value) {
        return true;    }

    @Override
    public boolean isWrappedValue(Object value) {
        return true;    }

}
