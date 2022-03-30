package org.amusedd.codeblocks.util.values.wrappers;

import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.util.values.Wrapper;
import org.bukkit.Material;

public class TypeWrapper implements Wrapper<Class> {

    @Override
    public ValueBlock wrap(Class value) {
        return new ValueBlock("Type", Material.FLINT, Class.class, value.getName());
    }

    @Override
    public ValueBlock wrap(String value) {
        return wrap(stringToClass(value));
    }

    @Override
    public String unwrapToString(Class value) {
        return value.getSimpleName();
    }

    @Override
    public Class unwrap(ValueBlock value) {
        return stringToClass((String) value.getCurrentValue());
    }

    @Override
    public Class getType() {
        return Class.class;
    }

    @Override
    public boolean isOfType(Object value) {
        if(value instanceof String){
            return stringToClass((String) value) != null;
        } else if(value instanceof Class){
            return true;
        }
        return false;
    }

    Class stringToClass(String value) {
        try {
            return Class.forName(value);
        } catch (Exception e) {
            return null;
        }
    }
}
