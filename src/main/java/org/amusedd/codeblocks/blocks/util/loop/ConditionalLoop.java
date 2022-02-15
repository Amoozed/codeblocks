package org.amusedd.codeblocks.blocks.util.loop;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.blocks.functions.FunctionBlock;
import org.amusedd.codeblocks.blocks.util.ConditionalBlock;
import org.amusedd.codeblocks.gui.ConditionalGUI;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConditionalLoop extends CodeBlockContainer {
    private ConditionalBlock conditionalBlock;

    private FunctionBlock trueFunction;
    private FunctionBlock falseFunction;


    public ConditionalLoop(String name, LinkedHashMap codeBlocks, ConditionalBlock conditionalBlock, FunctionBlock trueFunction, FunctionBlock falseFunction) {
        super(name, codeBlocks);
        this.conditionalBlock = conditionalBlock;
        this.trueFunction = trueFunction;
        this.falseFunction = falseFunction;
        item = new ItemBuilder(Material.CHAIN).addLore(ChatColor.GREEN + "Right Click to Edit Required Values").build();
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
    public boolean canRun() {
        return super.canRun() && conditionalBlock.canRun();
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
        ItemStack item = (ItemStack) data.get("block");
        String name = (String) item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(CodeBlocksPlugin.getInstance(), "name"), PersistentDataType.STRING);
        FunctionBlock trueFunction = (FunctionBlock) data.get("trueFunction");
        FunctionBlock falseFunction = (FunctionBlock) data.get("falseFunction");
        ConditionalLoop fin = new ConditionalLoop(name, lmap, conditionalBlock, trueFunction, falseFunction);
        return fin;
    }

    @Override
    public void onGUIRightClick(Player player, GUI gui) {
        conditionalBlock.onGUIRightClick(player, gui);
    }

    @Override
    public void onGUILeftClick(Player player, GUI gui) {
        new ConditionalGUI(player, this).open();
    }

    public ConditionalBlock getConditionalBlock() {
        return conditionalBlock;
    }

    public void setConditionalBlock(ConditionalBlock conditionalBlock) {
        this.conditionalBlock = conditionalBlock;
    }

    public FunctionBlock getTrueFunction() {
        return trueFunction;
    }

    public FunctionBlock getFalseFunction() {
        return falseFunction;
    }
}
