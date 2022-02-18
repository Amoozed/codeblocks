package org.amusedd.codeblocks.blocks.functions;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.input.ValueSet;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FunctionBlock extends CodeBlockContainer {
    HashMap<String, ValueBlock> variableScope = new HashMap<>();

    public FunctionBlock(ValueBlock name, ArrayList<CodeBlock> codeBlocks)
    {
        super(name, codeBlocks);
    }

    public FunctionBlock() {

    }

    @Override
    public HashMap<String, ValueBlock> getVariableScope() {
        return variableScope;
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.COMMAND_BLOCK).setName(getName()).build();
    }

    @Override
    public Map<String, Object> serialize() {
        return super.serialize();
    }

    public static FunctionBlock deserialize(Map<String, Object> map) {
        ValueBlock name = (ValueBlock) map.get("name");
        return new FunctionBlock(name, (ArrayList<CodeBlock>) map.get("blocks"));
    }

    @Override
    public void onGUIRightClick(Player player, GUI gui) {
        execute();
    }

    public static ItemStack getPreview(){
        return new ItemBuilder(Material.COMMAND_BLOCK).setName("Function").build();
    }

    @Override
    public ValueSet getValueSet() {
        return super.getValueSet();
    }
}
