package org.amusedd.codeblocks.blocks.executables.methods;

import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.value.VariableBlock;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class RunnableMethod {
    String methodName;
    Class<?>[] parameterTypes;
    Class<?> returnType;
    Function<MethodExecutionData, Object> methodExecutor;

    List<String> description;

    CodeBlockContainer container;

    public RunnableMethod(String methodName, Class<?> returnType, Class<?>[] parameterTypes , Function<MethodExecutionData, Object> methodExecutor) {
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.methodExecutor = methodExecutor;
    }

    public RunnableMethod(String methodName, Class<?> returnType , Class<?>[] parameterTypes, List<String> description, Function<MethodExecutionData, Object> methodExecutor){
        this(methodName, returnType, parameterTypes, methodExecutor);
        this.description = description;
    }


    public ItemStack getItem() {
        return new ItemBuilder(Material.BOW).setName(ChatColor.WHITE + methodName + "")
                .addLore(ChatColor.GREEN + "Method Call")
                .addLore(ChatColor.GRAY + "Return Type: " + ChatColor.GREEN + (returnType == null ? "None" : returnType.getSimpleName()))
                .build();
    }

    public void call(VariableBlock variable, CodeBlockContainer container, Object[] args) {
        MethodExecutionData data = new MethodExecutionData(container, args, variable.getValue());
        if(returnType != null) {
            Object returnValue = methodExecutor.apply(data);
            if(returnValue != null) variable.setValue(returnValue, false);
        } else {
            methodExecutor.apply(data);
        }
    }
}
