package org.amusedd.codeblocks.blocks.executables.methods;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.RetrievableValue;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.value.VariableBlock;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class RunnableMethod implements ConfigurationSerializable, RetrievableValue {
    String methodName;
    Class<?>[] parameterTypes;
    Class<?> returnType;
    Function<MethodExecutionData, Object> methodExecutor;
    boolean staticMethod;
    List<String> description;
    String id;
    Class<?> clazz;

    public RunnableMethod(String methodName, Class<?> clazz, Class<?> returnType, Class<?>[] parameterTypes, boolean staticMethod, Function<MethodExecutionData, Object> methodExecutor) {
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
        this.methodExecutor = methodExecutor;
        this.staticMethod = staticMethod;
        this.clazz = clazz;
        String id = clazz.getName() + "-" + methodName;
        if(parameterTypes != null) {
            for (Class<?> parameterType : parameterTypes) {
                id += "-" + parameterType.getSimpleName();
            }
        }
        this.id = id;
    }

    public RunnableMethod(String methodName, Class<?> clazz, Class<?> returnType, Class<?>[] parameterTypes, Function<MethodExecutionData, Object> methodExecutor) {
        this(methodName, clazz, returnType, parameterTypes, false, methodExecutor);
    }

    public RunnableMethod(String methodName, Class<?> clazz, Class<?> returnType , Class<?>[] parameterTypes, List<String> description,  Function<MethodExecutionData, Object> methodExecutor){
        this(methodName, clazz, returnType, parameterTypes, description, false, methodExecutor);
    }

    public RunnableMethod(String methodName, Class<?> clazz, Class<?> returnType , Class<?>[] parameterTypes, List<String> description, boolean staticMethod,  Function<MethodExecutionData, Object> methodExecutor){
        this(methodName, clazz, returnType, parameterTypes, staticMethod, methodExecutor);
        this.description = description;
    }

    public String getID(){
        return id;
    }

    public ItemStack getItem() {
        return new ItemBuilder(Material.BOW).setName(ChatColor.WHITE + methodName + "")
                .addLore(ChatColor.GREEN + "Method Call")
                .addLore(ChatColor.GRAY + "Return Type: " + ChatColor.GREEN + (getReturnType() == null ? "None" : getReturnType().getSimpleName()))
                .build();
    }

    public void call(VariableBlock variable, CodeBlockContainer container, Object[] args) {
        MethodExecutionData data = new MethodExecutionData(container, args, variable.getValue());
        if(getReturnType() != null) {
            Object returnValue = methodExecutor.apply(data);
            if(returnValue != null) variable.setValue(returnValue, false);
        } else {
            methodExecutor.apply(data);
        }
    }



    public boolean isStatic() {
        return staticMethod;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("id", getID());
        return data;
    }

    public static RunnableMethod deserialize(Map<String, Object> data) {
        return CodeBlocks.getAPI().getMethodByID((String) data.get("id"));
    }

    @Override
    public Object retrieveValue() {
        return null;
    }
}
