package org.amusedd.codeblocks.input;

import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueBlockData implements ConfigurationSerializable {
    Material mat;
    String name;
    ValueType type;
    boolean isRequired;
    ItemStack item;
    Object value;

    public ValueBlockData(Material mat, String name, ValueType type, Object value, boolean isRequired) {
        this.mat = mat;
        this.name = name;
        this.type = type;
        this.isRequired = isRequired;
        this.value = value;
        System.out.println("Pussy Woo-- " + name + ": " + value);
        item = new ItemBuilder(mat).setName(ChatColor.WHITE + "" + ChatColor.BOLD + name).build();
        updateLore();
    }

    public ValueBlockData(Material mat, String name, ValueType type, Object value) {
        this(mat, name, type, value, true);
    }

    public Material getMaterial() {
        return mat;
    }

    public String getName() {
        return name;
    }

    public ValueType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public boolean setValue(Object value) {
        if(type.isOfType(value)) {
            this.value = value;
            updateLore();
            return true;
        } else {
            Bukkit.getLogger().warning("Value " + value + " is not of type " + type.name());
            return false;
        }
    }

    public boolean isRequired() {
        return isRequired;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("material", mat.name());
        data.put("name", name);
        data.put("type", type.name());
        data.put("isRequired", isRequired);
        data.put("value", value);
        return data;
    }

    public static ValueBlockData deserialize(Map<String, Object> args) {
        Material mat = Material.getMaterial((String) args.get("material"));
        String name = (String) args.get("name");
        ValueType type = ValueType.valueOf((String) args.get("type"));
        boolean isRequired = (boolean) args.get("isRequired");
        Object value = args.get("value");
        return new ValueBlockData(mat, name, type, value, isRequired);
    }

    void updateLore(){
        ItemMeta meta = item.getItemMeta();
        if(getName() != null) meta.setDisplayName(getName());
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + "Type: " + type.name());
        if(isRequired)
            lore.add(ChatColor.RED + "" + ChatColor.BOLD + "Required");
        lore.add(ChatColor.GRAY + "Value: " + ChatColor.WHITE + (value != null ? value.toString() : "Undefined"));
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public ItemStack getItem() {
        return item;
    }
}
