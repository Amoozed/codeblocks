package org.amusedd.codeblocks.util.values;

import org.bukkit.Material;

public class ValueBlockData {
    String name;
    Material preview;
    Class type;
    Object value;

    public ValueBlockData(String name, Material preview, Class type, Object value) {
        this.name = name;
        this.preview = preview;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Material getPreview() {
        return preview;
    }

    public Object getValue() {
        return value;
    }

    public Class getType() {
        return type;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
