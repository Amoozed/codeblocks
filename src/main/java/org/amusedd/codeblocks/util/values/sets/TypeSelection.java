package org.amusedd.codeblocks.util.values.sets;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.amusedd.codeblocks.util.values.SpecifiedSet;
import org.amusedd.codeblocks.util.values.TypeData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TypeSelection implements SpecifiedSet<Class> {

    static HashMap<Class, TypeData> types = new HashMap<>();

    @Override
    public ArrayList<Class> getAsRaw() {
        return new ArrayList<>(types.keySet());
    }

    @Override
    public ArrayList<String> getAsStrings() {
        ArrayList<String> strings = new ArrayList<>();
        for(Class clazz : getAsRaw()) {
            strings.add(clazz.getSimpleName());
        }
        return strings;
    }

    @Override
    public ArrayList<ItemStack> getAsItems() {
        ArrayList<ItemStack> items = new ArrayList<>();
        for(Class clazz : types.keySet()) {
            TypeData type = types.get(clazz);
            ItemStack item = type.getItem();
            ItemMeta meta = item.getItemMeta();
            meta.getPersistentDataContainer().set(new NamespacedKey(CodeBlocks.getPlugin(), "type"), PersistentDataType.STRING, clazz.getName());
            item.setItemMeta(meta);
            items.add(item);
        }
        return items;
    }

    @Override
    public Class getType() {
        return Class.class;
    }

    public static void addType(Class clazz, TypeData type) {
        types.put(clazz, type);
    }
}
