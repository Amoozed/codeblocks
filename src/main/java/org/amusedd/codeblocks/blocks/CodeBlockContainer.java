package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.blocks.executables.ExecutableCodeBlock;

import java.util.ArrayList;

public abstract class CodeBlockContainer extends CodeBlock implements ExecutableCodeBlock {
    ArrayList<ExecutableCodeBlock> blocks = new ArrayList<ExecutableCodeBlock>();
    int blockIndex = 0;

    public void add(ExecutableCodeBlock block) {
        blocks.add(block);
    }

    public void remove(ExecutableCodeBlock block) {
        blocks.remove(block);
    }

    public void remove(int index) {
        blocks.remove(index);
    }

    public ExecutableCodeBlock get(int index) {
        return blocks.get(index);
    }

    public int size() {
        return blocks.size();
    }

    @Override
    public boolean run() {
        blockIndex = -1;
        nextBlock();
        return false;
    }

    public void nextBlock(){
        blockIndex++;
        if(blockIndex < size()){
            get(blockIndex).run();
        } else{
            blockIndex = -1;
            finishExecution();
        }
    }
}
