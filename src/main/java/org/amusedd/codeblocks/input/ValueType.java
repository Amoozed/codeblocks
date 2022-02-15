package org.amusedd.codeblocks.input;

import java.util.HashMap;
import java.util.Map;

public enum ValueType {
    INTEGER(0),
    DOUBLE(1),
    STRING(2),
    BOOLEAN(3);

    private static Map map = new HashMap<>();

    static {
        for (ValueType conditionalTypeType : ValueType.values()) {
            map.put(conditionalTypeType.value, conditionalTypeType);
        }
    }

    private int value;

    private ValueType(int value) {
        this.value = value;
    }

    public static ValueType valueOf(int pageType) {
        return (ValueType) map.get(pageType);
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
            default:
                return null;
        }
    }

    public int getValue() {
        return value;
    }
}
