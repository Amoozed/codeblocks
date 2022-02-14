package org.amusedd.codeblocks.events;

import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.input.ChatInput;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SimpleEventCalls implements Listener {
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        System.out.println("Chat event");
        if(ChatInput.isAwaitingResponse(event.getPlayer())) {
            System.out.println("2");
            ChatInput.setResponses(event.getPlayer().getUniqueId().toString(), event.getMessage());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getInventory().getHolder() instanceof GUI){
            event.setCancelled(true);
            if(event.getCurrentItem() == null) return;
            GUI gui = (GUI) event.getInventory().getHolder();
            gui.itemClicked(event.getCurrentItem(), event);
        }
    }
}
