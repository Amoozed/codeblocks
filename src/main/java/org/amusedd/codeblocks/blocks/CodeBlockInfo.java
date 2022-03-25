package org.amusedd.codeblocks.blocks;

import org.bukkit.Material;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CodeBlockInfo {
    String viewName();
    Material viewMaterial() default Material.STONE;
    String[] description() default {};
    String category() default "miscellaneous";
    boolean creatable() default true;
}
