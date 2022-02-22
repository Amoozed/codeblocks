package org.amusedd.codeblocks.gui;

import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PaginatedSelectGUI extends PaginatedGUI{

    ArrayList<ItemStack> items;
    GUI parent;
    int from;

    /*
    public PaginatedSelectGUI(Player player, ArrayList<String> values, GUI parent, int from) {
        super(player);
        this.items = new ArrayList<>();
        this.parent = parent;
        for(String value : values){
            this.items.add(new ItemBuilder(Material.STONE).setName(value).build());
        }
    }
    */

    public PaginatedSelectGUI(Player player, List<ItemStack> items, GUI parent, int from) {
        super(player);
        this.items = (ArrayList<ItemStack>) items;
        this.parent = parent;
        this.from = from;
    }

    public PaginatedSelectGUI(Player player, List<ItemStack> items, GUI parent) {
        this(player, items, parent, -1);
    }

    /*
    public PaginatedSelectGUI(Player player, ArrayList<String> values, GUI parent) {
        this(player, values, parent, -1);
    }
    */
    @Override
    public String getName() {
        return "Select a value";
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }

    @Override
    public void onClose() {

    }

    @Override
    public HashMap<Integer, ItemStack> getPage(int page) {
        if(page > this.getPages()){
            System.out.println("Page " + page + " is out of bounds");
            return null;
        }
        HashMap<Integer, ItemStack> items = new HashMap<>();
        for(int i = 0; i < 45; i++){
            if(i + (45 * page) < this.items.size()){
                items.put(i, this.items.get(i + (45 * page)));
            } else {
                System.out.println("Index " + i + " is out of bounds of " + this.items.size());
            }
        }
        return items;
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        if(item.equals(nextPage)) {
            currentPage = (currentPage + 1 > getPages()) ? 1 : currentPage + 1;
        } else if(item.equals(lastPage)) {
            currentPage = (currentPage - 1 < 1) ? getPages() : currentPage - 1;
        } else{
            parent.onPlayerGUISelection(item, event, from);
            parent.open();
            return;
        }
        open();
    }
    @Override
    public int getPages() {
        return this.items.size() / 45;
    }

    @Override
    public void onPlayerGUISelection(ItemStack item, InventoryClickEvent event, int from) {
        System.out.println("Bingod");
    }

}
