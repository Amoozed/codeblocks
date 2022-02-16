package org.amusedd.codeblocks.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class ResponseGUI extends GUI {
    GUI parent;

    public ResponseGUI(Player player, GUI parent) {
        super(player);
        this.parent = parent;
    }

    public GUI getParent() {
        return parent;
    }

    public ResponseGUI(Player player) {
        super(player);
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        parent.onPlayerGUISelection(item, event);
        parent.open();
    }
}
