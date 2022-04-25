package org.amusedd.codeblocks.util.values;

import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.bukkit.inventory.ItemStack;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

public class ValueWrapper {
    HashMap<Class<?>, Extension<?>> wrappers;
    public ValueWrapper(){
        wrappers = new HashMap<Class<?>, Extension<?>>();
        for (Class<? extends Extension> clazz : new Reflections("org.amusedd.codeblocks.util.values.wrappers").getSubTypesOf(Extension.class)) {
            try {
                Extension<?> extension = clazz.getDeclaredConstructor().newInstance();
                addWrapper(extension.getExtending(), extension);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean hasSpecifiedSet(Class<?> type){
        return(type != null && getExtension(type) != null && (getExtension(type).getSetAsStrings() != null || getExtension(type).getSetAsItems() != null || getExtension(type).getSetAsRaw() != null || getExtension(type).getSetAsMatch() != null));
    }

    public ArrayList<String> getSetAsStrings(Class<?> type){
        Extension<?> extension = getExtension(type);
        if(extension != null){
            return extension.getSetAsStrings();
        }
        return null;
    }

    public ArrayList<ItemStack> getSetAsItems(Class<?> type){
        Extension<?> extension = getExtension(type);
        if(extension != null){
            return extension.getSetAsItems();
        }
        return null;
    }

    public ArrayList<Object> getSetAsRaw(Class<?> type){
        Extension<?> extension = getExtension(type);
        if(extension != null){
            return (ArrayList<Object>) extension.getSetAsRaw();
        }
        return null;
    }

    public HashMap<Object, ItemStack> getSetAsMatch(Class<?> type){
        Extension<?> extension = getExtension(type);
        if(extension != null){
            return (HashMap<Object, ItemStack>) extension.getSetAsMatch();
        }
        return null;
    }

    public void addWrapper(Class<?> type, Extension<?> extension){
        wrappers.put(type, extension);
    }

    public Extension<?> getExtension(Class<?> type){
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
        Extension<?> extension = getExtension(type);
        if(extension != null && extension.isOfType(value)){
            return extension.overrideToString(value);
        }
        return value.toString();
    }

    public Object getUnwrapFromValueBlock(Class<?> type, ValueBlock valueBlock){
        if(valueBlock == null) return null;
        Extension<?> extension = getExtension(type);
        if(extension != null && extension.isOfType(valueBlock)){
            return extension.fromValueBlock(valueBlock);
        }
        return null;
    }

    public ValueBlock getWrapToValueBlock(Class<?> type, Object value){
        if(value == null) return null;
        Extension<?> extension = getExtension(type);
        if(extension != null && extension.isOfType(value)){
            return extension.toValueBlock(value);
        }
        return null;
    }

    public Object getUnwrapFromString(Class<?> type, String value){
        if(value == null) return null;
        Extension<?> extension = getExtension(type);
        if(extension != null && extension.isOfType(value)){
            return extension.fromString(value);
        }
        return null;
    }

}
