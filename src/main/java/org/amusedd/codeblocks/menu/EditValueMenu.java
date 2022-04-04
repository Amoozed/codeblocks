package org.amusedd.codeblocks.menu;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.executables.methods.RunnableMethod;
import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.blocks.value.VariableBlock;
import org.amusedd.codeblocks.commands.input.ChatInput;
import org.amusedd.codeblocks.commands.input.communication.Conversation;
import org.amusedd.codeblocks.commands.input.communication.Receiver;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.amusedd.codeblocks.util.items.LambdaButton;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditValueMenu extends Menu {
    static ItemStack keyboardEntry;
    static ItemStack variableEntry;
    static ItemStack functionEntry;
    static ItemStack callMethodFromVariable;
    static ItemStack callStaticMethod;
    ValueBlock valueBlock;

    static {
        keyboardEntry = new ItemBuilder()
                .setMaterial(Material.PAPER)
                .setName(ChatColor.WHITE + "Keyboard Entry")
                .addLore(ChatColor.GRAY + "Click to enter a value through the chat")
                .build();
        variableEntry = new ItemBuilder()
                .setMaterial(Material.DIAMOND)
                .setName(ChatColor.WHITE + "Variable Entry")
                .addLore(ChatColor.GRAY + "Click to tie this value to a variable")
                .setEnchanted(true)
                .build();
        functionEntry = new ItemBuilder()
                .setMaterial(Material.COMMAND_BLOCK)
                .setName(ChatColor.WHITE + "Function Entry")
                .addLore(ChatColor.GRAY + "Click to get this value from a function")
                .build();
        callMethodFromVariable = new LambdaButton(Material.MAP, ChatColor.WHITE + "Call Method From Variable", List.of(ChatColor.GRAY + "Click to call a function from a variable"), (event) -> {
            EditValueMenu menu = (EditValueMenu) event.getInventory().getHolder();
            ValueBlock block = menu.valueBlock;
            Class type = block.getValueType();
            ArrayList<RunnableMethod> methods = CodeBlocks.getAPI().getNonStaticMethods(type);
            ArrayList<ItemStack> items = new ArrayList<>();
            for (RunnableMethod method : methods) {
                items.add(new ItemBuilder()
                        .setMaterial(Material.COMMAND_BLOCK)
                        .setName(ChatColor.WHITE + method.getMethodName())
                        .addLore(ChatColor.GRAY + "Click to select this method")
                        .build());
            }
            new SelectMenu((Player) event.getWhoClicked(), items, ).open();
        });
        callStaticMethod = new LambdaButton(Material.MAP, ChatColor.WHITE + "Call Static Method", List.of(ChatColor.GRAY + "Click to call a static method"), (event) -> {
            EditValueMenu menu = (EditValueMenu) event.getInventory().getHolder();
            ValueBlock block = menu.valueBlock;
            Class type = block.getValueType();
            ArrayList<RunnableMethod> methods = CodeBlocks.getAPI().getStaticMethods(type);
            ArrayList<ItemStack> items = new ArrayList<>();
            for (RunnableMethod method : methods) {
                items.add(new ItemBuilder()
                        .setMaterial(Material.COMMAND_BLOCK)
                        .setName(ChatColor.WHITE + method.getMethodName())
                        .addLore(ChatColor.GRAY + "Click to select this method")
                        .build());
            }
        });
    }

    public EditValueMenu(Player player, ValueBlock valueBlock) {
        super(player);
        this.valueBlock = valueBlock;
    }


    @Override
    public String getName() {
        return "Set Value";
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = new HashMap<>();
        items.put(0, keyboardEntry);
        items.put(1, variableEntry);
        items.put(2, functionEntry);
        return items;
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        if (item.equals(keyboardEntry)) {
            new ChatInput("Please enter a value of the type: " + valueBlock.getData().getType().getSimpleName(), (Player) event.getWhoClicked(), new Conversation(valueBlock, (Receiver) (event.getClickedInventory().getHolder()))).awaitResponse();
            forceClose();
        } else if (item.equals(variableEntry)) {
            Menu parent = getFirstParentOfType(ContainerEditMenu.class);
            if (parent instanceof ContainerEditMenu) {
                ContainerEditMenu containerEditMenu = (ContainerEditMenu) parent;
                CodeBlockContainer container = containerEditMenu.getContainer();
                ArrayList<VariableBlock> variableBlocks = container.getAllVariables();
                ArrayList<ItemStack> items = new ArrayList<>();
                for (VariableBlock variableBlock : variableBlocks) {
                    items.add(variableBlock.getGUIItem());
                }
                new SelectMenu((Player) event.getWhoClicked(), items, valueBlock, 1).open();
            }
        } else if (item.equals(functionEntry)) {
            d
        }
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }

    @Override
    public int getRows() {
        return 1;
    }
}
