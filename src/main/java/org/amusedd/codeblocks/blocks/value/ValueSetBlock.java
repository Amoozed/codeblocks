package org.amusedd.codeblocks.blocks.value;

import org.amusedd.codeblocks.blocks.executables.ValueHolder;
import org.amusedd.codeblocks.commands.input.communication.Conversation;
import org.amusedd.codeblocks.commands.input.communication.Receiver;
import org.amusedd.codeblocks.menu.EditVariablesMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ValueSetBlock extends ValueBlock {
    HashMap<String, ValueBlock> values;
    Receiver changeCallback;
    ValueHolder valueHolder;

    public ValueSetBlock(String name, HashMap<String, ValueBlock> values) {
        super(name, Material.EMERALD_BLOCK, ValueSetBlock.class, null);
        this.values = values;
        for(ValueBlock value : values.values()){
            value.setParent(this);
        }
    }


    public ValueSetBlock(String name, HashMap<String,ValueBlock> values, ValueHolder valueHolder) {
        this(name, values);
        setHolder(valueHolder);
    }

    public ValueSetBlock(String name, ValueBlock... values) {
        super(name, Material.EMERALD_BLOCK, ValueSetBlock.class, null);
        HashMap<String, ValueBlock> map = new HashMap<>();
        for (ValueBlock value : values) {
            map.put(value.getData().getName(), value);
            value.setParent(this);
        }
        this.values = map;
    }

    public ValueHolder getHolder() {
        return valueHolder;
    }

    public void setHolder(ValueHolder valueHolder) {
        this.valueHolder = valueHolder;
    }

    public ValueSetBlock(HashMap<String, ValueBlock> values) {
        this("data", values);
    }

    public ValueSetBlock(String name){
        this(name, new HashMap<String, ValueBlock>());
    }

    public void add(String name, ValueBlock value) {
        values.put(name, value);
    }

    public void setChangeCallback(Receiver callback) {
        this.changeCallback = callback;
    }

    public ValueBlock get(String name) {
        return values.get(name);
    }

    public ArrayList<ValueBlock> getValues() {
        return new ArrayList<ValueBlock>(values.values());
    }

    @Override
    public void onGUIItemLeftClick(InventoryClickEvent event) {
        new EditVariablesMenu((Player) event.getWhoClicked(),  this).open();
    }

    public boolean canRun(){
        for(ValueBlock value : values.values()){
            if(value.getData().isRequired() && value.getValue() == null){
                return false;
            }
        }
        return true;
    }

    protected void replace(ValueBlock old, ValueBlock newValue){
        for(String key : values.keySet()){
            if(values.get(key).equals(old)){
                values.put(key, newValue);
                newValue.setParent(this);
                break;
            }
        }
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map =  super.serialize();
        map.put("values", values);
        return map;
    }

    public static ValueSetBlock deserialize(Map<String, Object> map){
        String name = (String) map.get("name");
        HashMap<String, ValueBlock> values = (HashMap<String, ValueBlock>) map.get("values");
        return new ValueSetBlock(name, values);
    }

    void callChange(ValueBlock value){
        if(changeCallback != null) changeCallback.onValueBlockEdit(new Conversation(changeCallback, this), value);
    }
}
