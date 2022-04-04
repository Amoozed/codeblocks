package org.amusedd.codeblocks.blocks.executables.print;

import org.amusedd.codeblocks.blocks.CodeBlockInfo;
import org.amusedd.codeblocks.blocks.value.ValueSet;
import org.amusedd.codeblocks.util.items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@CodeBlockInfo(viewName = "Send message to Server", viewMaterial = Material.PAPER, description = {"&7Send a message to the server", "&7Uses Bukkit's Broadcast functionality"})
public class PrintToServerBlock extends PrintBlock {

    public PrintToServerBlock(ValueSet valueset) {
        super(valueset);
    }

    public PrintToServerBlock() { super();}

    @Override
    public boolean print() {
        Bukkit.broadcastMessage((String) getValueBlock("text").getValue());
        return true;
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.PAPER).setName(ChatColor.WHITE + "Print to Server").build();
    }

    public static PrintToServerBlock deserialize(Map<String, Object> map){
        return new PrintToServerBlock((ValueSet) map.get("valueset"));
    }



}
