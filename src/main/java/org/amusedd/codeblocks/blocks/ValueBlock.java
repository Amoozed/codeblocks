package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.input.ChatInput;
import org.amusedd.codeblocks.input.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ValueBlock extends CodeBlock {

    private Object value;
    private ValueType type;

    public ValueBlock(ValueType type, Object value) {
        this.value = value;
        this.type = type;
        item = new ItemBuilder(getItem()).addLore(ChatColor.WHITE + "Type: " + ChatColor.GREEN + type.name()).addLore(ChatColor.WHITE + "Value:" + ChatColor.GREEN + ( (value == null) ? "Undefined" : value )).build();
    }

    public ValueBlock(ValueType type) {
        this(type, null);
    }


    public void setValue(Object t) { this.value = t; }
    public Object getValue() { return value; }


    @Override
    public void execute() {
        super.execute();
        System.out.println("Executing value block, if you see this, something went wrong");
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.DIAMOND).setName("Value Block").build();
    }

    @Override
    public boolean canRun() {
        return value != null;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("value", value);
        map.put("type", getValueType().name());
        return map;
    }

    public static ValueBlock deserialize(Map<String, Object> map)  {
        Object value = null;
        if(map.containsKey("value")) {
            value = map.get("value");
        }
        ValueType type = ValueType.valueOf((String) map.get("type"));
        if(value == null) {
            return new ValueBlock(type);
        } else {
            return new ValueBlock(type, value);
        }
    }

    @Override
    public void onGUIRightClick(Player player) {
    }

    @Override
    public void onGUILeftClick(Player player) {
        new ChatInput("Enter value of type :" + getValueType().name(), player, this).awaitResponse();
    }

    @Override
    public void onResponse(String response) {
        if(getValueType().isOfType(response)) {
            setValue(getValueType().getTypedValue(response));
        }
    }
    
    public ValueType getValueType() {
        return type;
    }
}
