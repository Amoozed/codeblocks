package org.amusedd.codeblocks.blocks.util.loop;

import org.amusedd.codeblocks.blocks.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.blocks.util.ConditionalBlock;

import java.util.HashMap;
import java.util.Map;

public class ConditionalLoop extends CodeBlockContainer {
    ConditionalBlock conditionalBlock;

    public ConditionalLoop(String name) {
        super(name);
    }

    @Override
    public HashMap<String, ValueBlock<Object>> getVariableScope() {
        return null;
    }

    @Override
    public void nextBlock() {
        if(blockIndex >= codeBlocks.size()) {
            if(!conditionalBlock.evaluate()) {
                blockIndex = 0;
                super.nextBlock();
            } else{
                getContainer().nextBlock();
            }
        } else {
            super.nextBlock();
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("conditionalBlock", conditionalBlock);
        return data;
    }

}
