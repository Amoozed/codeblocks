package org.amusedd.codeblocks.blocks;

import net.md_5.bungee.api.ChatColor;
import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.blocks.value.ValueSetBlock;
import org.amusedd.codeblocks.menu.ContainerEditMenu;
import org.amusedd.codeblocks.menu.EditVariablesMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public interface Viewable {
    ItemStack getBaseItem();
    default ItemStack getGUIItem(){
        return updateItemLore();
    }
    default ItemStack updateItemLore() {
        if(this instanceof ValueHolder) {
            ItemStack item = getBaseItem();
            if(item.getItemMeta().hasLore()) return item;
            List<String> lore = new ArrayList<>();
            for(ValueBlock valueBlock : ((ValueHolder) this).getValueSet().getValues()) {
                lore.add(ChatColor.GRAY + valueBlock.getData().getName() + ": " + (valueBlock.getValue() != null ? ChatColor.WHITE + "" + valueBlock.getValue() + "" : ChatColor.RED + "Undefined"));
            }
            ItemMeta meta = getBaseItem().getItemMeta();
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
        return getBaseItem();
    }

    default void onGUIItemRightClick(InventoryClickEvent event) {
        if(this instanceof ValueHolder) {
            ValueSetBlock valueSet = ((ValueHolder) this).getValueSet();
            new EditVariablesMenu((Player) event.getWhoClicked(), valueSet).open();
        }
    }

    default void onGUIItemLeftClick(InventoryClickEvent event) {
        if(this instanceof CodeBlockContainer){
            CodeBlockContainer codeBlockContainer = (CodeBlockContainer) this;
            new ContainerEditMenu((Player) event.getWhoClicked(), codeBlockContainer).open();
        }
    }
}
