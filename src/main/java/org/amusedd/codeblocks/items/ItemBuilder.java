package org.amusedd.codeblocks.items;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemBuilder {
    Material mat;
    int amount;
    String name;
    boolean enchanted;
    ItemStack presetItem;
    List<String> lore;
    HashMap<String, Object> tagData = new HashMap<>();
    ArrayList<PersistentDataType> types = new ArrayList<>();

    public ItemBuilder(String name, Material mat, int amount, boolean enchanted, List<String> lore) {
        this.mat = mat;
        this.amount = amount;
        this.name = name;
        this.enchanted = enchanted;
        this.lore = lore;
    }

    public ItemBuilder(String name, Material mat, int amount, boolean enchanted){
        this(name, mat, amount, enchanted, new ArrayList<String>());
    }

    public ItemBuilder(Material mat){
        this(null, mat, 1,false, new ArrayList<>());
    }

    public ItemBuilder(String name, Material mat, int amount){
        this(name, mat, amount, false, new ArrayList<String>());
    }

    public ItemBuilder(String name, Material mat){
        this(name, mat, 1);
    }

    public ItemBuilder(ItemStack item){
        presetItem = item;
    }

    public ItemBuilder(){

    }

    // Setters and Getters that return this
    public ItemBuilder setMaterial(Material mat) {
        this.mat = mat;
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setEnchanted(boolean enchanted) {
        this.enchanted = enchanted;
        return this;
    }

    public ItemBuilder addLore(String lore) {
        this.lore.add(lore);
        return this;
    }

    public ItemBuilder setTag(String key, Object value, PersistentDataType type){
        tagData.put(key, value);
        types.add(type);
        return this;
    }

    // Builds the item
    public ItemStack build() {
        ItemStack item = (presetItem != null) ? presetItem.clone() : new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();
        if(name != null) meta.setDisplayName(name);
        if(enchanted){
            meta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);
        }
        if(lore != null && lore.size() > 0) {
            meta.setLore(lore);
        }
        if(tagData.size() > 0){
            PersistentDataContainer container = meta.getPersistentDataContainer();
            for(int i = 0; i < tagData.keySet().size(); i++){
                String key = tagData.keySet().toArray()[i].toString();
                Object value = tagData.get(key);
                PersistentDataType type = types.get(i);
                container.set(new NamespacedKey(CodeBlocksPlugin.getInstance(), key), type, value);
            }
        }
        item.setItemMeta(meta);
        return item;
    }



}
