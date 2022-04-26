package org.amusedd.codeblocks.util.values.wrappers;

import org.amusedd.codeblocks.blocks.executables.methods.CodeBlockMethod;
import org.amusedd.codeblocks.blocks.executables.methods.Param;
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

    // CodeBlockMethod for increasing an integer
    @CodeBlockMethod(id = "integerincrease", viewName = "Increase by amount", viewMaterial = Material.DIAMOND)
    public Integer increase(@Param(viewName = "Increasing Integer", viewMaterial = Material.DIAMOND, self = true) Integer i,
                            @Param(viewName = "Increase by", viewMaterial = Material.EMERALD) Integer amount){
        return i + amount;
    }

    // CodeBlockMethod for decreasing an integer
    @CodeBlockMethod(id = "integerdecrease", viewName = "Decrease by amount", viewMaterial = Material.REDSTONE)
    public Integer decrease(@Param(viewName = "Decreasing Integer", viewMaterial = Material.REDSTONE, self = true) Integer i,
                            @Param(viewName = "Decrease by", viewMaterial = Material.EMERALD) Integer amount){
        return i - amount;
    }

    // CodeBlockMethod for multiplying an integer
    @CodeBlockMethod(id = "integermultiply", viewName = "Multiply by amount", viewMaterial = Material.EMERALD)
    public Integer multiply(@Param(viewName = "Multiplying Integer", viewMaterial = Material.YELLOW_DYE, self = true) Integer i,
                            @Param(viewName = "Multiply by", viewMaterial = Material.EMERALD) Integer amount){
        return i * amount;
    }

    // CodeBlockMethod for dividing an integer
    @CodeBlockMethod(id = "integerdivide", viewName = "Divide by amount", viewMaterial = Material.EMERALD)
    public Integer divide(@Param(viewName = "Dividing Integer", viewMaterial = Material.LAPIS_LAZULI, self = true) Integer i,
                            @Param(viewName = "Divide by", viewMaterial = Material.EMERALD) Integer amount){
        return i / amount;
    }

    // CodeBlockMethod for modulo an integer
    @CodeBlockMethod(id = "integermodulo", viewName = "Modulo by amount", viewMaterial = Material.EMERALD)
    public Integer modulo(@Param(viewName = "Modulo Integer", viewMaterial = Material.BONE_MEAL, self = true) Integer i,
                            @Param(viewName = "Modulo by", viewMaterial = Material.EMERALD) Integer amount){
        return i % amount;
    }
}
