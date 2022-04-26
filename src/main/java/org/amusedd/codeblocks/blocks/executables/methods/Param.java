package org.amusedd.codeblocks.blocks.executables.methods;

import org.bukkit.Material;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
    String viewName();
    Material viewMaterial() default Material.STONE;
    String[] description() default {};
    boolean required() default true;
    boolean self() default false;
}
