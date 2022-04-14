package org.amusedd.codeblocks.util.values;

import org.amusedd.codeblocks.blocks.value.ValueBlock;

public interface Wrapper<T>{
    default String overrideToString(Object o){
        if(o != null){
            return o.toString();
        } else {
            return null;
        }
    }
    default ValueBlock toValueBlock(Object o){
        return null;
    }
    default T fromValueBlock(ValueBlock valueBlock){
        if(isOfType(valueBlock)){
            return (T) valueBlock.getValue();
        }
        return null;
    }
    default T fromString(String s){
        return null;
    }
    default boolean isOfType(Object o){
        return true;
    }
    Class<?> getWrapperType();
}
