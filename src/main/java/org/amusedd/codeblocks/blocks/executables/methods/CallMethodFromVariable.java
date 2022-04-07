package org.amusedd.codeblocks.blocks.executables.methods;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.CodeBlockInfo;
import org.amusedd.codeblocks.blocks.RetrievableValue;
import org.amusedd.codeblocks.blocks.executables.ExecutableCodeBlock;
import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.value.DictionaryValueBlock;
import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.blocks.value.ValueSet;
import org.amusedd.codeblocks.blocks.value.VariableBlock;
import org.amusedd.codeblocks.commands.input.communication.Conversation;
import org.amusedd.codeblocks.commands.input.communication.Receiver;
import org.amusedd.codeblocks.util.ViewData;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CodeBlockInfo(viewName = "Call Method from Variable", viewMaterial = Material.PAPER)
public class CallMethodFromVariable extends CodeBlock implements ExecutableCodeBlock, ValueHolder, RetrievableValue, Receiver {
    ValueSet valueSet;
    CodeBlockContainer container;


    String variableName;
    String methodName;

    public CallMethodFromVariable(VariableBlock variableBlock, String id){
        this(variableBlock, CodeBlocks.getAPI().getMethodByID(variableBlock.getVariableType(), id));
        init();
    }

    public CallMethodFromVariable(VariableBlock variableBlock, RunnableMethod method){
        HashMap<String, ValueBlock> valueBlocks = new HashMap<>();
        // Variable, RunnableMethod
        valueBlocks.put("variable", new ValueBlock("Variable to Call From", Material.DIAMOND_BLOCK, VariableBlock.class, variableBlock));
        valueBlocks.put("method", new ValueBlock("Method to Call", Material.PAPER, RunnableMethod.class, method));
        valueBlocks.put("params", new DictionaryValueBlock("Parameters", Material.ENDER_CHEST, new ValueSet()));
        valueSet = new ValueSet(valueBlocks);
    }

    public CallMethodFromVariable(String variableName, String methodID){
        this.variableName = variableName;
        this.methodName = methodID;
        HashMap<String, ValueBlock> valueBlocks = new HashMap<>();
        // Variable, RunnableMethod
        valueBlocks.put("variable", new ValueBlock("Variable to Call From", Material.DIAMOND_BLOCK, VariableBlock.class, null));
        valueBlocks.put("method", new ValueBlock("Method to Call", Material.PAPER, RunnableMethod.class, null));
        valueBlocks.put("params", new DictionaryValueBlock("Parameters", Material.ENDER_CHEST, new ValueSet()));
        valueSet = new ValueSet(valueBlocks);
        init();
    }

    public CallMethodFromVariable(){
        HashMap<String, ValueBlock> valueBlocks = new HashMap<>();
        // Variable, RunnableMethod
        valueBlocks.put("variable", new ValueBlock("Variable to Call From", Material.DIAMOND_BLOCK, VariableBlock.class, null));
        valueBlocks.put("method", new ValueBlock("Method to Call", Material.PAPER, RunnableMethod.class, null));
        valueBlocks.put("params", new DictionaryValueBlock("Parameters", Material.ENDER_CHEST, new ValueSet()));
        valueSet = new ValueSet(valueBlocks);
    }

    @Override
    public Object retrieveValue() {
        run();
        return getVariable().getValue();
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.PAPER).setName(ChatColor.GREEN + "Call Method From Variable").addLore(ChatColor.GRAY + "Method: " + getMethod().getMethodName()).build();
    }

    @Override
    public boolean run() {
        Object[] params = new Object[getMethod().getParameterTypes().length];
        DictionaryValueBlock dictionaryValueBlock = (DictionaryValueBlock) valueSet.get("params");
        ValueSet set = dictionaryValueBlock.getValueSet();
        ArrayList<ValueBlock> valueBlocks = set.getValues();
        for(int i = 0; i < params.length; i++) {
            params[i] = valueBlocks.get(i).getValue();
        }
        getMethod().call(getVariable(), getContainer(), params);
        return true;
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
        init();
    }

    void init(){
        if(valueSet.get("variable").getValue() == null && variableName != null){
            valueSet.get("variable").setValue(getContainer().getVariable(variableName));
        }
        if(valueSet.get("method").getValue() == null && methodName != null){
            valueSet.get("method").setValue(CodeBlocks.getAPI().getMethodByID(((VariableBlock)valueSet.get("variable").getValue()).getVariableType(), methodName));
        }
    }

    public VariableBlock getVariable(){
        return (VariableBlock) getValueSet().get("variable").getValue();
    }

    public RunnableMethod getMethod(){
        return (RunnableMethod) getValueSet().get("method").getValue();
    }

    @Override
    public void onValueBlockEdit(Conversation conversation, ValueBlock valueBlock) {
        if(valueBlock.equals(getValueSet().get("method"))){
            RunnableMethod method = (RunnableMethod) valueBlock.getValue();
            HashMap<String, ValueBlock> valueBlocks = new HashMap<>();
            for(int i = 0; i < method.getParameterTypes().length; i++){
                Class<?> parameterType = method.getParameterTypes()[i];
                ViewData viewData = new ViewData("Parameter " + i, Material.BOW, List.of(ChatColor.GRAY + "Type: " + parameterType.getSimpleName()));
                valueBlocks.put("param" + i, new ValueBlock(viewData, Class.class, parameterType));
            }
            valueSet.get("params").setValue(new ValueSet(valueBlocks));
        }
    }
}
