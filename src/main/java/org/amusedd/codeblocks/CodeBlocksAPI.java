package org.amusedd.codeblocks;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.CodeBlockInfo;
import org.amusedd.codeblocks.blocks.Viewable;
import org.amusedd.codeblocks.blocks.executables.ExecutableCodeBlock;
import org.amusedd.codeblocks.util.ViewData;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.amusedd.codeblocks.util.values.TypeData;
import org.amusedd.codeblocks.util.values.sets.TypeSelection;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
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
    protected CodeBlocksAPI() {
        initalizeBaseBlocks();
        // String, Integer, Double, Boolean, Event
        registerType(new TypeData(String.class, Material.PAPER, List.of(ChatColor.GRAY + "Represents a value that can contain letters and numbers")));
        registerType(new TypeData(Integer.class, Material.BOWL, List.of(ChatColor.GRAY +"Represents a value that can contain whole numbers")));
        registerType(new TypeData(Double.class, Material.RABBIT_STEW, List.of(ChatColor.GRAY + "Represents a value that can contain decimal numbers")));
        registerType(new TypeData(Boolean.class, Material.COMPARATOR, List.of(ChatColor.GRAY + "Represents a value (conditional) that can be true or false")));
        registerType(new TypeData(Event.class, Material.BEACON, List.of(ChatColor.GRAY + "Represents an event that can be triggered")));
        categoryPreviews.put("miscellaneous", new ViewData("Miscellaneous", Material.TNT, List.of(ChatColor.GRAY + "Miscellaneous/Uncategorized CodeBlocks")));
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
        HashMap<String, ArrayList<ItemStack>> items = new HashMap<>();
        for (Class<? extends CodeBlock> clazz : blockPreviews.keySet()) {
            ViewData data = blockPreviews.get(clazz);
            ItemStack preview = data.getItem();
            String category = clazz.getAnnotation(CodeBlockInfo.class).category();
            ItemMeta meta = preview.getItemMeta();
            meta.getPersistentDataContainer().set(new NamespacedKey(CodeBlocks.getPlugin(), "type"), PersistentDataType.STRING, clazz.getName());
            preview.setItemMeta(meta);
            if(items.containsKey(category)){
                items.get(category).add(preview);
            } else {
                ArrayList<ItemStack> list = new ArrayList<>();
                list.add(preview);
                items.put(category, list);
            }
        }
        for (String category : items.keySet()) {
            if(category.contains("/")){
                String[] split = category.split("/");
                for(String s : split){
                    if(items.containsKey(s)){
                        items.get(s).add(categoryPreviews.get(s).getItem());
                    } else {
                        ArrayList<ItemStack> list = new ArrayList<>();
                        list.add(categoryPreviews.get(s).getItem());
                        items.put(s, list);
                    }
                }
                items.remove(category);
            }
        }
        return items;
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
