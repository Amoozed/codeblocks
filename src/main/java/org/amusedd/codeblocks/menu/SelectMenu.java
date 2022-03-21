package org.amusedd.codeblocks.menu;

import org.amusedd.codeblocks.commands.input.communication.Conversation;
import org.amusedd.codeblocks.commands.input.communication.Receiver;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectMenu extends Menu implements Receiver {
    ArrayList<ItemStack> items;
    Receiver receiver;
    int from;
    public SelectMenu(Player player, ArrayList<ItemStack> items) {
        this(player, items, -1);
    }

    public SelectMenu(Player player, ArrayList<ItemStack> items, int from) {
        super(player);
        this.items = items;
        this.from = from;
    }

    public SelectMenu(Player player, ArrayList<ItemStack> items, Receiver receiver) {
        super(player);
        this.items = items;
        this.receiver = receiver;
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public String getName() {
        return "Select an option";
    }


    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        onItemResponse(new Conversation(this),event, from);
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> ret = new HashMap<>();
        for(int i = 0; i < items.size(); i++) {
            ret.put(i, items.get(i));
        }
        return ret;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }

    @Override
    public void onItemResponse(Conversation conversation, InventoryClickEvent event, int position) {
        System.out.println("SelectMenu pussy");
        if(getParent() != null && getParent() instanceof SelectMenu) {
            System.out.println("pubby");
            if(conversation.getReceiver() == null) conversation.setReceiver((Receiver) getParent());
            ((Receiver) getParent()).onItemResponse(conversation, event, position);
            System.out.println("herber " + getParent().getName());
        }
        else if(receiver != null) {
            System.out.println("dubby");
            if(conversation.getReceiver() == null) conversation.setReceiver(receiver);
            receiver.onItemResponse(conversation, event, position);
        }
    }

    public Menu getAbsoluteParent(){
        Menu parent = getParent();
        while(parent != null && parent instanceof SelectMenu) {
            parent = parent.getParent();
        }
        return parent;
    }

    @Override
    public void onComplete(Conversation conversation) {
        getAbsoluteParent().open();
    }
}
