package org.amusedd.codeblocks.values.wrap;

import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.values.ValueBlockData;
import org.amusedd.codeblocks.values.ValueSet;
import org.amusedd.codeblocks.values.ValueType;
import org.amusedd.codeblocks.values.ValueWrapper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.HashMap;


public class LocationToSetWrapper extends ValueWrapper<Location, ValueSet> {

    @Override
    public ValueSet getWrappedValue(Location value) {
        ValueBlock x = new ValueBlock(new ValueBlockData(Material.MAP, "X", ValueType.DOUBLE, value.getBlockX()));
        ValueBlock y = new ValueBlock(new ValueBlockData(Material.MAP, "Y", ValueType.DOUBLE, value.getBlockY()));
        ValueBlock z = new ValueBlock(new ValueBlockData(Material.MAP, "Z", ValueType.DOUBLE, value.getBlockZ()));
        ValueBlock world = new ValueBlock(new ValueBlockData(Material.MAP, "World", ValueType.WORLD, value.getWorld().getName()));
        HashMap<String, ValueBlock> map = new HashMap<>();
        map.put("x", x);
        map.put("y", y);
        map.put("z", z);
        map.put("world", world);
        return new ValueSet(map);
    }

    @Override
    public Location getUnwrappedValue(ValueSet value) {
        ValueBlock x = value.getValueBlock("x");
        ValueBlock y = value.getValueBlock("y");
        ValueBlock z = value.getValueBlock("z");
        ValueBlock world = value.getValueBlock("world");
        return new Location((World) world.getData().getValue(), (Double) x.getData().getValue(),(Double) y.getData().getValue(), (Double) z.getData().getValue());
    }

    @Override
    public Class getType() {
        return Location.class;
    }
}
