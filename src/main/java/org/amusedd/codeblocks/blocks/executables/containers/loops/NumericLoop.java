package org.amusedd.codeblocks.blocks.executables.containers.loops;

import org.amusedd.codeblocks.blocks.CodeBlockInfo;
import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.blocks.value.ValueSet;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@CodeBlockInfo(viewName = "Numeric Loop", viewMaterial = Material.REPEATER, description = "&7Repeats all blocks a set amount of times")
public class NumericLoop extends CodeBlockContainer implements ValueHolder {

    CodeBlockContainer container;
    ValueSet values;
    int iterations = 0;

    public NumericLoop(ValueSet values, Map<String, Object> data) {
        super(data);
        this.values = values;
    }

    public NumericLoop(){
        HashMap<String, ValueBlock> map = new HashMap<>();
        map.put("amount", new ValueBlock("Loop Amount", Material.REPEATER, Integer.class, null));
        this.values = new ValueSet(map);
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
        return values;
    }

    @Override
    public void finishExecution() {
        iterations++;
        if(iterations >= getAmount()){
            iterations = 0;
            super.finishExecution();
        } else{
            execute();
        }
    }

    public int getAmount(){
        return Integer.parseInt(values.get("amount").getValue() + "");
    }

    public static NumericLoop deserialize(Map<String, Object> map){
        return new NumericLoop((ValueSet)map.get("valueset"), map);
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.REPEATER).setName(ChatColor.WHITE + "Numeric Loop").build();
    }
}
