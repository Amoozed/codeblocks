package org.amusedd.codeblocks.util.values.wrappers;

import org.amusedd.codeblocks.util.values.Wrapper;

public class IntegerWrapper implements Wrapper<Integer> {

    @Override
    public Integer fromString(String s) {
        try{
            return Integer.parseInt(s);
        } catch (NumberFormatException e){
            return null;
        }
    }

    @Override
    public Class<?> getWrapperType() {
        return Integer.class;
    }

    @Override
    public boolean isOfType(Object o) {
        if(o instanceof Integer){
            return true;
        } else if(o instanceof String){
            return fromString((String) o) != null;
        }
        return false;
    }
}
