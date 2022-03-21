package org.amusedd.codeblocks.blocks.executables.containers;

import net.md_5.bungee.api.ChatColor;
import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.CodeBlockInfo;
import org.amusedd.codeblocks.blocks.executables.ExecutableCodeBlock;
import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.blocks.value.ValueSetBlock;
import org.amusedd.codeblocks.menu.ContainerEditMenu;
import org.amusedd.codeblocks.menu.EditVariablesMenu;
import org.amusedd.codeblocks.menu.Menu;
import org.amusedd.codeblocks.util.items.ExecuteButton;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@CodeBlockInfo(viewName = "Standalone Function", viewMaterial = Material.COMMAND_BLOCK, description = "A standalone function block", creatable = false)
public class StandaloneFunctionBlock extends CodeBlockContainer implements ValueHolder {
    CodeBlockContainer container;
    ValueSetBlock set;

    public StandaloneFunctionBlock(ValueSetBlock valueSet, Map<String, Object> map) {
        super(map);
        this.set = valueSet;
    }

    public StandaloneFunctionBlock(){
        HashMap<String, ValueBlock> map = new HashMap<>();
        map.put("name", new ValueBlock("Name of Function", Material.NAME_TAG, String.class, null));
        this.set = new ValueSetBlock(map);
    }

    @Override
    public CodeBlockContainer getContainer() {
        return container;
    }

    @Override
    public void setContainer(CodeBlockContainer container) {
        this.container = container;
    }

    @Override
    public ValueSetBlock getValueSet() {
        return this.set;
    }

    public String getName(){
        return (String) this.set.get("name").getValue();
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.COMMAND_BLOCK)
                .setName(ChatColor.WHITE + (this.getName() == null ? "Undefined" : this.getName()))
                .addLore(ChatColor.GREEN + "Standalone Function")
                .addLore(ChatColor.GRAY + (blocks == null || blocks.size() == 0 ? "Empty" : "Contains " + blocks.size() + " CodeBlock(s)"))
                .addLore(ChatColor.GRAY + "Right-Click to view Settings or Execute")
                .addLore(ChatColor.GRAY + "Left-Click to Edit")
                .build();
    }


    public static StandaloneFunctionBlock deserialize(Map<String, Object> map){
        return new StandaloneFunctionBlock((ValueSetBlock) map.get("valueset"), map);
    }

    @Override
    public void onCreate(CodeBlockContainer container) {
        CodeBlocks.getPlugin().getFunctionStorage().addFunction(this);
    }

    @Override
    public void onGUIItemRightClick(InventoryClickEvent event) {
        ValueSetBlock valueSet = getValueSet();
        new EditVariablesMenu((Player) event.getWhoClicked(), valueSet, new ExecuteButton(this)).open();
    }

}
