package org.amusedd.codeblocks.blocks.value;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.util.values.ValueBlockData;
import org.bukkit.Material;

public class ValueBlock extends CodeBlock {
    ValueBlockData data;

    public ValueBlock(String name, Material preview, Class type, Object value) {
        this(new ValueBlockData(name, preview, type, value));
    }

    public ValueBlock(ValueBlockData data) {
        this.data = data;
    }

    public ValueBlockData getData() {
        return data;
    }

    public Object getValue() {
        return data.getValue();
    }

    public void setValue(Object value) {
        data.setValue(value);
    }


}
