package org.amusedd.codeblocks.blocks.executables.methods;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.CodeBlockInfo;
import org.amusedd.codeblocks.blocks.RetrievableValue;
import org.amusedd.codeblocks.blocks.executables.ExecutableCodeBlock;
import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.blocks.value.ValueSet;
import org.amusedd.codeblocks.blocks.value.VariableBlock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

@CodeBlockInfo(viewName = "Call Method from Variable", viewMaterial = Material.PAPER)
public class CallMethodFromVariable extends CodeBlock implements ExecutableCodeBlock, ValueHolder, RetrievableValue {
    ValueSet valueSet;
    CodeBlockContainer container;

    VariableBlock variable;
    RunnableMethod method;

    String variableName;
    String methodName;

    Object result;

    public CallMethodFromVariable(VariableBlock variableBlock, String id){
        this(variableBlock, CodeBlocks.getAPI().getMethodByID(variableBlock.getVariableType(), id));
    }

    public CallMethodFromVariable(VariableBlock variableBlock, RunnableMethod method){
        this.method = method;
        this.variable = variableBlock;
    }

    public CallMethodFromVariable(String variableName, String methodID){

    }

    public CallMethodFromVariable(){
        HashMap<String, ValueBlock> valueBlocks = new HashMap<>();
        // Variable, RunnableMethod
        valueBlocks.put("variable", new ValueBlock("Variable to Call From", Material.DIAMOND_BLOCK, VariableBlock.class, null));
        valueBlocks.put("method", new ValueBlock("Method to Call", Material.PAPER, RunnableMethod.class, null));
    }

    @Override
    public Object retrieveValue() {
        return null;
    }

    @Override
    public ItemStack getBaseItem() {
        return null;
    }

    @Override
    public boolean run() {
        return false;
    }

    @Override
    public CodeBlockContainer getContainer() {
        return container;
    }

    @Override
    public void setContainer(CodeBlockContainer container) {
        this.container = container;
    }

    @Override
    public ValueSet getValueSet() {
        return valueSet;
    }

    @Override
    public void onCreation(CodeBlockContainer container) {

    }

    public String getVariableName(){

    }
}
