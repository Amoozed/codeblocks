package org.amusedd.codeblocks.blocks.executables.methods;

import org.bukkit.Material;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CodeBlockMethod {
    String viewName() default "Unnamed Method";
    Material viewMaterial() default Material.STONE;
    String[] description() default {};
    String category() default "miscellaneous";
}