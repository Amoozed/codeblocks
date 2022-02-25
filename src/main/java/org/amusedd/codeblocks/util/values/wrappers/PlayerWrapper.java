package org.amusedd.codeblocks.util.values.wrappers;

import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.util.values.Wrapper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerWrapper implements Wrapper<Player> {
    @Override
    public ValueBlock wrap(Player value) {
        return null;
    }

    @Override
    public ValueBlock wrap(String value) {
        return (getPlayerFromString(value) != null) ? wrap(getPlayerFromString(value)) : null;
    }

    @Override
    public Player unwrap(ValueBlock value) {
        return null;
    }

    @Override
    public Class getType() {
        return Player.class;
    }

    @Override
    public boolean isOfType(Object value) {
        if(value instanceof Player){
            return true;
        } else if(value instanceof String){
            return getPlayerFromString((String) value) != null;
        }
        return false;
    }

    Player getPlayerFromString(String value){
        Player player = null;
        if(value.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")){
            player = Bukkit.getPlayer(UUID.fromString(value));
        } else {
            player = Bukkit.getPlayer(value);
        }
        return player;
    }
}
