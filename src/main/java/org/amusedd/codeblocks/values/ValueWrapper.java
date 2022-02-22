package org.amusedd.codeblocks.values;

public abstract class ValueWrapper<T, U> {

    public abstract U getWrappedValue(T value);

    public abstract T getUnwrappedValue(U value);

    public abstract Class getType();

}
