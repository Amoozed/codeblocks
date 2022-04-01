package org.amusedd.codeblocks;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.CodeBlockInfo;
import org.amusedd.codeblocks.blocks.executables.ExecutableCodeBlock;
import org.amusedd.codeblocks.blocks.executables.methods.RunnableMethod;
import org.amusedd.codeblocks.util.ViewData;
import org.amusedd.codeblocks.util.items.PageableItem;
import org.amusedd.codeblocks.util.values.TypeData;
import org.amusedd.codeblocks.util.values.sets.TypeSelection;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeBlocksAPI {
    HashMap<Class<? extends CodeBlock>, ViewData> blockPreviews = new HashMap<>();
    HashMap<String, ViewData> categoryPreviews = new HashMap<>();
    HashMap<Class<?>, ArrayList<RunnableMethod>> methods = new HashMap<>();
    HashMap<Class<?>, ArrayList<RunnableMethod>> autoGenerateMethods = new HashMap<>();
    protected CodeBlocksAPI() {
        initalizeBaseBlocks();
        // String, Integer, Double, Boolean, Event
        registerType(new TypeData(String.class, Material.PAPER, List.of(ChatColor.GRAY + "Represents a value that can contain letters and numbers")));
        registerType(new TypeData(Integer.class, Material.BOWL, List.of(ChatColor.GRAY +"Represents a value that can contain whole numbers")));
        registerType(new TypeData(Double.class, Material.RABBIT_STEW, List.of(ChatColor.GRAY + "Represents a value that can contain decimal numbers")));
        registerType(new TypeData(Boolean.class, Material.COMPARATOR, List.of(ChatColor.GRAY + "Represents a value (conditional) that can be true or false")));
        registerType(new TypeData(Event.class, Material.BEACON, List.of(ChatColor.GRAY + "Represents an event that can be triggered")));
        categoryPreviews.put("miscellaneous", new ViewData("Miscellaneous", Material.TNT, List.of(ChatColor.GRAY + "Miscellaneous/Uncategorized CodeBlocks")));
        methods.put(Integer.class, new ArrayList<>(List.of(
                new RunnableMethod("Addition", Integer.class, new Class[]{Integer.class}, data -> (int)data.getArg(0) + (int) data.getSource()),
                new RunnableMethod("Subtraction", Integer.class, new Class[]{Integer.class}, data -> (int)data.getSource() - (int) data.getArg(0)),
                new RunnableMethod("Multiplication", Integer.class, new Class[]{Integer.class}, data -> (int)data.getSource() * (int) data.getArg(0)),
                new RunnableMethod("Division", Integer.class, new Class[]{Integer.class}, data -> (int)data.getSource() / (int) data.getArg(0))
        )));
        methods.put(Double.class, new ArrayList<>(List.of(
                new RunnableMethod("Addition", Double.class, new Class[]{Double.class}, data -> (double)data.getArg(0) + (double) data.getSource()),
                new RunnableMethod("Subtraction", Double.class, new Class[]{Double.class}, data -> (double)data.getSource() - (double) data.getArg(0)),
                new RunnableMethod("Multiplication", Double.class, new Class[]{Double.class}, data -> (double)data.getSource() * (double) data.getArg(0)),
                new RunnableMethod("Division", Double.class, new Class[]{Double.class}, data -> (double)data.getSource() / (double) data.getArg(0))
        )));
        methods.put(String.class, new ArrayList<>(List.of(
                new RunnableMethod("Concatenation", String.class, new Class[]{String.class}, data -> data.getArg(0) + (String) data.getSource()),
                new RunnableMethod("Length", Integer.class, new Class[]{}, data -> ((String)data.getSource()).length())
        )));
        methods.put(Boolean.class, new ArrayList<>(List.of(
                new RunnableMethod("Not", Boolean.class, new Class[]{}, data -> !(boolean) data.getSource())
        )));

    }

    public void addRunnableMethod(Class<?> clazz, RunnableMethod method) {
        if(!methods.containsKey(clazz)) {
            methods.put(clazz, new ArrayList<>());
        }
        methods.get(clazz).add(method);
    }

    public void addAutoGenerateMethod(Class<?> clazz) {
        autoGenerateMethods.put(clazz, null);
    }

    boolean generateAtRuntime(Class<?> clazz) {
        if(autoGenerateMethods.containsKey(clazz)) return true;
        else {
            for(Class<?> c : autoGenerateMethods.keySet()) {
                if(c.isAssignableFrom(clazz)) return true;
            }
        }
        return false;
    }

    public ArrayList<RunnableMethod> getMethods(Class<?> clazz) {
        ArrayList<RunnableMethod> allMethods = methods.get(clazz);
        if(generateAtRuntime(clazz)) {
            for(Method method : clazz.getMethods()){
                String name = method.getName();
                Class[] params = method.getParameterTypes();
                Class returnType = method.getReturnType() == void.class ? null : method.getReturnType();
                addRunnableMethod(clazz, new RunnableMethod(name, returnType, params, data -> {
                        try {
                            return method.invoke(data.getSource(), data.getArgs());
                        } catch (InvocationTargetException | IllegalAccessException e) {
                            e.printStackTrace();
                            return null;
                        }
                }));
            }
            allMethods.addAll(autoGenerateMethods.get(clazz));
        }
        return allMethods;
    }

    public void registerCodeBlock(Class<? extends CodeBlock> codeBlockClass) {
        check(codeBlockClass);
    }

    void initalizeBaseBlocks() {
        for (Class<? extends CodeBlock> clazz : new Reflections("org.amusedd.codeblocks.blocks").getSubTypesOf(CodeBlock.class)) {
            check(clazz);
        }
    }

    void check(Class<? extends CodeBlock> clazz) {
        if(!Modifier.isAbstract(clazz.getModifiers()) && ExecutableCodeBlock.class.isAssignableFrom(clazz)){
            ViewData data;
            CodeBlockInfo info = clazz.getAnnotation(CodeBlockInfo.class);
            if(info != null){
                if(!info.creatable()) return;
                data = new ViewData(info.viewName(), info.viewMaterial(), List.of(info.description()));
            } else {
                CodeBlocks.getPlugin().getLogger().warning("CodeBlock " + clazz.getSimpleName() + " has no @CodeBlockInfo annotation, therefore it will have a default preview");
                data = new ViewData(clazz.getSimpleName(), Material.STONE, List.of(ChatColor.GRAY + "Preview for " + ChatColor.GREEN + clazz.getSimpleName(), ChatColor.RED + "TODO: Add @CodeBlockInfo annotation"));
            }
            if(ConfigurationSerializable.class.isAssignableFrom(clazz)){
                Method method;
                try{
                    method = clazz.getMethod("deserialize", Map.class);
                    blockPreviews.put(clazz, data);
                } catch (NoSuchMethodException e) {
                    CodeBlocks.getPlugin().getLogger().warning("Serializable CodeBlock " + clazz.getSimpleName() + " does not have a deserialize method and will not be able to be created.");
                }
            }
        }
    }

    public ArrayList<ItemStack> getPreviews(){
        HashMap<String, PageableItem> items = new HashMap<>();
        for (Class<? extends CodeBlock> clazz : blockPreviews.keySet()) {
            ViewData data = blockPreviews.get(clazz);
            ItemStack preview = data.getItem();
            String category = clazz.getAnnotation(CodeBlockInfo.class) != null ? clazz.getAnnotation(CodeBlockInfo.class).category() : "miscellaneous";
            ItemMeta meta = preview.getItemMeta();
            meta.getPersistentDataContainer().set(new NamespacedKey(CodeBlocks.getPlugin(), "type"), PersistentDataType.STRING, clazz.getName());
            preview.setItemMeta(meta);
            if(items.containsKey(category)){
                items.get(category).addItem(preview);
            } else {
                items.put(category, new PageableItem(getCategoryPreview(category).getItem(), new ArrayList<>(List.of(preview))));
            }

        }
        for(String category : items.keySet()) {
            if(category.contains("/")){
                String[] split = category.split("/");
                String root = split[0];
                String path = root;
                PageableItem parent = items.containsKey(root) ? items.get(root) : items.put(root, new PageableItem(getCategoryPreview(root).getItem(), new ArrayList<>()));
                for(int i = 1; i < split.length; i++){
                    String sub = split[i];
                    path += "/" + sub;
                    PageableItem subItem = items.containsKey(path) ? items.get(path) : new PageableItem(getCategoryPreview(path).getItem(), new ArrayList<>());
                    parent.addItem(subItem);
                    parent = subItem;
                }
                PageableItem last = (PageableItem) parent.getItems().get(parent.getItems().size() - 1);
                last.addItem(items.get(category));
                items.remove(category);
            }
        }
        ArrayList<ItemStack> result = new ArrayList<>(items.values());
        return result;
    }

    public void registerType(TypeData data){
        TypeSelection.addType(data.getType(), data);
    }
    public ViewData getCategoryPreview(String category){
        if(categoryPreviews.containsKey(category)) return categoryPreviews.get(category);
        ViewData data = new ViewData(category, Material.STONE, List.of(ChatColor.GRAY + "Preview for " + ChatColor.GREEN + category, ChatColor.RED + "TODO: Add Category Preview"));
        return categoryPreviews.put(category, data);
    }
    public void registerCategory(String category, ViewData data){
        categoryPreviews.put("addon/" + category, data);
    }

}
