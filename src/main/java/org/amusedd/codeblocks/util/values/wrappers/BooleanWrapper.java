package org.amusedd.codeblocks.util.values.wrappers;

import org.amusedd.codeblocks.util.values.Wrapper;

public class BooleanWrapper implements Wrapper<Boolean> {
    @Override
    public Boolean fromString(String s) {
        return Boolean.valueOf(s);
    }

    @Override
    public Class<?> getWrapperType() {
        return Boolean.class;
    }
}
