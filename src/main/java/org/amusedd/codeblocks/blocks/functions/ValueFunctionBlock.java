package org.amusedd.codeblocks.blocks.functions;

import org.amusedd.codeblocks.blocks.ValueBlock;

import java.util.LinkedHashMap;
import java.util.Map;

public class ValueFunctionBlock extends FunctionBlock {

    ValueBlock value;
    public ValueFunctionBlock(ValueBlock name, LinkedHashMap map, ValueBlock value) {
        super(name, map);
    }

    public ValueBlock getValue() {
        return value;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("value", value.serialize());
        return data;
    }

    public static ValueFunctionBlock deserialize(Map<String, Object> data) {
        return new ValueFunctionBlock(
                (ValueBlock) data.get("name"),
                (LinkedHashMap) data.get("blocks"),
                (ValueBlock) data.get("value")
        );
    }
}
