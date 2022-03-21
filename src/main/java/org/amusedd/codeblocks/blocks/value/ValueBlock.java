package org.amusedd.codeblocks.blocks.value;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.Viewable;
import org.amusedd.codeblocks.commands.input.ChatInput;
import org.amusedd.codeblocks.commands.input.communication.Conversation;
import org.amusedd.codeblocks.commands.input.communication.Receiver;
import org.amusedd.codeblocks.menu.Menu;
import org.amusedd.codeblocks.menu.SelectMenu;
import org.amusedd.codeblocks.util.ViewData;
import org.amusedd.codeblocks.util.values.SpecifiedSet;
import org.amusedd.codeblocks.util.values.ValueBlockData;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Map;

public class ValueBlock extends CodeBlock implements Viewable, Receiver {
    ValueBlockData data;
    ValueSetBlock parent;

    public ValueBlock(ViewData viewData, Class type, Object value) {
        this(new ValueBlockData(viewData, type, value));
    }

    public ValueBlock(ViewData viewData, Class type, Object value, boolean isRequired) {
        this(new ValueBlockData(viewData, type, value, isRequired));
    }

    public ValueBlock(String name, Material material, Class type, Object value) {
        this(new ViewData(name, material), type, value);
    }

    public ValueBlock(ValueBlockData data) {
        this.data = data;
    }

    public static ValueBlock deserialize(Map<String, Object> map) {
        return new ValueBlock((ValueBlockData) map.get("data"));
    }

    public ValueBlockData getData() {
        return data;
    }

    public Class getValueType() {
        return data.getType();
    }

    public Object getRawValue() {
        return data.getValue();
    }

    public Object getValue() {
        if(data.getValue() instanceof VariableBlock){
            return ((VariableBlock) data.getValue()).getValue();
        }
        if(CodeBlocks.getPlugin().getValueWrapper().hasWrapper(data.getType())) {
            return CodeBlocks.getPlugin().getValueWrapper().getUnwrappedValue(this);
        } else {
            return getRawValue();
        }
    }

    public String getValueAsString(){
        if(CodeBlocks.getPlugin().getValueWrapper().hasWrapper(data.getType())) {
            return CodeBlocks.getPlugin().getValueWrapper().unwrapToString(this);
        }
        return getValue().toString();
    }

    public void setValue(Object value) {
        if(value instanceof VariableBlock){
            data.setValue(value);
        } else if (CodeBlocks.getPlugin().getValueWrapper().hasWrapper(data.getType())) {
            ValueBlock block = CodeBlocks.getPlugin().getValueWrapper().getWrappedValue(data.getType(), value);
            if (block != null) {
                block.getData().setViewData(data.getViewData());
                block.getData().setType(data.getType());
                getParent().replace(this, block);
                if(getParent() != null) getParent().callChange(block);
            } else {
                CodeBlocks.getPlugin().getLogger().warning("Could not find value block for type: " + data.getType().getSimpleName() + " and value: " + value);
            }
        } else {
            data.setValue(value);
            CodeBlocks.getPlugin().getLogger().info("No wrapper for type: " + data.getType().getSimpleName() + " and value: " + value);
        }

    }

    @Override
    public ItemStack getBaseItem() {
        if (data.toItemStack() != null) {
            return data.toItemStack();
        }
        return null;
    }

    public ValueSetBlock getParent() {
        return parent;
    }

    public void setParent(ValueSetBlock parent) {
        this.parent = parent;
    }

    @Override
    public void onGUIItemLeftClick(InventoryClickEvent event) {
        if (CodeBlocks.getPlugin().getValueWrapper().getSet(data.getType()) != null) {
            SpecifiedSet set = CodeBlocks.getPlugin().getValueWrapper().getSet(data.getType());
            new SelectMenu((Player) event.getWhoClicked(), (ArrayList<ItemStack>) set.getAsItems(), this).open();
        } else {
            ((Menu) event.getClickedInventory().getHolder()).forceClose();
            new ChatInput("Please enter a value of the type: " + data.getType().getSimpleName(), (Player) event.getWhoClicked(), new Conversation(this, (Receiver) (event.getClickedInventory().getHolder()))).awaitResponse();
        }
    }

    @Override
    public void onItemResponse(Conversation conversation, InventoryClickEvent event, int position) {
        System.out.println("Response: " + position);
        String value = event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(CodeBlocks.getPlugin(), "type"), PersistentDataType.STRING);
        if (value != null) {
            setValue(value);
        } else {
            CodeBlocks.getPlugin().getLogger().warning("Could not find value block for type: " + data.getType().getSimpleName() + " and value: " + value);
        }
    }

    @Override
    public void onTextResponse(Conversation sender, String text) {
        ValueBlock block = CodeBlocks.getPlugin().getValueWrapper().getWrappedValue(data.getType(), text);
        if (block != null) {
            block.getData().setViewData(data.getViewData());
            block.getData().setType(data.getType());
            getParent().replace(this, block);
            if (sender != null) sender.complete();
            if(getParent() != null) getParent().callChange(block);
        } else {
            CodeBlocks.getPlugin().getLogger().warning("Could not find value block for type: " + data.getType().getSimpleName() + " and value: " + text);
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("data", data);
        return map;
    }

}
