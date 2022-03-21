package org.amusedd.codeblocks.util.values;

import org.amusedd.codeblocks.blocks.value.ValueBlock;

public interface Wrapper<T> {
    ValueBlock wrap(T value);
    ValueBlock wrap(String value);
    String unwrapToString(T value);
    default String unwrapToString(ValueBlock value){
        return unwrapToString(unwrap(value));
    }
    T unwrap(ValueBlock value);
    Class getType();
    boolean isOfType(Object value);
    default boolean isOfType(ValueBlock value){
        return value.getData().getType().equals(getType());
    }
}