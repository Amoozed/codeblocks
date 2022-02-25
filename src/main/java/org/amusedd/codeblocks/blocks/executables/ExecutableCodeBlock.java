package org.amusedd.codeblocks.blocks.executables;

import org.amusedd.codeblocks.blocks.CodeBlockContainer;

public interface ExecutableCodeBlock {
    default void execute(){
        if(run()){
            finishExecution();
        }
    }

    boolean run();
    CodeBlockContainer getContainer();
    void setContainer(CodeBlockContainer container);
    default void finishExecution(){
        if(getContainer() != null){
            getContainer().nextBlock();
        }
    }
}
