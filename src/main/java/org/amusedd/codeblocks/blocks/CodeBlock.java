package org.amusedd.codeblocks.blocks;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Map;

public abstract class CodeBlock implements ConfigurationSerializable {
    @Override
    public Map<String, Object> serialize() {
        return null;
    }
}
