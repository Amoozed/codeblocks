package org.amusedd.codeblocks.blocks.util.loop;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;

public class NumericLoop extends CodeBlockContainer {
    HashMap<String, ValueBlock> variableScope = new HashMap<>();
    ValueBlock amount;
    int iterations;

    public NumericLoop(String name, ArrayList<CodeBlock> codeBlocks) {
        super(name, codeBlocks);
    }

    @Override
    public HashMap<String, ValueBlock> getVariableScope() {
        return variableScope;
    }


    public void setValueBlock(ValueBlock amount) {
        this.amount = amount;
    }

    public ValueBlock getValueBlock() {
        return amount;
    }

    @Override
    public ItemStack getBaseItem() {
        return null;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("amount", amount);
        return data;
    }


    public static NumericLoop deserialize(Map<String, Object> data) {
        LinkedHashMap lmap = (LinkedHashMap) data.get("blocks");
        ArrayList<CodeBlock> codeBlocks = new ArrayList<>();
        for (Object o : lmap.values()) {
            codeBlocks.add((CodeBlock) o);
        }
        ItemStack item = (ItemStack) data.get("block");
        String name = (String) item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(CodeBlocksPlugin.getInstance(), "name"), PersistentDataType.STRING);
        ValueBlock amount = (ValueBlock) data.get("amount");
        NumericLoop fin = new NumericLoop(name, codeBlocks);
        fin.setValueBlock(amount);
        return fin;
    }

    @Override
    public void nextBlock() {
        if(blockIndex >= codeBlocks.size()) {
            iterations++;
            if(iterations < (int)amount.getValue()) {
                blockIndex = 0;
                super.nextBlock();
            } else{
                getContainer().nextBlock();
            }
        } else {
            super.nextBlock();
        }
    }
}
