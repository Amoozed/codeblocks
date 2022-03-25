package org.amusedd.codeblocks.blocks.executables.containers;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.executables.ExecutableCodeBlock;
import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.blocks.value.VariableBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class CodeBlockContainer extends CodeBlock implements ExecutableCodeBlock {

    ArrayList<ExecutableCodeBlock> blocks;
    int blockIndex = 0;
    HashMap<String, VariableBlock> startingVariables = new HashMap<>();
    HashMap<String, VariableBlock> runtimeVariables = new HashMap<>();

    public CodeBlockContainer(Map<String, Object> map) {
        this((ArrayList<ExecutableCodeBlock>) map.get("blocks"), (HashMap<String, VariableBlock>) map.get("startingVariables"));
    }

    public CodeBlockContainer(ArrayList<ExecutableCodeBlock> blocks, HashMap<String, VariableBlock> startingVariables) {
        this.blocks = blocks;
        for(ExecutableCodeBlock block : blocks){
            block.setContainer(this);
        }
        this.startingVariables = startingVariables;
    }

    public CodeBlockContainer(){
        this(new ArrayList<>(), new HashMap<>());
    }

    public void add(ExecutableCodeBlock block) {
        blocks.add(block);
    }

    public void remove(ExecutableCodeBlock block) {
        blocks.remove(block);
    }

    public void remove(int index) {
        blocks.remove(index);
    }

    public ExecutableCodeBlock get(int index) {
        return blocks.get(index);
    }

    public int size() {
        return blocks.size();
    }

    @Override
    public boolean run() {
        blockIndex = -1;
        nextBlock();
        return false;
    }

    public void nextBlock(){
        blockIndex++;
        if(blockIndex < size()){
            get(blockIndex).execute();
        } else{
            blockIndex = -1;
            finishExecution();
        }
    }

    @Override
    public void finishExecution() {
        getVariables().forEach(VariableBlock::reset);
        ExecutableCodeBlock.super.finishExecution();
    }

    public ArrayList<ExecutableCodeBlock> getBlocks(){
        return new ArrayList<>(blocks);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("blocks", blocks);
        map.put("startingVariables", startingVariables);
        return map;
    }

    public void addVariable(String name, VariableBlock value, boolean retain){
        if(retain) startingVariables.put(name, value);
        else runtimeVariables.put(name, value);
    }

    ArrayList<VariableBlock> getVariables(){
        ArrayList<VariableBlock> list = new ArrayList<>(startingVariables.values());
        list.addAll(runtimeVariables.values());
        return list;
    }

    public ArrayList<VariableBlock> getAllVariables(){
        ArrayList<VariableBlock> list = new ArrayList<>(getVariables());
        CodeBlockContainer container = getContainer();
        while(container != null){
            list.addAll(container.getVariables());
            container = container.getContainer();
        }
        return list;
    }
}
