package org.amusedd.codeblocks.util.values.wrappers;

import org.amusedd.codeblocks.blocks.executables.methods.CodeBlockMethod;
import org.amusedd.codeblocks.util.values.Extension;
import org.bukkit.Material;

public class IntegerExtension implements Extension<Integer> {

    @Override
    public Integer fromString(String s) {
        try{
            return Integer.parseInt(s);
        } catch (NumberFormatException e){
            return null;
        }
    }

    @Override
    public Class<?> getExtending() {
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

    @CodeBlockMethod(viewName = "Increase by amount", viewMaterial = Material.DIAMOND)
    public Integer increase(Integer i, Integer amount){
        return i + amount;
    }
}
