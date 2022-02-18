package org.amusedd.codeblocks.blocks;

import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.gui.SelectGUI;
import org.amusedd.codeblocks.input.ChatInput;
import org.amusedd.codeblocks.input.ValueBlockData;
import org.amusedd.codeblocks.input.ValueSet;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ValueBlock extends CodeBlock {

    private ValueBlockData data;

    public ValueBlock(ValueBlockData data) {
        this.data = data;
    }

    @Override
    public boolean run() {
       return true;
    }

    @Override
    public ItemStack getBaseItem() {
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
    public void onGUIRightClick(Player player, GUI gui) {
    }

    @Override
    public void onGUILeftClick(Player player, GUI gui) {
        if(getData().getType().getValueSelection() != null) {
            new SelectGUI(player, gui, getData().getType().getValueSelection()).open();
            return;
        }
        new ChatInput("Enter value of type: " + getData().getType().name(), player, this).awaitResponse();
    }

    @Override
    public ValueSet getValueSet() {
        throw new UnsupportedOperationException("Value blocks do not support value sets");
    }

    @Override
    public void onResponse(String response) {
        getData().setValue(getData().getType().getTypedValue(response));
    }


    public boolean hasValue() {
        return getData().getValue() != null;
    }

    public ValueBlockData getData() {
        return data;
    }
}
