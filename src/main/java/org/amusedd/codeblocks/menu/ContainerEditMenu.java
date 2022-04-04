package org.amusedd.codeblocks.menu;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.CodeBlockInfo;
import org.amusedd.codeblocks.blocks.executables.ExecutableCodeBlock;
import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.executables.containers.StandaloneFunctionBlock;
import org.amusedd.codeblocks.commands.input.communication.Conversation;
import org.amusedd.codeblocks.commands.input.communication.Receiver;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ContainerEditMenu extends Menu implements Receiver {
    CodeBlockContainer container;

    ItemStack addBlock;
    {
        addBlock = new ItemBuilder(Material.EMERALD).setName(ChatColor.GREEN + "Add Block").build();
    }
    ItemStack variables;
    {
        variables = new ItemBuilder(Material.PAPER).setName(ChatColor.WHITE + "Variables").build();
    }

    public ContainerEditMenu(Player player, CodeBlockContainer container) {
        super(player);
        this.container = container;
    }



    @Override
    public String getName() {
        if(container instanceof StandaloneFunctionBlock){
            return ((StandaloneFunctionBlock)container).getName() == null ? "Standalone Function" : ((StandaloneFunctionBlock)container).getName();
        }
        else if(container.getClass().getAnnotation(CodeBlockInfo.class) != null){
            return container.getClass().getAnnotation(CodeBlockInfo.class).viewName();
        } else {
            return container.getClass().getSimpleName();
        }
    }

    @Override
    public void itemClicked(ItemStack item, InventoryClickEvent event) {
        if(item.equals(addBlock)){
            new SelectMenu(getOwner(), CodeBlocks.getAPI().getPreviews(), this).open();
        } else if(item.equals(variables)){
            new VariableMenu(getOwner(), container).open();
        }
        else {
            ExecutableCodeBlock block = getCodeBlock(item);
            if(block != null){
               if(event.isLeftClick()) block.onGUIItemLeftClick(event);
               else if(event.isRightClick()) block.onGUIItemRightClick(event);
            }
        }
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = new HashMap<>();
        for(int i = 0; i < container.getBlocks().size(); i++){
            items.put(i, container.getBlocks().get(i).getGUIItem());
        }
        items.put(53, addBlock);
        items.put(52, variables);
        return items;
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }

    public CodeBlockContainer getContainer(){
        return container;
    }

    public ExecutableCodeBlock getCodeBlock(int slot){
        if(slot < container.getBlocks().size()){
            return container.getBlocks().get(slot);
        }
        return null;
    }

    public ExecutableCodeBlock getCodeBlock(ItemStack item){
        return getCodeBlock(getInventory().first(item));
    }

    @Override
    public void onObjectResponse(Conversation conversation, Object object) {
        Class<? extends CodeBlock> clazz = (Class<? extends CodeBlock>) object;
        try {
            CodeBlock block = clazz.getDeclaredConstructor().newInstance();
            if(block instanceof ValueHolder) ((ValueHolder)block).onInitialize(getOwner(), container);
            else if(block instanceof ExecutableCodeBlock) ((ExecutableCodeBlock)block).addToContainer(container); // Only adds if it isn't a value holder. It is expected that the value holder will add it to the container itself if need be.
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose() {
        if (getParent() != null) {
            getParent().open();
            System.out.println("asshole?");
        } else{
            System.out.println("Parent is null?");
        }
    }
}
