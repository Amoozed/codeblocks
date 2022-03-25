package org.amusedd.codeblocks.util;

import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewData implements ConfigurationSerializable {
    String viewName;
    Material material;
    List<String> description;

    ItemStack item;

    public ViewData(String viewName, Material material, List<String> description) {
        this.viewName = viewName;
        this.material = material;
        this.description = description;
        refreshItem();
    }

    public ViewData(String viewName, Material material){
        this(viewName, material, new ArrayList<>());
    }

    public String getViewName() {
        return viewName;
    }

    public void setMaterial(Material material) {
        this.material = material;
        refreshItem();
    }

    public void refreshItem(){
        this.item = new ItemBuilder(material).setName(ChatColor.WHITE + viewName).setLore(description).build();
    }

    public ItemStack getItem() {
        return item;
    }

    public List<String> getDescription(){
        return description;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("viewName", viewName);
        map.put("material", material.name());
        map.put("description", description);
        return map;
    }

    public static ViewData deserialize(Map<String, Object> map) {
        return new ViewData(
                (String) map.get("viewName"),
                (Material) Material.getMaterial((String) map.get("material")),
                (List<String>) map.get("description")
        );
    }
}
