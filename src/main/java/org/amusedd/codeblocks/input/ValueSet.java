package org.amusedd.codeblocks.input;

import org.amusedd.codeblocks.blocks.ValueBlock;

import java.util.ArrayList;
import java.util.List;

public class ValueSet {
    public ArrayList<ValueBlock> values;


    public ValueSet(ValueBlock[] values) {
        this.values = new ArrayList<ValueBlock>(List.of(values));
    }

    public ValueBlock getValue(int index) {
        return values.get(index);
    }

    public int getSize() {
        return values.size();
    }

    public ArrayList<ValueBlock> getValues() {
        return values;
    }

    public void addValue(ValueBlock value) {
        values.add(value);
    }

    public boolean isFull(){
        for(ValueBlock value : values){
            if(!value.canRun()) return false;
        }
        return true;
    }
}
