package org.amusedd.codeblocks.blocks.util.loop;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.blocks.util.ConditionalBlock;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConditionalLoop extends CodeBlockContainer {
    private ConditionalBlock conditionalBlock;

    public ConditionalLoop(String name, ArrayList<CodeBlock> codeBlocks) {
        super(name, codeBlocks);
    }

    @Override
    public HashMap<String, ValueBlock> getVariableScope() {
        return null;
    }

    @Override
    public void nextBlock() {
        if(blockIndex >= codeBlocks.size()) {
            if(!getConditionalBlock().evaluate()) {
                blockIndex = 0;
                super.nextBlock();
            } else{
                getContainer().nextBlock();
            }
        } else {
            super.nextBlock();
        }
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.CHAIN).setName("Conditional Loop").build();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("conditionalBlock", getConditionalBlock());
        return data;
    }

    public static ConditionalLoop deserialize(Map<String, Object> data) {
        ConditionalBlock conditionalBlock = (ConditionalBlock) data.get("conditionalBlock");
        LinkedHashMap lmap = (LinkedHashMap) data.get("blocks");
        ArrayList<CodeBlock> codeBlocks = new ArrayList<>();
        for (Object o : lmap.values()) {
            codeBlocks.add((CodeBlock) o);
        }
        ItemStack item = (ItemStack) data.get("block");
        String name = (String) item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(CodeBlocksPlugin.getInstance(), "name"), PersistentDataType.STRING);
        ConditionalLoop fin = new ConditionalLoop(name, codeBlocks);
        fin.setConditionalBlock(conditionalBlock);
        return fin;
    }

    public ConditionalBlock getConditionalBlock() {
        return conditionalBlock;
    }

    public void setConditionalBlock(ConditionalBlock conditionalBlock) {
        this.conditionalBlock = conditionalBlock;
    }
}
