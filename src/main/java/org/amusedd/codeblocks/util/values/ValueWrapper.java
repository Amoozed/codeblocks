package org.amusedd.codeblocks.util.values;

import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ValueWrapper {
    HashMap<Class<?>, SpecifiedSet<?>> sets;
    HashMap<Class<?>, Wrapper<?>> wrappers;
    public ValueWrapper(){
        sets = new HashMap<Class<?>, SpecifiedSet<?>>();
        for (Class<? extends SpecifiedSet> clazz : new Reflections("org.amusedd.codeblocks.util.values.sets").getSubTypesOf(SpecifiedSet.class)) {
            try {
                SpecifiedSet<?> set = clazz.getDeclaredConstructor().newInstance();
                sets.put(set.getType(), set);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        wrappers = new HashMap<Class<?>, Wrapper<?>>();
        for (Class<? extends Wrapper> clazz : new Reflections("org.amusedd.codeblocks.util.values.wrappers").getSubTypesOf(Wrapper.class)) {
            try {
                Wrapper<?> wrapper = clazz.getDeclaredConstructor().newInstance();
                addWrapper(wrapper.getWrapperType(), wrapper);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public SpecifiedSet<?> getSet(Class<?> type){
        return sets.get(type);
    }

    public void addWrapper(Class<?> type, Wrapper<?> wrapper){
        wrappers.put(type, wrapper);
    }

    public Wrapper<?> getWrapper(Class<?> type){
        if(wrappers.containsKey(type)){
            return wrappers.get(type);
        } else {
            for (Class<?> clazz : wrappers.keySet()) {
                if(clazz.isAssignableFrom(type)){
                    return wrappers.get(clazz);
                }
            }
        }
        return null;
    }

    public String getWrapToString(Class<?> type, Object value){
        if(value == null) return null;
        Wrapper<?> wrapper = getWrapper(type);
        if(wrapper != null && wrapper.isOfType(value)){
            return wrapper.overrideToString(value);
        }
        return value.toString();
    }

    public Object getUnwrapFromValueBlock(Class<?> type, ValueBlock valueBlock){
        if(valueBlock == null) return null;
        Wrapper<?> wrapper = getWrapper(type);
        if(wrapper != null && wrapper.isOfType(valueBlock)){
            return wrapper.fromValueBlock(valueBlock);
        }
        return null;
    }

    public ValueBlock getWrapToValueBlock(Class<?> type, Object value){
        if(value == null) return null;
        Wrapper<?> wrapper = getWrapper(type);
        if(wrapper != null && wrapper.isOfType(value)){
            return wrapper.toValueBlock(value);
        }
        return null;
    }

    public Object getUnwrapFromString(Class<?> type, String value){
        if(value == null) return null;
        Wrapper<?> wrapper = getWrapper(type);
        if(wrapper != null && wrapper.isOfType(value)){
            return wrapper.fromString(value);
        }
        return null;
    }

}
