package org.amusedd.codeblocks.util.values.wrappers;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.util.values.Extension;
import org.amusedd.codeblocks.util.values.TypeData;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;

public class TypeSelection implements Extension<Class> {

    static HashMap<Class, TypeData> types = new HashMap<>();

    @Override
    public ArrayList<Class> getSetAsRaw() {
        return new ArrayList<>(types.keySet());
    }

    @Override
    public ArrayList<String> getSetAsStrings() {
        ArrayList<String> strings = new ArrayList<>();
        for(Class clazz : getSetAsRaw()) {
            strings.add(clazz.getSimpleName());
        }
        return strings;
    }

    @Override
    public ArrayList<ItemStack> getSetAsItems() {
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
    public Class getExtending() {
        return Class.class;
    }

    public static void addType(Class clazz, TypeData type) {
        types.put(clazz, type);
    }
}
