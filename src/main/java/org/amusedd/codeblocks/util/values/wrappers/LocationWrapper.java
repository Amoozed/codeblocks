package org.amusedd.codeblocks.util.values.wrappers;

import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.util.values.Wrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationWrapper implements Wrapper<Location> {
    @Override
    public ValueBlock wrap(Location value) {
        return null;
    }

    @Override
    public ValueBlock wrap(String value) {
        if(stringToLocation(value) != null) {
            return wrap(stringToLocation(value));
        }
        return null;
    }

    @Override
    public Location unwrap(ValueBlock value) {
        return null;
    }

    @Override
    public Class getType() {
        return Location.class;
    }

    @Override
    public boolean isOfType(Object value) {
        if(value instanceof Location) {
            return true;
        } else {
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
        return new Location(Bukkit.getWorld(split[3].trim()), Double.parseDouble(split[0].trim()), Double.parseDouble(split[1].trim()), Double.parseDouble(split[2].trim()));
    }

}
