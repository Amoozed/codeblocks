package org.amusedd.codeblocks.menu;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.CodeBlockInfo;
import org.amusedd.codeblocks.blocks.Viewable;
import org.amusedd.codeblocks.blocks.executables.ExecutableCodeBlock;
import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.executables.containers.StandaloneFunctionBlock;
import org.amusedd.codeblocks.commands.input.communication.Conversation;
import org.amusedd.codeblocks.commands.input.communication.Receiver;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.amusedd.codeblocks.util.items.OverridableItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
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
    public void onItemResponse(Conversation conversation, InventoryClickEvent event, int position) {
        ItemStack item = event.getCurrentItem();
        if(item == null || item.getItemMeta() == null || !item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(CodeBlocks.getPlugin(), "type"), PersistentDataType.STRING)){
            CodeBlocks.getPlugin().getLogger().warning("Invalid item selected: Could not find item meta, or it does not have a type.");
            return;
        }
        String name = item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(CodeBlocks.getPlugin(), "type"), PersistentDataType.STRING);
        try{
            Class<? extends ValueHolder> clazz = Class.forName(name).asSubclass(ValueHolder.class);
            ValueHolder block = clazz.getDeclaredConstructor().newInstance();
            block.create(getOwner(), container);
            if(block instanceof ExecutableCodeBlock) ((ExecutableCodeBlock)block).addToContainer(container);
        } catch(Exception e){
            CodeBlocks.getPlugin().getLogger().warning("Could not create block: " + e.getMessage());
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
