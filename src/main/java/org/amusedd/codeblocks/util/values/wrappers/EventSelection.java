package org.amusedd.codeblocks.util.values.wrappers;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.util.fake.EventType;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.amusedd.codeblocks.util.items.PageableItem;
import org.amusedd.codeblocks.util.values.Extension;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;

public class EventSelection implements Extension<EventType> {

    @Override
    public ArrayList<EventType> getSetAsRaw() {
        ArrayList<String> eventNames = getSetAsStrings();
        ArrayList<EventType> eventTypes = new ArrayList<>();
        for (String eventName : eventNames) {
            eventTypes.add(new EventType(eventName));
        }
        return eventTypes;
    }

    @Override
    public ArrayList<String> getSetAsStrings() {
        return CodeBlocks.getPlugin().getEventStorage().getEventNames();
    }

    @Override
    public ArrayList<ItemStack> getSetAsItems() {
        ArrayList<ItemStack> itemStacks = new ArrayList<>();
        HashMap<String, HashMap<Object, ItemStack>> eventPages = new HashMap<>();
        for (String eventName : getSetAsStrings()) {
            String page = CodeBlocks.getPlugin().getEventStorage().getEventClassification(eventName);
            if (!eventPages.containsKey(page)) {
                eventPages.put(page, new HashMap<>());
            }
            ItemStack item = new ItemBuilder(getSpecifiedMaterial(eventName)).setName(eventName).build();
            ItemMeta meta = item.getItemMeta();
            meta.getPersistentDataContainer().set(new NamespacedKey(CodeBlocks.getPlugin(), "type"), PersistentDataType.STRING, eventName);
            item.setItemMeta(meta);
            eventPages.get(page).put(eventPages.get(page).size(), item);
        }
        for (String page : eventPages.keySet()) {
            itemStacks.add(new PageableItem(new ItemBuilder(Material.PAPER).setName(page).build(), eventPages.get(page)));
        }
        return itemStacks;
    }

    @Override
    public Class getExtending() {
        return EventType.class;
    }

    public Material getSpecifiedMaterial(String eventName) {
        //Block, enchantment, entity, hanging, inventory, player, raid, server, vehicle, weather, world, other
        if(eventName.toLowerCase().contains("block")) {
            return Material.COBBLESTONE;
        } else if(eventName.toLowerCase().contains("enchantment")) {
            return Material.ENCHANTING_TABLE;
        } else if(eventName.toLowerCase().contains("entity")) {
            return Material.ZOMBIE_HEAD;
        } else if(eventName.toLowerCase().contains("hanging")) {
            return Material.BAT_SPAWN_EGG;
        } else if(eventName.toLowerCase().contains("inventory")) {
            return Material.CHEST;
        } else if(eventName.toLowerCase().contains("player")) {
            return Material.PLAYER_HEAD;
        } else if(eventName.toLowerCase().contains("raid")) {
            return Material.EMERALD;
        } else if(eventName.toLowerCase().contains("server")) {
            return Material.COMMAND_BLOCK;
        } else if(eventName.toLowerCase().contains("vehicle")) {
            return Material.SADDLE;
        } else if(eventName.toLowerCase().contains("weather")) {
            return Material.WATER_BUCKET;
        } else if(eventName.toLowerCase().contains("world")) {
            return Material.MAP;
        } else {
            return Material.MINECART;
        }
    }
}
