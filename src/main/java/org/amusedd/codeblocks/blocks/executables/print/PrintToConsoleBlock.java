package org.amusedd.codeblocks.blocks.executables.print;

import org.amusedd.codeblocks.blocks.value.ValueSetBlock;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class PrintToConsoleBlock extends PrintBlock {

    public PrintToConsoleBlock(ValueSetBlock valueset) {
        super(valueset);
    }

    public PrintToConsoleBlock() {
        super();
    }

    @Override
    public boolean print() {
        Bukkit.getLogger().info((String) getValueBlock("text").getValue());
        return true;
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.PAPER).setName(ChatColor.WHITE + "Print to Console").build();
    }

    public static PrintToConsoleBlock deserialize(Map<String, Object> map){
        return new PrintToConsoleBlock((ValueSetBlock) map.get("valueset"));
    }



}
