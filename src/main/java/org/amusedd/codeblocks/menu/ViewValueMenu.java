package org.amusedd.codeblocks.menu;

import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.blocks.value.ValueSetBlock;
import org.amusedd.codeblocks.commands.input.communication.Conversation;
import org.amusedd.codeblocks.commands.input.communication.Receiver;
import org.amusedd.codeblocks.util.items.OverridableItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ViewValueMenu extends Menu implements Receiver {
    ValueSetBlock valueSetBlock;
    OverridableItemStack[] extraItems;
    Receiver callback;

    public ViewValueMenu(Player player, ValueSetBlock valueSetBlock) {
        super(player);
        this.valueSetBlock = valueSetBlock;
    }

    public ViewValueMenu(Player player, ValueSetBlock valueSetBlock, OverridableItemStack... extraItems) {
        this(player, valueSetBlock);
        this.extraItems = extraItems;
    }


    @Override
    public String getName() {
        //return valueSetBlock.getType() == null ? "Edit Variables" : valueSetBlock.getType();
        return "Edit Variables";
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        if(item != null) {
            ValueBlock valueBlock = valueSetBlock.getValues().get(event.getSlot());
            if(event.isLeftClick()) valueBlock.onGUIItemLeftClick(event);
            else if(event.isRightClick()) valueBlock.onGUIItemRightClick(event);
        }
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = new HashMap<>();
        for(int i = 0; i < valueSetBlock.getValues().size(); i++) {
            items.put(i, valueSetBlock.getValues().get(i).getGUIItem());
        }
        if(extraItems != null) {
            for(int i = 0; i < extraItems.length; i++) {
                items.put(i + valueSetBlock.getValues().size(), extraItems[i]);
            }
        }
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }

    public ValueSetBlock getValueSetBlock() {
        return valueSetBlock;
    }

    @Override
    public void onComplete(Conversation conversation) {
        open();
    }
}
