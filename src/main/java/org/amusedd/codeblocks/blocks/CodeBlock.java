package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.amusedd.codeblocks.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public abstract class CodeBlock implements ConfigurationSerializable{

    CodeBlockContainer container;
    protected ItemStack item;


    public CodeBlock() {
        item = getBaseItem();
        setTag("type", getType(), PersistentDataType.STRING);
    }


    public String getType(){
        return getClass().getSimpleName();
    }

    public void execute(){
        if(getContainer() != null)
            getContainer().nextBlock();
    }

    public ItemStack getItem(){
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
        if(canRun()){
            return true;
        } else{
            Bukkit.getLogger().warning("Invalid block: " + getID() + " of " + container.getName());
            return false;
        }
    }

    public abstract boolean canRun();

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
        data.put("block", getItem());
        data.put("type", getType());
        return data;
    }

    public static CodeBlock deserialize(Map<String, Object> args) {
        return null;
    }

    public abstract void onGUIRightClick(Player player, GUI gui);

    public abstract void onGUILeftClick(Player player, GUI gui);
}
