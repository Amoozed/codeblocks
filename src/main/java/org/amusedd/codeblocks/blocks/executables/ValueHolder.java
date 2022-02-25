package org.amusedd.codeblocks.blocks.executables;

import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.blocks.value.ValueSetBlock;

public interface ValueHolder {
    ValueSetBlock getValueSet();
    default void addValue(String name, ValueBlock value) {
        getValueSet().add(name, value);
    }
    default boolean isRunnable() {
        for(ValueBlock value : getValueSet().getValues()) {
            if(value.getData().getValue() == null) return false;
        }
        return true;
    }
}
