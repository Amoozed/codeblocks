package org.amusedd.codeblocks.blocks.executables.functions;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.blocks.value.ValueSetBlock;

public class StandaloneFunction extends CodeBlockContainer implements ValueHolder {

    public StandaloneFunction(String name, CodeBlock... blocks) {

    @Override
    public CodeBlockContainer getContainer() {
        return null;
    }

    @Override
    public void setContainer(CodeBlockContainer container) {}

    @Override
    public ValueSetBlock getValueSet() {
        return null;
    }
}
