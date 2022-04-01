package org.amusedd.codeblocks.util.items;

import org.amusedd.codeblocks.menu.Menu;
import org.amusedd.codeblocks.menu.SelectMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LambdaButton extends OverridableItemStack{
    Consumer<InventoryClickEvent> lambda;
    public LambdaButton(Material material, String name, List<String> lore, Consumer<InventoryClickEvent> lambda) {
        super(material, name, lore);
        this.lambda = lambda;
    }

    public LambdaButton(ItemStack item, Consumer<InventoryClickEvent> lambda) {
        super(item);
        this.lambda = lambda;
    }

    @Override
    public void onClick(Menu menu, InventoryClickEvent event) {
        lambda.accept(event);

    }
}
