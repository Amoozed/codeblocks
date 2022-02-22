package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.gui.PaginatedGUI;
import org.amusedd.codeblocks.gui.PaginatedSelectGUI;
import org.amusedd.codeblocks.gui.SelectGUI;
import org.amusedd.codeblocks.input.ChatInput;
import org.amusedd.codeblocks.input.ValueBlockData;
import org.amusedd.codeblocks.input.ValueSet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Map;

public class ValueBlock extends CodeBlock {

    private ValueBlockData data;
    private GUI editingGUI;

    public ValueBlock(ValueBlockData data) {
        System.out.println("ValueBlock created: " + data);
        this.data = data;
    }

    @Override
    public void run() {}

    @Override
    public ItemStack getBaseItem() {
        return getData().getItem();
    }

    @Override
    public ItemStack getRefreshedItem() {
        return getData().getItem();
    }

    @Override
    public ItemStack getItem() {
        return getData().getItem();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("data", getData());
        return map;
    }

    public static ValueBlock deserialize(Map<String, Object> map)  {
        return new ValueBlock((ValueBlockData) map.get("data"));
    }

    @Override
    public void onGUILeftClick(Player player, GUI gui, InventoryClickEvent event) {
        System.out.println("mm yeah");
        editingGUI = gui;
        if(getData().getType().getValueSelection() != null) {
            ArrayList<ItemStack> selection = getData().getType().getValueSelection();
            if(selection.size() > 45) {
                new PaginatedSelectGUI(player, selection, gui, event.getSlot()).open();
            } else {
                new SelectGUI(player, gui, selection, event.getSlot()).open();
            }
        } else{
            new ChatInput("Enter value of type: " + getData().getType().name(), player, this).awaitResponse();
        }

    }
    @Override
    public void onResponse(String response) {
        System.out.println("dog fart");
        if(getData().getType().isOfType(response)){
            getData().setValue(getData().getType().getTypedValue(response));
            if(editingGUI != null) {
                editingGUI.open();
                System.out.println("DDDDDD DUDE FNAF!!!!");
                editingGUI = null;
            }
        } else{
            Bukkit.getLogger().info("Invalid value: " + response);
        }
    }


    public boolean hasValue() {
        return getData().getValue() != null;
    }

    public ValueBlockData getData() {
        System.out.println("ValueBlockData: " + data);
        return data;
    }
}
