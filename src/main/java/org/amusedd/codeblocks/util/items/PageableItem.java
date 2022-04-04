package org.amusedd.codeblocks.util.items;

import org.amusedd.codeblocks.menu.Menu;
import org.amusedd.codeblocks.menu.SelectMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PageableItem extends OverridableItemStack{
    HashMap<Object, ItemStack> items;
    public PageableItem(Material material, String name, List<String> lore,  HashMap<Object, ItemStack> items) {
        super(material, name, lore);
        this.items = items;
    }

    public PageableItem(ItemStack item,  HashMap<Object, ItemStack> items) {
        super(item);
        this.items = items;
    }

    public void addItem(Object obj, ItemStack item) {
        items.put(obj, item);
    }

    public ArrayList<ItemStack> getItems() {
        return items.values().stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    @Override
    public void onClick(Menu menu, InventoryClickEvent event) {
        SelectMenu selectMenu = new SelectMenu((Player) event.getWhoClicked(), items);
        selectMenu.open();
    }
}
