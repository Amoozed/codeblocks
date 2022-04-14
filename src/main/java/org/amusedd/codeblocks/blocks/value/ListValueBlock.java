package org.amusedd.codeblocks.blocks.value;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.menu.ViewValueMenu;
import org.amusedd.codeblocks.util.ViewData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListValueBlock extends ValueBlock {
    ArrayList<ValueBlock> list;
    HashMap<String, Integer> values;
    Class<?> internalType;
    public ListValueBlock(ViewData viewData, Class<?> internalType, ArrayList<ValueBlock> list) {
        super(viewData, ArrayList.class, list);
        this.list = list;
        this.internalType = internalType;
    }

    public ListValueBlock(String name, Class<?> internalType, Material material) {
        super(name, material, ArrayList.class, null);
        list = new ArrayList<>();
        this.internalType = internalType;
    }

    public ListValueBlock(String name, Material material, Class<?> internalType, ArrayList<ValueBlock> list) {
        super(name, material, ArrayList.class, list);
        this.list = list;
        this.internalType = internalType;
    }

    @Override
    public void onGUIItemLeftClick(InventoryClickEvent event) {
        new ViewValueMenu((Player) event.getWhoClicked(), getList()).open();
    }



    @Override
    public Object getValue() {
        return getList();
    }


    @Override
    public void setValue(Object value) {
        if(value instanceof ValueSet) super.setValue(value);
        else{
            CodeBlocks.getPlugin().getLogger().warning("Cannot directly set a dictionary value unless it is a ValueSet");
        }
    }



    public ArrayList<ValueBlock> getList() {
        return this.list;
    }

    public Object getValue(int index){
        return getList().get(index);
    }

    public void addValue(ValueBlock value){
        if(value.getValueType() == internalType) {
            list.add(value);
        } else if(internalType.isAssignableFrom(value.getValueType())){
            list.add(value);
        }
        else {
            CodeBlocks.getPlugin().getLogger().warning("Cannot add a value of type " + value.getValueType().getName() + " to a list of type " + internalType.getName());
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("data", getData().getViewData());
        map.put("type", internalType.getName());
        map.put("set", getValue());
        return map;
    }

    public static ListValueBlock deserialize(Map<String, Object> map) {
        try {
            return new ListValueBlock(
                    (ViewData) map.get("data"),
                    Class.forName((String) map.get("type")),
                    (ArrayList<ValueBlock>) map.get("set")
            );
        } catch (ClassNotFoundException e) {
                e.printStackTrace();
            return null;
        }
    }
}
