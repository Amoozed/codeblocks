package org.amusedd.codeblocks.commands.input;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.commands.input.communication.Conversation;
import org.amusedd.codeblocks.commands.input.communication.Receiver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatInput {
    static HashMap<String, String> responses = new HashMap<>();
    static ArrayList<String> awaitingResponse = new ArrayList<>();

    Player player;
    Conversation conversation;
    InventoryClickEvent event;
    String prompt;

    int taskID = 0;


    public ChatInput(String prompt, Player player, Conversation conversation) {
        this.player = player;
        this.conversation = conversation;
        this.prompt = prompt;
        awaitingResponse.add(player.getUniqueId().toString());
        player.sendMessage(prompt);
    }

    public static void setResponses(String uuid, String response) {
        responses.put(uuid, response);
        awaitingResponse.remove(uuid);
    }

    public static boolean hasResponded(String uuid){
        boolean bool = responses.containsKey(uuid);
        return bool;
    }

    // Static boolean to check if player is awaiting response
    public static boolean isAwaitingResponse(Player player){
        return awaitingResponse.contains(player.getUniqueId().toString());
    }

    public void awaitResponse(){
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(CodeBlocks.getPlugin(), () -> {
            if (hasResponded(player.getUniqueId().toString())) {
                Bukkit.getScheduler().cancelTask(taskID);
                awaitingResponse.remove(player.getUniqueId().toString());
                sendResponse(responses.get(player.getUniqueId().toString()));
                responses.remove(player.getUniqueId().toString());
            }
        }, 0, 20);
    }

    void sendResponse(String response){
        conversation.getReceiver().onTextResponse(conversation, response);
    }
}
