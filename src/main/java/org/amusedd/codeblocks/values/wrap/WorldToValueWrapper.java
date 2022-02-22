package org.amusedd.codeblocks.values.wrap;

import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.values.ValueBlockData;
import org.amusedd.codeblocks.values.ValueType;
import org.amusedd.codeblocks.values.ValueWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

public class WorldToValueWrapper extends ValueWrapper<World, ValueBlock> {

    @Override
    public ValueBlock getWrappedValue(World value) {
        String name = value.getName();
        return new ValueBlock(new ValueBlockData(Material.MAP, "world", ValueType.STRING, name));
    }

    @Override
    public World getUnwrappedValue(ValueBlock value) {
        if(value.getData().getType() == ValueType.WORLD) {
            String name = (String) value.getData().getValue();
            return Bukkit.getWorld(name);
        }
        Bukkit.getLogger().warning("WorldToValueWrapper: Could not unwrap value");
        return null;
    }

    @Override
    public Class getType() {
        return World.class;
    }
}
