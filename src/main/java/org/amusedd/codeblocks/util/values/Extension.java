package org.amusedd.codeblocks.util.values;

import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public interface Extension<T>{
    Class<?> getExtending();
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
    default ArrayList<T> getSetAsRaw() {
        return null;
    }
    default ArrayList<String> getSetAsStrings(){
        return null;
    }
    default ArrayList<ItemStack> getSetAsItems(){
        return null;
    }
    default HashMap<T, ItemStack> getSetAsMatch(){
        HashMap<T, ItemStack> map = new HashMap<>();
        for (T t : getSetAsRaw()) {
            map.put(t, getSetAsItems().get(getSetAsRaw().indexOf(t)));
        }
        return map;
    }
}
