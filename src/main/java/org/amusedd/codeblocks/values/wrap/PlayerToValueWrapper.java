package org.amusedd.codeblocks.values.wrap;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.values.ValueBlockData;
import org.amusedd.codeblocks.values.ValueType;
import org.amusedd.codeblocks.values.ValueWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerToValueWrapper extends ValueWrapper<Player, ValueBlock> {

    @Override
    public ValueBlock getWrappedValue(Player value) {
        String uuid = value.getUniqueId().toString();
        return new ValueBlock(new ValueBlockData(Material.PLAYER_HEAD, "Player", ValueType.PLAYER, uuid));
    }

    @Override
    public Player getUnwrappedValue(ValueBlock value) {
        ValueBlockData data = value.getData();
        if (data.getType() == ValueType.PLAYER) {
            return CodeBlocksPlugin.getInstance().getServer().getPlayer(UUID.fromString((String) data.getValue()));
        }
        Bukkit.getLogger().warning("Tried to unwrap a value that is not a player!");
        return null;
    }

    @Override
    public Class getType() {
        return Player.class;
    }
}
