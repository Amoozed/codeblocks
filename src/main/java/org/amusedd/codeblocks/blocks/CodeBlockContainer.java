package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CodeBlockContainer extends CodeBlock {
    protected ArrayList<CodeBlock> codeBlocks;
    HashMap<String, ValueBlock<Object>> variablesInScope = new HashMap<>();
    protected int blockIndex = 0;
    String name;

    public CodeBlockContainer(String name){
        this(name, new ArrayList<CodeBlock>());
    }

    ItemStack item;
    {
        item = new ItemBuilder(Material.CHEST).setName(getName()).build();
    }

    public CodeBlockContainer(String name, ArrayList<CodeBlock> codeBlocks){
        this.name = name;
        this.codeBlocks = codeBlocks;
    }


    @Override
    public void execute() {
        blockIndex = 0;
        nextBlock();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        ArrayList<ItemStack> blockItems = new ArrayList<>();
        for(CodeBlock codeBlock : codeBlocks){
            blockItems.add(codeBlock.getGUIItem());
        }
        data.put("blocks", blockItems);
        return data;
    }

    public void addCodeBlock(CodeBlock codeBlock){
        codeBlocks.add(codeBlock);
    }

    public void removeCodeBlock(CodeBlock codeBlock){
        codeBlocks.remove(codeBlock);
    }

    public void removeCodeBlock(int index){
        codeBlocks.remove(index);
    }

    public void nextBlock(){
        codeBlocks.get(blockIndex).execute();
        blockIndex++;
        if(blockIndex >= codeBlocks.size() && getContainer() != null){
            getContainer().nextBlock();
        }
    }


    public abstract HashMap<String, ValueBlock<Object>> getVariableScope();

    public void setVariableInScope(String variableName, ValueBlock<Object> variableValue){
        getVariableScope().put(variableName, variableValue);
    }

    @Override
    public ItemStack getGUIItem() {
        return item;
    }

    public ValueBlock<Object> getVariable(String variableName){
        if(getVariableScope().containsKey(variableName)){
            return getVariableScope().get(variableName);
        } else if(getContainer() != null){
            return getContainer().getVariable(variableName);
        }
        return null;
    }

    public String getName(){
        return name;
    }

    public int idOf(CodeBlock codeBlock){
        return codeBlocks.indexOf(codeBlock);
    }

}
