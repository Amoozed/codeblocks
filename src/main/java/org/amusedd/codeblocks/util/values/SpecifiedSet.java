package org.amusedd.codeblocks.util.values;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public interface SpecifiedSet<T> {
    ArrayList<T> getAsRaw();
    ArrayList<String> getAsStrings();
    ArrayList<ItemStack> getAsItems();
    Class getType();
}
