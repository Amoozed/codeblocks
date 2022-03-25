package org.amusedd.codeblocks.menu;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.util.items.OverridableItemStack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Cod;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class Menu implements InventoryHolder {
    protected Inventory inventory;
    private Menu parent;
    private final Player owner;
    boolean forceClosed = false;


    public Menu(Player player) {
        this.owner = player;
    }

    public abstract String getName();


    public final void clickEvent(InventoryClickEvent event) {
        ItemStack itemStack = event.getCurrentItem();
        if(itemStack == null) return;
        if (OverridableItemStack.isOverridableItemStack(itemStack)) {
            OverridableItemStack overridableItemStack = OverridableItemStack.getOverridableItemStack(itemStack);
            overridableItemStack.onClick(this, event);
        } else {
            itemClicked(itemStack, event);
        }
    }

    public void itemClicked(ItemStack item, InventoryClickEvent event){
        event.getWhoClicked().sendMessage(ChatColor.RED + "This action has not been implemented yet.");
    }

    public abstract HashMap<Integer, ItemStack> getItems();

    public abstract ItemStack blankSpot();

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void fillInventory() {
        HashMap<Integer, ItemStack> items = getItems();
        if (blankSpot() != null) {
            for (int i = 0; i < getRows() * 9; i++) {
                if (items.containsKey(i)) {
                    inventory.setItem(i, items.get(i));
                } else {
                    inventory.setItem(i, blankSpot());
                }
            }
        }
        for (int index : items.keySet()) {
            inventory.setItem(index, items.get(index));
        }
    }

    public Menu getParent(){
        return parent;
    }

    public void setParent(Menu parent){
        this.parent = parent;
    }

    public final void open() {
        forceClosed = false;
        if(owner.getOpenInventory().getTopInventory().getHolder() instanceof Menu){
            if(parent == null) {
                setParent((Menu)owner.getOpenInventory().getTopInventory().getHolder());
                parent.forceClose();
            }
            else ((Menu) owner.getOpenInventory().getTopInventory().getHolder()).forceClose();
        }
        inventory = Bukkit.createInventory(this, getRows() * 9, (getName() != null) ? getName() : getClass().getSimpleName());
        fillInventory();
        owner.openInventory(inventory);
    }

    public Player getOwner() {
        return owner;
    }

    public final void closeEvent(){
        CodeBlocks.getPlugin().getLogger().info("Closing menu " + getName());
        if(forceClosed) return;
        Bukkit.getScheduler().scheduleSyncDelayedTask(CodeBlocks.getPlugin(), new Runnable() {
            @Override
            public void run() {
                onClose();
            }
        }, 2);
    }

    public int getRows() {
        if(getItems().size() % 9 == 0) {
            return getItems().size() / 9;
        } else {
            return getItems().size() / 9 + 1;
        }
    }

    public void onClose(){
        if (parent != null) {
            parent.open();
        }
    }

    public Menu getFirstParentOfType(Class<? extends Menu> menuClass){
        if(parent == null) return null;
        if(menuClass.isAssignableFrom(parent.getClass())) return parent;
        return parent.getFirstParentOfType(menuClass);
    }

    public void forceClose(){
        forceClosed = true;
        owner.closeInventory();
    }
}
