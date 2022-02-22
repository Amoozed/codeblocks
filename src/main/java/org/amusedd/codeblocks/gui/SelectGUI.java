package org.amusedd.codeblocks.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectGUI extends ResponseGUI{

    ArrayList<ItemStack> selections;
    int from;
    public SelectGUI(Player player, GUI parent, ArrayList<ItemStack> selection) {
        this(player, parent, selection, -1);
    }

    public SelectGUI(Player player, GUI parent, ArrayList<ItemStack> selection, int from) {
        super(player, parent, from);
        selections = selection;
        this.from = from;
    }

    @Override
    public String getName() {
        return "Select Value";
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = new HashMap<>();
        for(int i = 0; i < selections.size(); i++) {
            items.put(i, selections.get(i));
        }
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }

    @Override
    public void onClose() {
        System.out.println("Closed ya mam");
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        super.itemClicked(item, event);
    }

    @Override
    public void onPlayerGUISelection(ItemStack item, InventoryClickEvent event, int from) {
        System.out.println("Bingod");
    }
}
