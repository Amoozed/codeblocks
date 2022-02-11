package org.amusedd.codeblocks.input;

import org.amusedd.codeblocks.blocks.CodeBlock;

public class ChatInput {
    CodeBlock codeBlock;
    String prompt;
    ValueType type;

    public ChatInput(CodeBlock codeBlock, String prompt, ValueType type) {
        this.codeBlock = codeBlock;
        this.prompt = prompt;
        this.type = type;
    }

    public CodeBlock getCodeBlock() {
        return codeBlock;
    }

    public String getPrompt() {
        return prompt;
    }

    public ValueType getType() {
        return type;
    }

    public void awaitResponse() {

    }
}

