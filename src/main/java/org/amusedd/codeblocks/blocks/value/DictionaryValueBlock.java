package org.amusedd.codeblocks.blocks.value;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.menu.ViewValueMenu;
import org.amusedd.codeblocks.util.ViewData;
import org.amusedd.codeblocks.util.values.ValueBlockData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Map;

public class DictionaryValueBlock extends ValueBlock {
    ValueSet valueSet;
    public DictionaryValueBlock(ViewData viewData, ValueSet set) {
        super(viewData, Map.class, null);
        this.valueSet = set;
    }

    public DictionaryValueBlock(String name, Material material) {
        super(name, material, Map.class, null);
        valueSet = new ValueSet();
    }

    public DictionaryValueBlock(String name, Material material, ValueSet set) {
        super(name, material, Map.class, null);
        this.valueSet = set;
    }

    @Override
    public void onGUIItemLeftClick(InventoryClickEvent event) {
        new ViewValueMenu((Player) event.getWhoClicked(), getValueSet()).open();
    }


    public ValueSet getValueSet() {
        return valueSet;
    }

    @Override
    public Object getValue() {
        return valueSet;
    }

    @Override
    public void setValue(Object value) {
        if(value instanceof ValueSet) super.setValue(value);
        else{
            CodeBlocks.getPlugin().getLogger().warning("Cannot directly set a dictionary value unless it is a ValueSet");
        }
    }


    public Object getValue(String key){
        return valueSet.get(key);
    }

    public void setValue(String key, ValueBlock value){
        valueSet.add(key, value);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("data", getData().getViewData());
        map.put("set", getValueSet());
        return map;
    }

    public static DictionaryValueBlock deserialize(Map<String, Object> map){
        return new DictionaryValueBlock(
                (ViewData) map.get("data"),
                (ValueSet) map.get("set")
        );
    }
}
