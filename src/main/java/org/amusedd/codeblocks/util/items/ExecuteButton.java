package org.amusedd.codeblocks.util.items;

import org.amusedd.codeblocks.blocks.executables.ExecutableCodeBlock;
import org.amusedd.codeblocks.menu.Menu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class ExecuteButton extends OverridableItemStack{
    ExecutableCodeBlock executableCodeBlock;
    public ExecuteButton(ExecutableCodeBlock executableCodeBlock) {
        super(Material.BEACON, ChatColor.WHITE + "Execute", List.of(new String[]{ChatColor.GRAY + "Execute this CodeBlock", ChatColor.RED + "WARNING: This can cause errors!"}));
        this.executableCodeBlock = executableCodeBlock;
    }

    @Override
    public void onClick(Menu menu, InventoryClickEvent event) {
        executableCodeBlock.execute();
    }
}
