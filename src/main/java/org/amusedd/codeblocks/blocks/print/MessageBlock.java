package org.amusedd.codeblocks.blocks.print;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.input.ValueSet;
import org.amusedd.codeblocks.input.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public abstract class MessageBlock extends CodeBlock {
    ValueSet set;

    public MessageBlock(ValueBlock text) {
        if(text != null) getValueSet().getValueBlock("text").setValue(text.getValue());
    }

    public MessageBlock(){}

    @Override
    public ValueSet getValueSet() {
        if(set == null) {
            set = super.getValueSet();
            set.addValueBlock("text", new ValueBlock(ValueType.STRING));
        }
        return set;
    }

    @Override
    public boolean run() {
        String print = (String) getValueSet().getValueBlock("text").getValue();
        print(print);
        System.out.println("C");
        return true;
    }

    protected abstract void print(String text);


    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.PAPER).setName("Message Block").build();
    }

    @Override
    public void onGUIRightClick(Player player, GUI gui) {

    }

    @Override
    public void onGUILeftClick(Player player, GUI gui) {}

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = super.serialize();
        data.put("text", getValueSet().getValueBlock("text"));
        return data;
    }
}
