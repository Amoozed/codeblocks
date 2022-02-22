package org.amusedd.codeblocks.values;

import org.amusedd.codeblocks.blocks.ValueBlock;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ValueSet implements ConfigurationSerializable {
    HashMap<String, ValueBlock> values;

    public ValueSet(){
        values = new HashMap<String, ValueBlock>();
    }

    public ValueSet(HashMap<String, ValueBlock> vs){
        values = vs;
    }

    public void addValueBlock(String name, ValueBlock vb){
        values.put(name, vb);
    }

    public boolean setValue(String name, Object value){
        ValueBlock vb = values.get(name);
        return vb.getData().setValue(value);
    }

    public ArrayList<ValueBlock> getValueBlocks(){
        return new ArrayList<ValueBlock>(values.values());
    }

    public ValueBlock getValueBlock(String name){
        ValueBlock vb = values.get(name);
        if(vb != null){
            return vb;
        }
        return null;
    }

    public boolean hasValues(){
        return !values.isEmpty();
    }

    public boolean isComplete(){
        for(ValueBlock vb : values.values()){
            if(!vb.hasValue()) return false;
        }
        return true;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, ValueBlock>  saveValues = new HashMap<String, ValueBlock>();
        for(String key : values.keySet()){
            saveValues.put(key, values.get(key));
        }
        map.put("values", saveValues);
        return map;
    }

    public static ValueSet deserialize(Map<String, Object> map){
        return new ValueSet((HashMap<String, ValueBlock>) map.get("values"));
    }
}