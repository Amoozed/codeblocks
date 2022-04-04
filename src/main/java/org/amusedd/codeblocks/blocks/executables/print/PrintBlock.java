package org.amusedd.codeblocks.blocks.executables.print;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.Viewable;
import org.amusedd.codeblocks.blocks.executables.ExecutableCodeBlock;
import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.blocks.value.ValueSet;
import org.bukkit.Material;

import java.util.HashMap;

public abstract class PrintBlock extends CodeBlock implements ValueHolder, ExecutableCodeBlock, Viewable {
    ValueSet valueSet;
    CodeBlockContainer codeBlockContainer;

    public PrintBlock(ValueSet valueSet) {
        this.valueSet = valueSet;
    }

    public PrintBlock(){
        HashMap<String, ValueBlock> valueBlocks = new HashMap<String, ValueBlock>();
        valueBlocks.put("text", new ValueBlock("Text to Print", Material.PAPER, String.class, null));
        this.valueSet = new ValueSet(valueBlocks);
    }

    @Override
    public ValueSet getValueSet() {
        return this.valueSet;
    }

    @Override
    public boolean run() {
        return print();
    }

    public abstract boolean print();

    @Override
    public CodeBlockContainer getContainer() {
        return codeBlockContainer;
    }

    @Override
    public void setContainer(CodeBlockContainer container) {
        this.codeBlockContainer = container;
    }
}
