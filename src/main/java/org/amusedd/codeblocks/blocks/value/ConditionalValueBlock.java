package org.amusedd.codeblocks.blocks.value;

import org.amusedd.codeblocks.menu.Menu;
import org.amusedd.codeblocks.util.ViewData;
import org.amusedd.codeblocks.util.values.ValueBlockData;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.Map;

public class ConditionalValueBlock extends ValueBlock {

    public ConditionalValueBlock(String name, List<String> description, boolean start) {
        super(new ViewData(name, !start ? Material.RED_CONCRETE : Material.GREEN_CONCRETE, description), Boolean.class, start);
    }

    @Override
    public void onGUIItemLeftClick(InventoryClickEvent event) {
        boolean newValue = !(boolean)getValue();
        setValue(newValue);
        getData().getViewData().setMaterial(newValue ? Material.GREEN_CONCRETE : Material.RED_CONCRETE);
        ((Menu)event.getInventory().getHolder()).open();
    }

    public static ConditionalValueBlock deserialize(Map<String, Object> map){
        ValueBlockData data = (ValueBlockData) map.get("data");
        return new ConditionalValueBlock(data.getName(), data.getViewData().getDescription(), Boolean.parseBoolean((String) map.get("value")));
    }


}