package org.amusedd.codeblocks.commands.input.communication;

import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface Receiver {
    default void onTextResponse(Conversation conversation, String text){

    }
    /*
    default void onItemResponse(Conversation conversation, InventoryClickEvent event, int position){

    }
    */
    default void onComplete(Conversation conversation){

    }
    default void onValueBlockEdit(Conversation conversation, ValueBlock valueBlock){

    }
    default void onObjectResponse(Conversation conversation, Object object){

    }
}
