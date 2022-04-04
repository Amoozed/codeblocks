package org.amusedd.codeblocks.util.values;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public interface SpecifiedSet<T> {
    ArrayList<T> getAsRaw();
    ArrayList<String> getAsStrings();
    ArrayList<ItemStack> getAsItems();
    default HashMap<T, ItemStack> getAsMatch(){
        HashMap<T, ItemStack> map = new HashMap<>();
        for (T t : getAsRaw()) {
            map.put(t, getAsItems().get(getAsRaw().indexOf(t)));
        }
        return map;
    }
    Class getType();
}
