package org.amusedd.codeblocks.util.values;

import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.bukkit.Bukkit;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ValueWrapper {
    HashMap<Class, Wrapper> wrappers;
    Wrapper anyWrapper;
    public ValueWrapper(){
        wrappers = new HashMap<Class, Wrapper>();
        for (Class<? extends Wrapper> clazz : new Reflections("org.amusedd.codeblocks.util.values.wrappers").getSubTypesOf(Wrapper.class)) {
            try {
                Wrapper wrapper = clazz.getDeclaredConstructor().newInstance();
                wrappers.put(wrapper.getType(), wrapper);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        anyWrapper = new AnyWrapper();
    }

    public Class getClass(Object value){
        if(wrappers.containsKey(value.getClass())){
            return wrappers.get(value.getClass()).getType();
        } else {
            for (Class clazz : wrappers.keySet()) {
                if (clazz.isAssignableFrom(value.getClass())) {
                    return get(clazz).getType();
                }
            }
        }
        return Object.class;
    }

    public ValueBlock getWrappedValue(Class type, Object value){
        Wrapper wrapper = get(type);
        if(wrapper.isOfType(value)){
            return (value instanceof String) ? wrapper.wrap((String) value) : wrapper.wrap(value);
        } else {
            Bukkit.getLogger().warning("Value " + value.toString() + " is not of type " + type.getSimpleName());
            return null;
        }
    }

    public ValueBlock getWrappedValue(Object value){
        Class clazz = getClass(value);
        if(clazz != null){
            return getWrappedValue(clazz, value);
        } else {
            Bukkit.getLogger().warning("No wrapper found for class " + value.getClass().getName());
            return null;
        }
    }

    public Object getUnwrappedValue(ValueBlock value){
        Wrapper wrapper = wrappers.get(value.getData().getType());
        if(wrapper.isOfType(value)){
            return wrapper.unwrap(value);
        }
        Bukkit.getLogger().warning("Value " + value.toString() + " is not of type " + value.getData().getType().getSimpleName());
        return null;
    }

    Wrapper get(Class type){
        if(type.equals(Object.class)){
            return anyWrapper;
        }
        return wrappers.get(type);
    }

}
