package org.amusedd.codeblocks;

import org.amusedd.codeblocks.util.values.ValueWrapper;

public class CodeBlocksUtility {
    ValueWrapper valueWrapper;

    protected CodeBlocksUtility() {
        valueWrapper = new ValueWrapper();
    }

    public ValueWrapper getValueWrapper() {
        return valueWrapper;
    }
}
