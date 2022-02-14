package org.amusedd.codeblocks.blocks.util;

import java.util.HashMap;
import java.util.Map;

public enum ConditionalType {
    EQUALS(0),
    LESS_THAN(1),
    GREATER_THAN(2),
    LESS_THAN_EQUALS(3),
    GREATER_THAN_EQUALS(4),
    NOT_EQUALS(5);

    private int value;
    private static Map map = new HashMap<>();

    private ConditionalType(int value) {
        this.value = value;
    }

    static {
        for (ConditionalType conditionalTypeType : ConditionalType.values()) {
            map.put(conditionalTypeType.value, conditionalTypeType);
        }
    }

    public static ConditionalType valueOf(int pageType) {
        return (ConditionalType) map.get(pageType);
    }

    public int getValue() {
        return value;
    }

}
