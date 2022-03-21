package org.amusedd.codeblocks.util.items;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.menu.Menu;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Cod;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class OverridableItemStack extends ItemStack {
    static HashMap<Integer, OverridableItemStack> items = new HashMap<>();
    static int instances = 0;
    public OverridableItemStack(Material material, String name, List<String> lore){
        super(material);
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.getPersistentDataContainer().set(new NamespacedKey(CodeBlocks.getPlugin(), "overridable"), PersistentDataType.INTEGER, instances);
        setItemMeta(meta);
        items.put(instances, this);
        instances++;
    }

    public OverridableItemStack(ItemStack item){
        this(item.getType(), item.getItemMeta().getDisplayName(), item.getItemMeta().getLore());
    }

    public abstract void onClick(Menu menu, InventoryClickEvent event);

    public static boolean isOverridableItemStack(ItemStack item){
        return item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(CodeBlocks.getPlugin(), "overridable"), PersistentDataType.INTEGER);
    }

    public static OverridableItemStack getOverridableItemStack(ItemStack item){
        Integer id = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(CodeBlocks.getPlugin(), "overridable"), PersistentDataType.INTEGER);
        if(id == null){
            System.out.println("Item is not overridable!");
            return null;
        }
        return items.get(id);
    }
}
