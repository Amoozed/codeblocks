package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public abstract class CodeBlock implements ConfigurationSerializable {
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        if(this instanceof ValueHolder){
            ValueHolder valueHolder = (ValueHolder) this;
            map.put("valueset", valueHolder.getValueSet());
        }
        return map;
    }

    public String getCodeBlockType(){
        return this.getClass().getSimpleName();
    }
    public CodeBlockInfo getInfo(){return getClass().getAnnotation(CodeBlockInfo.class);}
}
