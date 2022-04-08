package org.amusedd.codeblocks.util.values;

import org.amusedd.codeblocks.blocks.executables.methods.RunnableMethod;
import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.bukkit.Bukkit;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class ValueWrapper {
    HashMap<Class, Wrapper> wrappers;
    HashMap<Class, SpecifiedSet> sets;
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
        sets = new HashMap<Class, SpecifiedSet>();
        for (Class<? extends SpecifiedSet> clazz : new Reflections("org.amusedd.codeblocks.util.values.sets").getSubTypesOf(SpecifiedSet.class)) {
            try {
                SpecifiedSet set = clazz.getDeclaredConstructor().newInstance();
                sets.put(set.getType(), set);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    Class getClass(Object value){
        if(wrappers.containsKey(value.getClass())){
            return wrappers.get(value.getClass()).getType();
        } else {
            for (Class clazz : wrappers.keySet()) {
                if (clazz.isAssignableFrom(value.getClass())) {
                    return getWrapper(clazz).getType();
                }
            }
        }
        return Object.class;
    }

    public ValueBlock getWrappedValue(Class type, Object value){
        Wrapper wrapper = getWrapper(type);
        if(wrapper == null) wrapper = anyWrapper;
        if(wrapper.isOfType(value)){
            return (value instanceof String) ? wrapper.wrap((String) value) : wrapper.wrap(value);
        } else {
            Bukkit.getLogger().warning("Wrapper: Value " + value.toString() + " is not of type " + type.getSimpleName());
            return null;
        }
    }

    public ValueBlock getWrappedValue(Object value){
        Class clazz = getClass(value);
        if(clazz != null){
            return getWrappedValue(clazz, value);
        } else {
            Bukkit.getLogger().warning("WrapperValueBlock: No wrapper found for class " + value.getClass().getName());
            return null;
        }
    }

    public Object getUnwrappedValue(ValueBlock value, Object currentValue){
        Wrapper wrapper = getWrapper(value.getValueType());
        if(wrapper.isOfType(value) && wrapper.isWrappedValue(currentValue)){
            return wrapper.unwrap(value);
        }
        Bukkit.getLogger().warning("Unwrapper: Value " + value.toString() + " is not of type " + value.getData().getType().getSimpleName());
        return null;
    }

    public boolean hasWrapper(Class type){
        return getWrapper(type) != null;
    }

    public String unwrapToString(ValueBlock value){
        Wrapper wrapper = getWrapper(value.getValueType());
        if(wrapper.isOfType(value)){
            return wrapper.unwrapToString(value);
        }
        Bukkit.getLogger().warning("Value " + value + " is not of type " + value.getData().getType().getSimpleName());
        return null;
    }

    Wrapper getWrapper(Class type){
        if(wrappers.containsKey(type)){
            return wrappers.get(type);
        } else{
            for (Class clazz : wrappers.keySet()) {
                if (clazz.isAssignableFrom(type)) {
                    return wrappers.get(clazz);
                }
            }
            return anyWrapper;
        }
    }

    public SpecifiedSet getSet(Class type){
        return sets.get(type);
    }

}
