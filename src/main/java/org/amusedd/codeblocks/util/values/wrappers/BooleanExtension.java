package org.amusedd.codeblocks.util.values.wrappers;

import org.amusedd.codeblocks.util.values.Extension;

public class BooleanExtension implements Extension<Boolean> {
    @Override
    public Boolean fromString(String s) {
        return Boolean.valueOf(s);
    }

    @Override
    public Class<?> getExtending() {
        return Boolean.class;
    }
}
