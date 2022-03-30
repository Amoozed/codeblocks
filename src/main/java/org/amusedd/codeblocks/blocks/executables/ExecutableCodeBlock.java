package org.amusedd.codeblocks.blocks.executables;

import org.amusedd.codeblocks.blocks.Viewable;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;

public interface ExecutableCodeBlock extends Viewable {
    default void execute(){
        if(run()){
            finishExecution();
        }
    }

    boolean run();
    CodeBlockContainer getContainer();
    default void addToContainer(CodeBlockContainer container){
        setContainer(container);
        container.add(this);
    }
    void setContainer(CodeBlockContainer container);
    default void finishExecution(){
        if(getContainer() != null){
            getContainer().nextBlock();
        }
    }
}
