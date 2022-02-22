package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.gui.ContainerEditGUI;
import org.amusedd.codeblocks.gui.CreateWithVariablesGUI;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.values.ValueBlockData;
import org.amusedd.codeblocks.values.ValueSet;
import org.amusedd.codeblocks.values.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public abstract class CodeBlockContainer extends CodeBlock {
    protected ArrayList<CodeBlock> codeBlocks = new ArrayList<>();
    HashMap<String, ValueBlock> variablesInScope = new HashMap<>();
    int blockIndex = 0;
    ValueSet set;



    public CodeBlockContainer(ValueSet set, ArrayList<CodeBlock> codeBlocks) {
        if(codeBlocks != null){
            for (CodeBlock codeBlock : codeBlocks) {
                if(codeBlock == null) continue;
                codeBlock.setContainer(this);
            }
            this.codeBlocks = codeBlocks;
        }
        this.set = set;
        System.out.println("FINGER FREDDY: " + codeBlocks);
        //if(name != null) getValueSet().getValueBlock("name").getData().setValue(name.getValue());
        setTag("name", set.getValueBlock("name").getData().getValue(), PersistentDataType.STRING);
        ItemMeta meta = getItem().getItemMeta();
        meta.setDisplayName((String) set.getValueBlock("name").getData().getValue());
        getItem().setItemMeta(meta);
    }

    public CodeBlockContainer(ValueSet name) {
        this(name, new ArrayList<>());
    }

    public CodeBlockContainer(){

    }

    @Override
    public void run() {
        blockIndex = -1;
        nextBlock();
    }

    public void nextBlock(){
        blockIndex++;
        if(blockIndex < codeBlocks.size()){
            CodeBlock block = codeBlocks.get(blockIndex);
            block.run();
        } else {
            onContainerFinish();
        }
    }

    public ArrayList<CodeBlock> getCodeBlocks(){
        return codeBlocks;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("blocks", codeBlocks);
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


    public void onContainerFinish(){
        if(getContainer() != null) getContainer().nextBlock();
    }

    @Override
    public void onGUILeftClick(Player player, GUI gui, InventoryClickEvent event) {
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
        String name = (String)getValueSet().getValueBlock("name").getData().getValue();
        return (name == null) ? "Undefined Name" : name;
    }

    @Override
    public void onVariableCreation(Player player, CreateWithVariablesGUI gui, InventoryClickEvent event) {
        ItemMeta meta = getItem().getItemMeta();
        meta.setDisplayName((String) getValueSet().getValueBlock("name").getData().getValue());
        getItem().setItemMeta(meta);
    }



    public int indexOf(CodeBlock codeBlock){
        return codeBlocks.indexOf(codeBlock);
    }

    @Override
    public void onGUIRightClick(Player player, GUI gui, InventoryClickEvent event) {
        run();
    }

    @Override
    public CodeBlockContainer getContainer() {
        return super.getContainer();
    }

    @Override
    public ValueSet getValueSet() {
        if(set == null) {
            set = new ValueSet();
            set.addValueBlock("name", new ValueBlock(new ValueBlockData(Material.NAME_TAG, "Name", ValueType.STRING, null)));
        }
        return set;
    }


}
