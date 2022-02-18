package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.gui.SelectGUI;
import org.amusedd.codeblocks.input.ChatInput;
import org.amusedd.codeblocks.input.ValueSet;
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
        setType(type);
        setValue(value);
    }

    public ValueBlock(ValueType type) {
        this(type, null);
    }


    public void setType(ValueType type) {
        this.type = type;
        addValueToLore("Value Type", type.name());
    }

    public void setValue(Object t) {
        this.value = t;
        addValueToLore("Value", (t == null ? "Undefined" : t.toString()));
    }
    public Object getValue() { return value; }



    @Override
    public boolean run() {
       return true;
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.DIAMOND).setName("Value Block").build();
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
    public void onGUIRightClick(Player player, GUI gui) {
    }

    @Override
    public void onGUILeftClick(Player player, GUI gui) {
        if(type.getValueSelection() != null) {
            new SelectGUI(player, gui, type.getValueSelection()).open();
            return;
        }
        new ChatInput("Enter value of type :" + getValueType().name(), player, this).awaitResponse();
    }

    @Override
    public ValueSet getValueSet() {
        throw new UnsupportedOperationException("Value blocks do not support value sets");
    }

    @Override
    public void onResponse(String response) {
        System.out.println("Variable edit attempt");
        if(getValueType().isOfType(response)) {
            System.out.println("Response is of type");
            setValue(getValueType().getTypedValue(response));
        }
    }
    
    public ValueType getValueType() {
        return type;
    }

    public boolean hasValue() {
        return value != null;
    }
}
