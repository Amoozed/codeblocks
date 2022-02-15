package org.amusedd.codeblocks.input;

import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public enum ValueType {
    INTEGER,
    DOUBLE,
    STRING,
    BOOLEAN,
    CONDITIONAL(ConditionalType.values()),
    STRING_LIST,
    PLAYER,
    ITEMSTACK;


    ValueType(){

    }

    Enum[] values;

    ValueType(Enum[] values){
        this.values = values;
    }

    public boolean isOfType(Object value) {
        switch (this) {
            case INTEGER:
                if (value instanceof String) {
                    String stringValue = (String) value;
                    return stringValue.matches("[0-9]+");
                } else return value instanceof Integer;
            case DOUBLE:
                if (value instanceof String) {
                    String stringValue = (String) value;
                    return stringValue.matches("[0-9]+\\.[0-9]+");
                } else return value instanceof Double;
            case STRING:
                return value instanceof String;
            case BOOLEAN:
                if (value instanceof String) {
                    String stringValue = (String) value;
                    return stringValue.matches("true|false");
                } else return value instanceof Boolean;
            case CONDITIONAL:
                if (value instanceof ConditionalType) {
                    return true;
                } else {
                    if (value instanceof String) {
                        String stringValue = (String) value;
                        try {
                            ConditionalType type = ConditionalType.valueOf(stringValue);
                            return true;
                        } catch (IllegalArgumentException e) {
                            return false;
                        }
                    }
                }
            default:
                return false;
        }
    }

    public Object getTypedValue(Object value) {
        switch (this) {
            case INTEGER:
                return Integer.parseInt((String) value);
            case DOUBLE:
                return Double.parseDouble((String) value);
            case STRING:
                return (String) value;
            case BOOLEAN:
                return Boolean.parseBoolean((String) value);
            case CONDITIONAL:
                return ConditionalType.valueOf((String) value);
            default:
                return null;
        }
    }

    public ArrayList<ItemStack> getValueSelection(){
        if(values == null) return null;
        ArrayList<ItemStack> items = new ArrayList<>();
        for(Enum e : values){
            ItemStack item = new ItemBuilder(Material.STONE).setName(e.name()).build();
            items.add(item);
        }
        return items;
    }
}
