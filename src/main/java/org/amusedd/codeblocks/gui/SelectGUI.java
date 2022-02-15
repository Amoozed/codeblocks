package org.amusedd.codeblocks.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectGUI extends ResponseGUI{

    ArrayList<ItemStack> selections;
    public SelectGUI(Player player, GUI parent, ArrayList<ItemStack> selection) {
        super(player, parent);
        selections = selection;
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
}
