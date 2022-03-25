package org.amusedd.codeblocks.blocks.value;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.Viewable;
import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.commands.input.communication.Conversation;
import org.amusedd.codeblocks.commands.input.communication.Receiver;
import org.amusedd.codeblocks.util.ViewData;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.amusedd.codeblocks.util.values.ValueBlockData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VariableBlock extends CodeBlock implements Viewable, ValueHolder, Receiver {
    ValueSetBlock set;
    Object startingValue;
    public VariableBlock(ValueSetBlock set) {
        this.set = set;
        this.set.setChangeCallback(this);
    }

    public VariableBlock(String name, Class type, Object value, boolean staticVariable){
        HashMap<String, ValueBlock> values = new HashMap<>();
        values.put("static", new ConditionalValueBlock("Static", new ArrayList<String>(List.of(ChatColor.GRAY + "If set to " + ChatColor.GREEN + "true" + ChatColor.GRAY + ", any changes to this variable will be retained", ChatColor.GRAY + "Otherwise, the value of this variable will be reset each execution")), staticVariable));
        values.put("name", new ValueBlock("Name of Variable", Material.NAME_TAG, String.class, name));
        values.put("type", new ValueBlock("Type", Material.FLINT, Class.class, type));
        values.put("value", new ValueBlock(new ValueBlockData(new ViewData("Value", Material.DIAMOND), Object.class, value, false)));this.set = new ValueSetBlock(values);
        this.set.setChangeCallback(this);
        startingValue = value;
    }

    public VariableBlock(){
        HashMap<String, ValueBlock> values = new HashMap<>();
        values.put("static", new ConditionalValueBlock("Static", new ArrayList<String>(List.of(ChatColor.GRAY + "If set to " + ChatColor.GREEN + "true" + ChatColor.GRAY + ", any changes to this variable will be retained", ChatColor.GRAY + "Otherwise, the value of this variable will be reset each execution")), false));
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

    public void setValue(Object value, boolean retain){
        this.set.get("value").setValue(value);
        if(retain || isStatic()) this.startingValue = value;
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.MAP).setName(this.getName()).addLore(ChatColor.GRAY + "Variable of Type: " + ChatColor.WHITE + "" + getVariableType().getSimpleName()).addLore(ChatColor.GRAY + "Starting Value: " + (getValue() == null ? "None" : getValue().toString())).build();
    }


    public boolean isStatic(){
        return (boolean) this.set.get("static").getValue();
    }

    @Override
    public ValueSetBlock getValueSet() {
        return set;
    }

    @Override
    public void onCreate(CodeBlockContainer container) {
        container.addVariable(getName(), this, true);
        startingValue = getValue();
    }

    public void reset(){
        setValue(startingValue, false);
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
