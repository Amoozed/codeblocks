package org.amusedd.codeblocks.menu;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.executables.methods.RunnableMethod;
import org.amusedd.codeblocks.commands.input.communication.Receiver;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class MethodSelectMenu extends SelectMenu{
    public MethodSelectMenu(Player player, Class<?> type, boolean staticCall, Receiver receiver) {
        super(player, receiver);
        HashMap<Object, ItemStack> items = new HashMap<>();
        ArrayList<RunnableMethod> methods = (staticCall ? CodeBlocks.getAPI().getStaticMethods(type) : CodeBlocks.getAPI().getNonStaticMethods(type));
        System.out.println(methods);
        methods.forEach(method -> items.put(method, method.getItem()));
        setItems(items);
    }

    public MethodSelectMenu(Player player, Class<?> type, boolean staticCall, Receiver receiver, int from) {
        this(player , type, staticCall, receiver);
        setFrom(from);
    }
}
