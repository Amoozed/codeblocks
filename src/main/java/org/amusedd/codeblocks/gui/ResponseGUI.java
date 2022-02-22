package org.amusedd.codeblocks.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class ResponseGUI extends GUI {
    GUI parent;
    int from;

    public ResponseGUI(Player player, GUI parent) {
        this(player, parent, -1);
    }

    public ResponseGUI(Player player, GUI parent, int from) {
        super(player);
        this.parent = parent;
        this.from = from;
    }


    public GUI getParent() {
        return parent;
    }

    public ResponseGUI(Player player) {
        super(player);
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        System.out.println("gay monkey! " + item.getType().name());
        parent.onPlayerGUISelection(item, event, from);
        parent.open();
    }


}
