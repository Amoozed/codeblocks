package org.amusedd.codeblocks.blocks.print;

import org.amusedd.codeblocks.blocks.CodeBlock;

public abstract class MessageBlock extends CodeBlock {
    String text;

    public MessageBlock(String text) {
        this.text = text;
    }

    @Override
    public void execute() {
        print(text);
        super.execute();
    }

    protected abstract void print(String text);

}
