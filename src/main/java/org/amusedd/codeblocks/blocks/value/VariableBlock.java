package org.amusedd.codeblocks.blocks.value;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.Viewable;
import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.commands.input.communication.Conversation;
import org.amusedd.codeblocks.commands.input.communication.Receiver;
import org.amusedd.codeblocks.menu.VariableMenu;
import org.amusedd.codeblocks.util.ViewData;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.amusedd.codeblocks.util.values.ValueBlockData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class VariableBlock extends CodeBlock implements Viewable, ValueHolder, Receiver {
    ValueSetBlock set;

    public VariableBlock(ValueSetBlock set) {
        this.set = set;
        this.set.setChangeCallback(this);
    }

    public VariableBlock(String name, Class type, Object value){
        HashMap<String, ValueBlock> values = new HashMap<>();
        values.put("name", new ValueBlock("Name of Variable", Material.NAME_TAG, String.class, name));
        values.put("type", new ValueBlock("Type", Material.FLINT, Class.class, type));
        values.put("value", new ValueBlock(new ValueBlockData(new ViewData("Value", Material.DIAMOND), Object.class, value, false)));
        this.set = new ValueSetBlock(values);
        this.set.setChangeCallback(this);
    }

    public VariableBlock(){
        HashMap<String, ValueBlock> values = new HashMap<>();
        values.put("name", new ValueBlock("Name of Variable", Material.NAME_TAG, String.class, null));
        values.put("type", new ValueBlock("Type", Material.FLINT, Class.class, null));
        values.put("value", new ValueBlock(new ValueBlockData(new ViewData("Value", Material.DIAMOND), Object.class, null, false)));
        this.set = new ValueSetBlock(values);
        this.set.setChangeCallback(this);
    }

    public String getName(){
        return (String) this.set.get("name").getValue();
    }

    public Class getVariableType(){
        return (Class) this.set.get("type").getValue();
    }

    public Object getValue(){
        if(this.set.get("value") != null) return this.set.get("value").getValue();
        else return null;
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.MAP).setName(this.getName()).addLore(ChatColor.GRAY + "Variable of Type: " + ChatColor.WHITE + "" + getVariableType().getSimpleName()).addLore(ChatColor.GRAY + "Starting Value: " + (getValue() == null ? "None" : getValue().toString())).build();
    }

    @Override
    public void onGUIItemRightClick(InventoryClickEvent event) {
        Viewable.super.onGUIItemRightClick(event);
    }

    @Override
    public void onGUIItemLeftClick(InventoryClickEvent event) {
        Viewable.super.onGUIItemLeftClick(event);
    }

    @Override
    public ValueSetBlock getValueSet() {
        return set;
    }

    @Override
    public void onCreate(CodeBlockContainer container) {
        container.addVariable(getName(), this, true);
    }

    @Override
    public void onValueBlockEdit(Conversation conversation, ValueBlock valueBlock) {
        if(valueBlock.equals(getValueBlock("type"))){
            getValueBlock("value").getData().setType((Class) valueBlock.getValue());
        }
        conversation.complete();
    }

    public static VariableBlock deserialize(Map<String, Object> map){
        return new VariableBlock((ValueSetBlock) map.get("valueset"));
    }
}
