package org.amusedd.codeblocks.util.values.wrappers;

import org.amusedd.codeblocks.util.values.Wrapper;

public class TypeWrapper implements Wrapper<Class<?>> {

    @Override
    public String overrideToString(Object o) {
        Class<?> clazz = (Class<?>) o;
        return clazz.getSimpleName();
    }

    @Override
    public Class<?> getWrapperType() {
        return Class.class;
    }

    @Override
    public Class<?> fromString(String s) {
        try{
            return Class.forName(s);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean isOfType(Object o) {
        if(o instanceof Class) {
            return true;
        } else if(o instanceof String) {
            return fromString((String) o) != null;
        }
        return false;
    }
}
