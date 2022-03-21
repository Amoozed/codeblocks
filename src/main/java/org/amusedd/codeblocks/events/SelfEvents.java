package org.amusedd.codeblocks.events;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.commands.input.ChatInput;
import org.amusedd.codeblocks.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class SelfEvents implements Listener {
    ArrayList<Menu> closedMenus = new ArrayList<>();
    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getInventory().getHolder() instanceof Menu) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null)
                ((Menu) event.getInventory().getHolder()).clickEvent(event);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        if(event.getInventory().getHolder() instanceof Menu) {
            Menu menu = (Menu) event.getInventory().getHolder();
            if(closedMenus.contains(menu)) return;
            closedMenus.add(menu);
            menu.closeEvent();
            Bukkit.getScheduler().runTaskLater(CodeBlocks.getPlugin(), () -> closedMenus.remove(menu), 5);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if(ChatInput.isAwaitingResponse(event.getPlayer())){
            ChatInput.setResponses(event.getPlayer().getUniqueId().toString(), event.getMessage());
            event.setCancelled(true);
        }
    }
}
