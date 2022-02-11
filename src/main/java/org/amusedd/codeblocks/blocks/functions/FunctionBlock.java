package org.amusedd.codeblocks.blocks.functions;

import org.amusedd.codeblocks.blocks.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class FunctionBlock extends CodeBlockContainer {
    HashMap<String, ValueBlock<Object>> variableScope = new HashMap<>();
    String name;

    public FunctionBlock(String name) {
        super(name);
    }

    @Override
    public HashMap<String, ValueBlock<Object>> getVariableScope() {
        return variableScope;
    }

    @Override
    public ItemStack getGUIItem() {
        return new ItemBuilder(Material.COMMAND_BLOCK).setName(name).build();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setFunctionName(String name) {
        this.name = name;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("name", name);
        return data;
    }
}
