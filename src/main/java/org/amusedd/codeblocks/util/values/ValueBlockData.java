package org.amusedd.codeblocks.util.values;


import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.util.ViewData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValueBlockData implements ConfigurationSerializable {
    ViewData viewData;
    Class type;
    boolean isRequired;

    public ValueBlockData(ViewData viewData, Class type) {
        this.viewData = viewData;
        this.type = type;
        this.isRequired = true;
    }

    public ValueBlockData(ViewData viewData, Class type, boolean isRequired) {
        this(viewData, type);
        this.isRequired = isRequired;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public String getName() {
        return viewData.getViewName();
    }

    public ViewData getViewData() {
        return viewData;
    }

    public void setViewData(ViewData viewData) {
        this.viewData = viewData;
    }

    public Material getPreview() {
        return viewData.getItem().getType();
    }


    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }


    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new java.util.HashMap<>();
        map.put("viewData", viewData);
        map.put("type", type.getName());
        map.put("isRequired", isRequired);
        return map;
    }

    public static ValueBlockData deserialize(Map<String, Object> map){
        try{
            return new ValueBlockData((ViewData) map.get("viewData"), Class.forName((String) map.get("type")), (boolean) map.get("isRequired"));
        } catch (Exception e){
            CodeBlocks.getPlugin().getLogger().warning("Failed to deserialize ValueBlockData: " + e.getCause());
            return null;
        }
    }
}
