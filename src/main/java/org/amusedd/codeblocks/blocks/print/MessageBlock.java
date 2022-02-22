package org.amusedd.codeblocks.blocks.print;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.values.ValueBlockData;
import org.amusedd.codeblocks.values.ValueSet;
import org.amusedd.codeblocks.values.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class MessageBlock extends CodeBlock {
    ValueSet set;

    public MessageBlock(ValueSet set) {
        this.set = set;
    }

    public MessageBlock(){}

    @Override
    public ValueSet getValueSet() {
        if(set == null) {
            set = super.getValueSet();
            set.addValueBlock("text", new ValueBlock(new ValueBlockData(Material.PAPER, "Text", ValueType.STRING, null)));
        }
        return set;
    }

    @Override
    public void run() {
        String print = (String) getValueSet().getValueBlock("text").getData().getValue();
        print(print);
        System.out.println("C");
        super.run();
    }

    protected abstract void print(String text);


    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.PAPER).setName("Message Block").build();
    }

    @Override
    public void onGUILeftClick(Player player, GUI gui, InventoryClickEvent event) {
    }
}
