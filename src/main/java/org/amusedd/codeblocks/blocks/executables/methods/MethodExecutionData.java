package org.amusedd.codeblocks.blocks.executables.methods;

import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.value.VariableBlock;

public class MethodExecutionData {
    CodeBlockContainer container;
    Object[] args;
    Object source;

    public MethodExecutionData(CodeBlockContainer container, Object[] args, Object source) {
        this.container = container;
        this.args = args;
        this.source = source;
    }

    public CodeBlockContainer getContainer() {
        return container;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object getSource() {
        return source;
    }

    public Object getArg(int index) {
        return args[index];
    }
}
