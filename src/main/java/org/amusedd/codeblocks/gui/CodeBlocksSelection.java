package org.amusedd.codeblocks.gui;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.amusedd.codeblocks.blocks.print.DebugBlock;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;

public class CodeBlocksSelection extends ResponseGUI{


    public CodeBlocksSelection(Player player, GUI parent) {
        super(player, parent);
    }

    @Override
    public String getName() {
        return "Select a CodeBlock";
    }

    @Override
    public int getRows() {
        return 6;
    }

    @Override
    public HashMap<Integer, ItemStack> getItems() {
        HashMap<Integer, ItemStack> items = new HashMap<>();
        ArrayList<ItemStack> previews = CodeBlocksPlugin.getInstance().getBlockStorage().getPreviewBlocks();
        for (int i = 0; i < previews.size(); i++) {
            items.put(i, previews.get(i));
        }
        return items;
    }

    @Override
    public ItemStack blankSpot() {
        return null;
    }

    @Override
    public void onClose() {
    }
}
