package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.gui.ContainerEditGUI;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.input.ValueSet;
import org.amusedd.codeblocks.input.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Cod;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public abstract class CodeBlockContainer extends CodeBlock {
    protected ArrayList<CodeBlock> codeBlocks = new ArrayList<>();
    HashMap<String, ValueBlock> variablesInScope = new HashMap<>();
    protected int blockIndex = -1;
    ValueSet set;


    public CodeBlockContainer(ValueBlock name, LinkedHashMap data) {
        if(data != null)
        for (Object o : data.values()) {
            CodeBlock block = (CodeBlock) o;
            block.setContainer(this);
            codeBlocks.add(block);
        }
        if(name != null) getValueSet().getValueBlock("name").setValue(name.getValue());
        setTag("name", name.getValue(), PersistentDataType.STRING);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName((String) name.getValue());
        item.setItemMeta(meta);
    }

    public CodeBlockContainer(ValueBlock name, ArrayList<CodeBlock> codeBlocks) {
        if(codeBlocks != null){
            for (CodeBlock codeBlock : codeBlocks) {
                if(codeBlock == null) continue;
                codeBlock.setContainer(this);
            }
            this.codeBlocks = codeBlocks;
        }
        System.out.println("FINGER FREDDY: " + codeBlocks);
        if(name != null) getValueSet().getValueBlock("name").setValue(name.getValue());
        setTag("name", name.getValue(), PersistentDataType.STRING);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName((String) name.getValue());
        item.setItemMeta(meta);
    }

    public CodeBlockContainer(ValueBlock name) {
        this(name, new ArrayList<>());
    }

    public CodeBlockContainer(){

    }

    @Override
    public void execute() {
        blockIndex = -1;
        System.out.println("A");
        nextBlock();
    }

    public ArrayList<CodeBlock> getCodeBlocks(){
        return codeBlocks;
    }

    @Override
    public boolean canRun() {
        return getValueSet().isComplete() && codeBlocks.size() > 0;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("blocks", codeBlocks);
        data.put("name", getValueSet().getValueBlock("name"));
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
        blockIndex++;
        if(blockIndex < codeBlocks.size()){
            CodeBlock block = codeBlocks.get(blockIndex);
            System.out.println("B");
            block.execute();
        } else if(getContainer() != null){
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
        return (String)getValueSet().getValueBlock("name").getValue();
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
        return super.getContainer();
    }

    @Override
    public ValueSet getValueSet() {
        if(set == null) {
            set = new ValueSet();
            set.addValueBlock("name", new ValueBlock(ValueType.STRING));
            return set;
        } else {
            return set;
        }
    }
}
