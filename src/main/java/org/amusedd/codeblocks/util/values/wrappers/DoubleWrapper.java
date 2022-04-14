package org.amusedd.codeblocks.util.values.wrappers;

import org.amusedd.codeblocks.util.values.Wrapper;

public class DoubleWrapper implements Wrapper<Double>{

    @Override
    public Double fromString(String s) {
        try{
            return Double.parseDouble(s);
        } catch (NumberFormatException e){
            return null;
        }
    }

    @Override
    public Class<?> getWrapperType() {
        return Double.class;
    }

    @Override
    public boolean isOfType(Object o) {
        if(o instanceof Double){
            return true;
        } else if(o instanceof String){
            return fromString((String) o) != null;
        }
        return false;
    }
}
