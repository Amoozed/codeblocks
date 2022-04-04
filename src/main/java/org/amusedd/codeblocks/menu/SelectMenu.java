package org.amusedd.codeblocks.menu;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.commands.input.communication.Conversation;
import org.amusedd.codeblocks.commands.input.communication.Receiver;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectMenu extends Menu implements Receiver {
    HashMap<Object, ItemStack> items;
    Receiver receiver;
    int from;
    public SelectMenu(Player player, HashMap<Object, ItemStack>  items) {
        this(player, items, -1);
    }

    public SelectMenu(Player player, HashMap<Object, ItemStack>  items, int from) {
        super(player);
        this.items = items;
        this.from = from;
    }

    public SelectMenu(Player player, HashMap<Object, ItemStack>  items, Receiver receiver) {
        this(player, items, receiver, -1);
    }

    public SelectMenu(Player player, HashMap<Object, ItemStack> items, Receiver receiver, int from) {
        super(player);
        this.items = items;
        this.receiver = receiver;
        this.from = from;
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public String getName() {
        return "Select an option";
    }

    Object getFirstMatchingKey(ItemStack value){
        for(Object key : items.keySet()) {
            if(items.get(key).equals(value)) return key;
        }
        return null;
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        Object key = getFirstMatchingKey(item);
        if(key != null) {
            onObjectResponse(new Conversation(this), key);
        }
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> ret = new HashMap<>();
        ItemStack[] itemArr = (ItemStack[]) this.items.values().toArray();
        for(int i = 0; i < itemArr.length; i++) {
            ret.put(i, itemArr[i]);
        }
        return ret;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }

    @Override
    public void onObjectResponse(Conversation conversation, Object object) {
        System.out.println("SelectMenu pussy");
        if(getParent() != null && getParent() instanceof SelectMenu) {
            System.out.println("pubby");
            if(conversation.getReceiver() == null) conversation.setReceiver((Receiver) getParent());
            ((Receiver) getParent()).onObjectResponse(conversation, object);
            System.out.println("herber " + getParent().getName());
        }
        else if(receiver != null) {
            System.out.println("dubby");
            if(conversation.getReceiver() == null) conversation.setReceiver(receiver);
            receiver.onObjectResponse(conversation, object);
        }
    }


    public Menu getAbsoluteParent(){
        Menu parent = getParent();
        while(parent != null && parent instanceof SelectMenu) {
            parent = parent.getParent();
        }
        System.out.println("talent:" + parent.getName());
        return parent;
    }

    @Override
    public void onComplete(Conversation conversation) {
        getAbsoluteParent().open();
    }
}
