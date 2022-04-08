package org.amusedd.codeblocks.menu;

import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.blocks.value.VariableBlock;
import org.amusedd.codeblocks.commands.input.ChatInput;
import org.amusedd.codeblocks.commands.input.communication.Conversation;
import org.amusedd.codeblocks.commands.input.communication.Receiver;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class DirectValueEditMenu extends Menu implements Receiver {

    ValueBlock valueBlock;

    ItemStack keyboardEntry;
    {
        keyboardEntry = new ItemBuilder(Material.PAPER).setName(ChatColor.WHITE + "Keyboard Entry").addLore(ChatColor.GRAY + "Click to enter a value with your keyboard").build();
    }
    ItemStack variableEntry;

    public DirectValueEditMenu(Player player, ValueBlock valueBlock) {
        super(player);
        this.valueBlock = valueBlock;
    }

    @Override
    public String getName() {
        return "Edit Value Block";
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = new HashMap<>();
        items.put(0, keyboardEntry);
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        if (item.equals(keyboardEntry)) {
            new ChatInput("Please enter a value of the type: " + valueBlock.getData().getType().getSimpleName(), (Player) event.getWhoClicked(), new Conversation(valueBlock, this)).awaitResponse();
            forceClose();
        } else if (item.equals(variableEntry)) {
            Menu parent = getFirstParentOfType(ContainerEditMenu.class);
            if (parent instanceof ContainerEditMenu) {
                ContainerEditMenu containerEditMenu = (ContainerEditMenu) parent;
                CodeBlockContainer container = containerEditMenu.getContainer();
                ArrayList<VariableBlock> variableBlocks = container.getAllVariables();
                HashMap<Object, ItemStack> items = new HashMap<>();
                for (VariableBlock variableBlock : variableBlocks) {
                    items.put(variableBlock, variableBlock.getGUIItem());
                }
                new SelectMenu((Player) event.getWhoClicked(), items, valueBlock, 1).open();
            }
        }
    }

    @Override
    public void onComplete(Conversation conversation) {
        getFirstParentOfType(ViewValueMenu.class).open();
    }
}
