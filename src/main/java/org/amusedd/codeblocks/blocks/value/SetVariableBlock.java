package org.amusedd.codeblocks.blocks.value;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.CodeBlockInfo;
import org.amusedd.codeblocks.blocks.executables.ExecutableCodeBlock;
import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.commands.input.communication.Conversation;
import org.amusedd.codeblocks.commands.input.communication.Receiver;
import org.amusedd.codeblocks.util.ViewData;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.amusedd.codeblocks.util.values.ValueBlockData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@CodeBlockInfo(viewName = "Set Variable", viewMaterial = Material.EMERALD_BLOCK, description = {"&7Sets a defined variable to a given value at runtime."})
public class SetVariableBlock extends CodeBlock implements ExecutableCodeBlock, ValueHolder, Receiver {
    ValueSetBlock valueSetBlock;
    CodeBlockContainer container;

    public SetVariableBlock(ValueSetBlock valueSetBlock) {
        this.valueSetBlock = valueSetBlock;
        getValueSet().setChangeCallback(this);
    }

    public SetVariableBlock(){
        HashMap<String, ValueBlock> valueBlocks = new HashMap<String, ValueBlock>();
        valueBlocks.put("variable", new ValueBlock(new ValueBlockData(new ViewData("Name of Variable", Material.DIAMOND_BLOCK), VariableBlock.class), null));
        valueBlocks.put("value", new ValueBlock(new ValueBlockData(new ViewData("Value", Material.DIAMOND), Object.class, false), null));
        this.valueSetBlock = new ValueSetBlock(valueBlocks);
        getValueSet().setChangeCallback(this);
    }

    public VariableBlock getVariableBlock(){
        return (VariableBlock)valueSetBlock.get("variable").getCurrentValue();
    }

    public Object getValue(){
        return getValueBlock("value").getValue();
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.EMERALD_BLOCK).setName("Set Variable").build();
    }

    @Override
    public boolean run() {
        VariableBlock variableBlock = getVariableBlock();
        variableBlock.setValue(getValue(), false);
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
    public ValueSetBlock getValueSet() {
        return valueSetBlock;
    }

    public static SetVariableBlock deserialize(Map<String, Object> data){
        return new SetVariableBlock((ValueSetBlock) data.get("valueset"));
    }

    @Override
    public void onValueBlockEdit(Conversation conversation, ValueBlock valueBlock) {
        System.out.println("ValueBlockEdit");
        if(valueBlock.equals(getValueBlock("variable"))){
            System.out.println("farvk");
            getValueBlock("value").getData().setType(getVariableBlock().getVariableType());
        }
        conversation.complete();
    }
}
