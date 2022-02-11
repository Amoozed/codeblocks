package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.plugin.CodeBlocksPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public abstract class CodeBlock implements ConfigurationSerializable {

    CodeBlockContainer container;

    public CodeBlock() {
        setTag("tag", getContainer().getName() + ":" + getContainer().idOf(this), PersistentDataType.STRING);
        setTag("type", getType(), PersistentDataType.STRING);
    }

    public String getType(){
        return getClass().getSimpleName();
    }

    public void execute(){
        if(getContainer() != null)
            getContainer().nextBlock();
    }

    public Map<String, Object> serialize(){
        Map<String, Object> map = new HashMap<>();
        map.put("codeblock", getGUIItem());
        return map;
    }

    public abstract ItemStack getGUIItem();

    public CodeBlockContainer getContainer(){
        return container;
    }

    public void setContainer(CodeBlockContainer container){
        this.container = container;
        this.container.addCodeBlock(this);
    }

    public void onResponse(String response){
        if(getContainer() != null)
            getContainer().nextBlock();
    }


    public void setTag(String key, Object value, PersistentDataType type){
        ItemMeta meta = getGUIItem().getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(CodeBlocksPlugin.getInstance(), key), type, value);
        getGUIItem().setItemMeta(meta);
    }
}
