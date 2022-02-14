package org.amusedd.codeblocks.input;

import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatInput {
    static HashMap<String, String> responses = new HashMap<>();
    static ArrayList<String> awaitingResponse = new ArrayList<>();

    Player player;
    GUI gui;
    InventoryClickEvent event;
    String prompt;

    int taskID = 0;

    public ChatInput(String prompt, Player player, InventoryClickEvent event, GUI gui) {
        this.player = player;
        this.event = event;
        this.gui = gui;
        this.prompt = prompt;
        awaitingResponse.add(player.getUniqueId().toString());
        player.sendMessage(prompt);
    }

    public static void setResponses(String uuid, String response) {
        responses.put(uuid, response);
        System.out.println("Set response for " + uuid + " to " + response);
        System.out.println(responses.containsKey(uuid) + " " + hasResponded(uuid));
        awaitingResponse.remove(uuid);
    }

    public static boolean hasResponded(String uuid){
        boolean bool = responses.containsKey(uuid);
        System.out.println("Has responded? " + bool);
        return bool;
    }

    // Static boolean to check if player is awaiting response
    public static boolean isAwaitingResponse(Player player){
        return awaitingResponse.contains(player.getUniqueId().toString());
    }

    public void awaitResponse(){
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(CodeBlocksPlugin.getInstance(), () -> {
            Bukkit.broadcastMessage("searching");
            System.out.println(responses);
            if (hasResponded(player.getUniqueId().toString())) {
                Bukkit.broadcastMessage("Response found?");
                Bukkit.getScheduler().cancelTask(taskID);
                awaitingResponse.remove(player.getUniqueId().toString());
                gui.onPlayerTextResponse(event.getCurrentItem(), event, responses.get(player.getUniqueId().toString()));
                responses.remove(player.getUniqueId().toString());
            }
        }, 0, 20);
    }
}
