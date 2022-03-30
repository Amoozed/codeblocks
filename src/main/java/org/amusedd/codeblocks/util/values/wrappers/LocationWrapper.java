package org.amusedd.codeblocks.util.values.wrappers;

import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.blocks.value.ValueSetBlock;
import org.amusedd.codeblocks.util.ViewData;
import org.amusedd.codeblocks.util.values.Wrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;

public class LocationWrapper implements Wrapper<Location> {
    @Override
    public ValueBlock wrap(Location value) {
        HashMap<String, ValueBlock> map = new HashMap<>();
        map.put("x", new ValueBlock(new ViewData("X", Material.MAP, new ArrayList<>()), Double.class, value.getX()));
        map.put("y", new ValueBlock(new ViewData("Y", Material.MAP, new ArrayList<>()), Double.class, value.getY()));
        map.put("z", new ValueBlock(new ViewData("Z", Material.MAP, new ArrayList<>()), Double.class, value.getZ()));
        map.put("world", new ValueBlock(new ViewData("World", Material.MAP, new ArrayList<>()), String.class, value.getWorld().getName()));
        return new ValueSetBlock("Location", map);
    }

    @Override
    public ValueBlock wrap(String value) {
        if(stringToLocation(value) != null) {
            return wrap(stringToLocation(value));
        }
        return null;
    }

    @Override
    public String unwrapToString(Location value) {
        return "(Specified Location)";
    }

    @Override
    public Location unwrap(ValueBlock value) {
        assert value instanceof ValueSetBlock;
        return new Location(Bukkit.getWorld((String) ((ValueSetBlock) value).get("world").getCurrentValue()), (Double) ((ValueSetBlock) value).get("x").getCurrentValue(), (Double) ((ValueSetBlock) value).get("y").getCurrentValue(), (Double) ((ValueSetBlock) value).get("z").getCurrentValue());
    }

    @Override
    public Class getType() {
        return Location.class;
    }

    @Override
    public boolean isOfType(Object value) {
        if(value instanceof Location) {
            return true;
        } else if(value instanceof ValueSetBlock){
            return ((ValueSetBlock) value).get("x").getCurrentValue() != null && ((ValueSetBlock) value).get("y").getCurrentValue() != null && ((ValueSetBlock) value).get("z").getCurrentValue() != null && ((ValueSetBlock) value).get("world").getCurrentValue() != null;
        }
        else {
            if(value instanceof String) {
                return stringToLocation((String) value) != null;
            }
        }
        return false;
    }


    // x, y, z, world
    Location stringToLocation(String value) {
        String[] split = value.split(",");
        if(split.length != 4) {
            Bukkit.getLogger().warning("Invalid location string: " + value);
            return null;
        }
        try{
            Location loc = new Location(Bukkit.getWorld(split[3].trim()), Double.parseDouble(split[0].trim()), Double.parseDouble(split[1].trim()), Double.parseDouble(split[2].trim()));
            return loc;
        } catch (Exception e) {
            Bukkit.getLogger().warning("Invalid/Malformed location string: " + value);
            return null;
        }
    }

}
