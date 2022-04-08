package org.amusedd.codeblocks.blocks.value;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.RetrievableValue;
import org.amusedd.codeblocks.blocks.Viewable;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.commands.input.communication.Conversation;
import org.amusedd.codeblocks.commands.input.communication.Receiver;
import org.amusedd.codeblocks.menu.ContainerEditMenu;
import org.amusedd.codeblocks.menu.DirectValueEditMenu;
import org.amusedd.codeblocks.menu.Menu;
import org.amusedd.codeblocks.menu.SelectMenu;
import org.amusedd.codeblocks.util.ViewData;
import org.amusedd.codeblocks.util.values.SpecifiedSet;
import org.amusedd.codeblocks.util.values.ValueBlockData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueBlock extends CodeBlock implements Viewable, Receiver, RetrievableValue {
    ValueBlockData data;
    ValueSet parent;
    Object value;

    public ValueBlock(ViewData viewData, Class type, Object value) {
        this(new ValueBlockData(viewData, type), value);
    }

    public ValueBlock(ViewData viewData, Class type, Object value, boolean isRequired) {
        this(new ValueBlockData(viewData, type, isRequired), value);
    }

    public ValueBlock(String name, Material material, Class type, Object value) {
        this(new ViewData(name, material), type, value);
    }

    public ValueBlock(ValueBlockData data, Object value) {
        this.data = data;
        this.value = value;
    }

    public static ValueBlock deserialize(Map<String, Object> map) {
        return new ValueBlock((ValueBlockData) map.get("data"), map.get("value"));
    }

    public ValueBlockData getData() {
        return data;
    }

    public Class getValueType() {
        return data.getType();
    }

    public Object getCurrentValue() {
        return value;
    }

    public Object getValue() {
        if(getCurrentValue() == null) return null;
        if (getCurrentValue() instanceof RetrievableValue) {
            return ((RetrievableValue) getCurrentValue()).retrieveValue();
        }
        if (CodeBlocks.getPlugin().getValueWrapper().hasWrapper(data.getType())) {
            return CodeBlocks.getPlugin().getValueWrapper().getUnwrappedValue(this, getCurrentValue());
        } else {
            return getCurrentValue();
        }
    }

    public void setValue(String value){
        if(getValueType().equals(String.class)) {
            this.value = value;
        }
        else {
            if(CodeBlocks.getPlugin().getValueWrapper().hasWrapper(data.getType())) {
                this.value = CodeBlocks.getPlugin().getValueWrapper().getUnwrappedValue(this, value);
            }
        }
    }

    public void setValue(Object value) {
        if(value == null) return;
        if (value instanceof VariableBlock) {
            this.value = value;
        } else if (CodeBlocks.getPlugin().getValueWrapper().hasWrapper(data.getType())) {
            ValueBlock block = CodeBlocks.getPlugin().getValueWrapper().getWrappedValue(data.getType(), value);
            if (block != null) {
                block.getData().setViewData(data.getViewData());
                block.getData().setType(data.getType());
                getParent().replace(this, block);
                if (getParent() != null) getParent().callChange(block);
                return;
            } else {
                CodeBlocks.getPlugin().getLogger().warning("Could not find value block for type: " + data.getType().getSimpleName() + " and value: " + value);
            }
        } else {
            this.value = value;
            CodeBlocks.getPlugin().getLogger().info("No wrapper for type: " + data.getType().getSimpleName() + " and value: " + value);
        }
        if (getParent() != null) getParent().callChange(this);
    }

    public String getValueAsString() {
        if(getValue() == null) return null;
        else if (CodeBlocks.getPlugin().getValueWrapper().hasWrapper(data.getType())) {
            return CodeBlocks.getPlugin().getValueWrapper().unwrapToString(this);
        }
        return getValue().toString();
    }

    @Override
    public ItemStack getBaseItem() {
        if (toItemStack() != null) {
            return toItemStack();
        }
        return null;
    }

    public ValueSet getParent() {
        return parent;
    }

    public void setParent(ValueSet parent) {
        this.parent = parent;
    }

    @Override
    public void onGUIItemLeftClick(InventoryClickEvent event) {
        if (isRepresentingVariable()) {
            Menu menu = (Menu) event.getClickedInventory().getHolder();
            Menu parent = menu.getFirstParentOfType(ContainerEditMenu.class);
            System.out.println("aught " + menu.getParent().getName() + " : " + (parent instanceof ContainerEditMenu));
            if (parent instanceof ContainerEditMenu) {
                ContainerEditMenu containerEditMenu = (ContainerEditMenu) parent;
                CodeBlockContainer container = containerEditMenu.getContainer();
                ArrayList<VariableBlock> variableBlocks = container.getAllVariables();
                HashMap<Object, ItemStack> items = new HashMap<>();
                for (VariableBlock variableBlock : variableBlocks) {
                    items.put(variableBlock, variableBlock.getGUIItem());
                }
                new SelectMenu((Player) event.getWhoClicked(), items, this, 1).open();
            }
        }
        else if (CodeBlocks.getPlugin().getValueWrapper().getSet(data.getType()) != null) {
            SpecifiedSet set = CodeBlocks.getPlugin().getValueWrapper().getSet(data.getType());
            new SelectMenu((Player) event.getWhoClicked(), set.getAsMatch(), this).open();
        } else {
            /*((Menu) event.getClickedInventory().getHolder()).forceClose();
            new ChatInput("Please enter a value of the type: " + data.getType().getSimpleName(), (Player) event.getWhoClicked(), new Conversation(this, (Receiver) (event.getClickedInventory().getHolder()))).awaitResponse();
            */
            new DirectValueEditMenu((Player) event.getWhoClicked(), this).open();
        }
    }

    @Override
    public void onGUIItemRightClick(InventoryClickEvent event) {
        /*
        if(isRepresentingVariable()) {
            onGUIItemLeftClick(event);
            return;
        }
        Menu menu = (Menu) event.getClickedInventory().getHolder();
        Menu parent = menu.getFirstParentOfType(ContainerEditMenu.class);
        System.out.println("aught " + menu.getParent().getName() + " : " + (parent instanceof ContainerEditMenu));
        if (parent instanceof ContainerEditMenu) {
            ContainerEditMenu containerEditMenu = (ContainerEditMenu) parent;
            CodeBlockContainer container = containerEditMenu.getContainer();
            ArrayList<VariableBlock> variableBlocks = container.getAllVariables();
            ArrayList<ItemStack> items = new ArrayList<>();
            for (VariableBlock variableBlock : variableBlocks) {
                items.add(variableBlock.getGUIItem());
            }
            new SelectMenu((Player) event.getWhoClicked(), items, this, 1).open();
        }
        */
    }


    public boolean isRepresentingVariable(){
        return getValueType().equals(VariableBlock.class);
    }

    @Override
    public void onObjectResponse(Conversation conversation, Object value) {
        //Object value = event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(CodeBlocks.getPlugin(), "type"), PersistentDataType.STRING);
        /*if (value instanceof VariableBlock) {
            System.out.println("Setting value to: " + value);
            SelectMenu menu = (SelectMenu) conversation.getSender();
            ContainerEditMenu containerEditMenu = ((ContainerEditMenu) menu.getFirstParentOfType(ContainerEditMenu.class));
            CodeBlockContainer container = containerEditMenu.getContainer();
            // Get the first variable block from the container that shares the same name as the value
            String name = value + "";
            value = container.getAllVariables().stream().filter(v -> v.getName().equals(name)).findFirst().orElse(null);
        } */
        if (value != null) {
            setValue(value);
        } else {
            CodeBlocks.getPlugin().getLogger().warning("Could not find value block for type: " + data.getType().getSimpleName() + " and value: " + value);
        }
        conversation.complete();
    }

    @Override
    public void onTextResponse(Conversation sender, String text) {
        ValueBlock block = CodeBlocks.getPlugin().getValueWrapper().getWrappedValue(data.getType(), text);
        System.out.println("Setting value to: " + text + " wrapped to " + block.getValue());
        if (block != null) {
            block.getData().setViewData(data.getViewData());
            block.getData().setType(data.getType());
            getParent().replace(this, block);
            if (sender != null) sender.complete();
            if (getParent() != null) getParent().callChange(block);
        } else {
            CodeBlocks.getPlugin().getLogger().warning("Could not find value block for type: " + data.getType().getSimpleName() + " and value: " + text);
        }
    }

    public ItemStack toItemStack(){
        ItemStack item = getData().getViewData().getItem();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "Value: " + ChatColor.GRAY + getValueAsString());
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("data", data);
        map.put("value", getValueAsString());
        return map;
    }

    @Override
    public Object retrieveValue() {
        return getValue();
    }
}
