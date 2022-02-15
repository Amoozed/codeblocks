package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.gui.ContainerEditGUI;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public abstract class CodeBlockContainer extends CodeBlock {
    protected ArrayList<CodeBlock> codeBlocks;
    HashMap<String, ValueBlock> variablesInScope = new HashMap<>();
    protected int blockIndex = 0;
    String name;


    public CodeBlockContainer(String name, LinkedHashMap data) {
        for (Object o : data.values()) {
            CodeBlock block = (CodeBlock) o;
            block.setContainer(this);
            codeBlocks.add(block);
        }
        this.name = name;
        setTag("name", name, PersistentDataType.STRING);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
    }

    public CodeBlockContainer(String name, ArrayList<CodeBlock> codeBlocks) {
        this.codeBlocks = codeBlocks;
        this.name = name;
        setTag("name", name, PersistentDataType.STRING);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
    }

    @Override
    public void execute() {
        blockIndex = 0;
        nextBlock();
    }

    public ArrayList<CodeBlock> getCodeBlocks(){
        return codeBlocks;
    }

    @Override
    public boolean canRun() {
        return name != null && !name.isEmpty();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        HashMap<String, Map<String, Object>> blocks = new HashMap<>();
        for(CodeBlock codeBlock : codeBlocks){
            Map<String, Object> codeBlocksData = codeBlock.serialize();
            blocks.put(indexOf(codeBlock) + "", codeBlocksData);
        }
        data.put("blocks", blocks);
        return data;
    }

    public CodeBlock getCodeBlock(int index){
        return codeBlocks.get(index);
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

    @Override
    public void onGUILeftClick(Player player, GUI gui) {
        new ContainerEditGUI(player,this).open();
    }

    public abstract HashMap<String, ValueBlock> getVariableScope();

    public void setVariableInScope(String variableName, ValueBlock variableValue){
        getVariableScope().put(variableName, variableValue);
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.CHEST).setName("Container").build();
    }

    public ValueBlock getValue(String variableName){
        if(getVariableScope().containsKey(variableName)){
            return getVariableScope().get(variableName);
        } else if(getContainer() != null){
            return getContainer().getValue(variableName);
        }
        return null;
    }

    public String getName(){
        System.out.println(name);
        return name;
    }

    public int indexOf(CodeBlock codeBlock){
        return codeBlocks.indexOf(codeBlock);
    }

    @Override
    public void onGUIRightClick(Player player, GUI gui) {
        execute();
    }

    @Override
    public CodeBlockContainer getContainer() {
        return (super.getContainer() == null) ? this : super.getContainer();
    }


}
