package org.amusedd.codeblocks.blocks.util.loop;

import org.amusedd.codeblocks.blocks.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.ValueBlock;

import java.util.HashMap;
import java.util.Map;

public class NumericLoop extends CodeBlockContainer {
    HashMap<String, ValueBlock<Object>> variableScope = new HashMap<>();
    ValueBlock<Integer> amount;
    int iterations;
    String name;

    public NumericLoop(String name) {
        super(name);
    }

    @Override
    public HashMap<String, ValueBlock<Object>> getVariableScope() {
        return variableScope;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("amount", amount);
        return data;
    }



    @Override
    public void nextBlock() {
        if(blockIndex >= codeBlocks.size()) {
            iterations++;
            if(iterations < amount.get()) {
                blockIndex = 0;
                super.nextBlock();
            } else{
                getContainer().nextBlock();
            }
        } else {
            super.nextBlock();
        }
    }
}
