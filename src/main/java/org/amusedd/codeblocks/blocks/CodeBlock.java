package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.amusedd.codeblocks.gui.CreateWithVariablesGUI;
import org.amusedd.codeblocks.gui.EditVariablesGUI;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.values.ValueSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CodeBlock implements ConfigurationSerializable{

    CodeBlockContainer container;
    protected ItemStack item;

    public CodeBlock() {
        //setTag("type", getType(), PersistentDataType.STRING);
    }


    public String getType(){
        return getClass().getSimpleName();
    }

    public void run(){
        getContainer().nextBlock();
    }


    public ItemStack getRefreshedItem(){
        refreshItem();
        return getItem();
    }

    public ItemStack getItem(){
        if(item == null){
            item = getBaseItem().clone();
        }
        return item;
    }

    public abstract ItemStack getBaseItem();

    public CodeBlockContainer getContainer(){
        return container;
    }


    public void setContainer(CodeBlockContainer container){
        this.container = container;
        this.container.addCodeBlock(this);
        setTag("id", getID(), PersistentDataType.STRING);
    }

    public void onResponse(String response){
        if(getContainer() != null)
            getContainer().nextBlock();
    }

    public final boolean isValid(){
        if(isRunnable()){
            return true;
        } else{
            Bukkit.getLogger().warning("Invalid block: " + getID() + " of " + container.getName());
            return false;
        }
    }

    public boolean isRunnable(){
        boolean isRunnable = getValueSet() == null || getValueSet().isComplete();
        System.out.println("Is runnable: " + isRunnable);
        return isRunnable;
    }

    public void setTag(String key, Object value, PersistentDataType type){
        ItemMeta meta = getItem().getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(CodeBlocksPlugin.getInstance(), key), type, value);
        getItem().setItemMeta(meta);
    }

    public Object getTag(String key, PersistentDataType type){
        ItemMeta meta = getItem().getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.get(new NamespacedKey(CodeBlocksPlugin.getInstance(), key), type);
    }

    public String getID() {
        return container.indexOf(this) + "";
    }


    public Map<String, Object> nonNBTData() {
        return null;
    }

    public Map<String, Object> serialize() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("valueset", getValueSet());
        return data;
    }

    public static CodeBlock deserialize(Map<String, Object> args) {
        return null;
    }

    public void onGUICreation(Player player, GUI gui){
        if(getValueSet() != null && getValueSet().hasValues()){
            new EditVariablesGUI(player, getValueSet()).open();
        }
    }

    public void onGUIRightClick(Player player, GUI gui, InventoryClickEvent event){
        if(getValueSet() != null && getValueSet().hasValues()){
            new EditVariablesGUI(player, getValueSet()).open();
        }
    }

    public void onGUILeftClick(Player player, GUI gui, InventoryClickEvent event){}

    public void onVariableCreation(Player player, CreateWithVariablesGUI gui, InventoryClickEvent event){}

    public ValueSet getValueSet(){
        return new ValueSet();
    }

    public void refreshItem(){
        List<String> lore = new ArrayList<>();
        for(ValueBlock valueBlock : getValueSet().getValueBlocks()){
            String key = valueBlock.getData().getName();
            String value = (valueBlock.getData().getValue() == null) ? "Undefined" : "" + valueBlock.getData().getValue();
            lore.add(ChatColor.GRAY + key + ": " + ChatColor.WHITE + ( (value == null) ? "Undefined" : value));
        }
        ItemMeta meta = getItem().getItemMeta();
        meta.setLore(lore);
        getItem().setItemMeta(meta);
    }
}
