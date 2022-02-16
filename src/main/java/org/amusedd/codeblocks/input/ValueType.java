package org.amusedd.codeblocks.input;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public enum ValueType {
    INTEGER,
    DOUBLE,
    STRING,
    BOOLEAN,
    CONDITIONAL(ConditionalType.values()),
    STRING_LIST,
    PLAYER,
    EVENT_TYPE(CodeBlocksPlugin.getInstance().getEventBlockUtility().getEventNames()),
    ITEMSTACK,
    CODEBLOCK,
    ANY;


    Enum[] values;
    ArrayList<String> listValues;

    ValueType() {

    }

    ValueType(Enum[] values) {
        this.values = values;
    }

    ValueType(ArrayList<String> listValues) {
        this.listValues = listValues;
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
            case EVENT_TYPE:
                return CodeBlocksPlugin.getInstance().getEventBlockUtility().getEventNames().contains(value);
            case CODEBLOCK:
                return value instanceof CodeBlock;
            case ANY:
                return true;
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
            case EVENT_TYPE:
            case ANY:
            case CODEBLOCK:
                return value;
            default:
                return null;
        }
    }

    public ArrayList<ItemStack> getValueSelection() {
        if (values == null && listValues == null) return null;
        ArrayList<ItemStack> items = new ArrayList<>();
        if (values != null) {
            for (Enum value : values) {
                for (Enum e : values) {
                    ItemStack item = new ItemBuilder(Material.STONE).setName(e.name()).build();
                    items.add(item);
                }
            }
        } else if (listValues != null) {
            for (String value : listValues) {
                ItemStack item = new ItemBuilder(Material.STONE).setName(value).build();
                items.add(item);
            }
        }
        return items;
    }
}
