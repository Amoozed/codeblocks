package org.amusedd.codeblocks.util.values;

import org.amusedd.codeblocks.util.ViewData;
import org.bukkit.Material;

import java.util.List;

public class TypeData extends ViewData {
    Class type;

    public TypeData(Class type, Material material, List<String> description) {
        super(type.getSimpleName(), material, description);
        this.type = type;
    }

    public TypeData(Class type, Material material) {
        super(type.getSimpleName(), material);
        this.type = type;
    }

    public Class getType() {
        return type;
    }
}
