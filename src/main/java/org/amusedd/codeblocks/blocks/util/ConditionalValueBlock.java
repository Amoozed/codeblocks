package org.amusedd.codeblocks.blocks.util;

import org.amusedd.codeblocks.blocks.ValueBlock;
import org.amusedd.codeblocks.gui.EditVariablesGUI;
import org.amusedd.codeblocks.gui.GUI;
import org.amusedd.codeblocks.input.ConditionalType;
import org.amusedd.codeblocks.input.ValueBlockData;
import org.amusedd.codeblocks.input.ValueSet;
import org.amusedd.codeblocks.input.ValueType;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ConditionalValueBlock extends ValueBlock {
    ValueSet set;

    public ConditionalValueBlock(ValueBlockData data, ValueSet set){
        super(data);
        this.set = set;
        //if(set.getValueBlock("conditional_type").getData().getValue() != null) getValueSet().setValue("conditional_type", type.getValue());
        //if(a.getValue() != null) getValueSet().setValue("a", a.getValue());
        //if(b.getValue() != null) getValueSet().setValue("b", b.getValue());
        //setTag("conditional_type", type.getValue(), PersistentDataType.STRING);
    }



    @Override
    public ValueSet getValueSet() {
        if(set == null){
            set = new ValueSet();
            set.addValueBlock("a", new ValueBlock(new ValueBlockData(Material.BLUE_WOOL, "Value A", ValueType.INTEGER, null)));
            set.addValueBlock("b", new ValueBlock(new ValueBlockData(Material.GREEN_WOOL, "Value B", ValueType.INTEGER, null)));
            set.addValueBlock("conditional_type", new ValueBlock(new ValueBlockData(Material.OAK_FENCE_GATE, "Conditional Type", ValueType.CONDITIONAL, null)));
        }
        return set;
    }

    @Override
    public void onGUILeftClick(Player player, GUI gui, InventoryClickEvent event) {
        new EditVariablesGUI(player, set).open();
    }

    @Override
    public ItemStack getBaseItem() {
        return new ItemBuilder(Material.REDSTONE_BLOCK).setName("Conditional Block").build();
    }

    public boolean evaluate(){
        ValueBlock a = getA();
        ValueBlock b = getB();
        ConditionalType type = (ConditionalType) getValueSet().getValueBlock("conditional_type").getData().getValue();
        if(type == ConditionalType.EQUALS){
            return a.getData().getValue().equals(b.getData().getValue());
        } else if(type == ConditionalType.NOT_EQUALS){
            return !a.getData().getValue().equals(b.getData().getValue());
        } else{
            if(a.getData().getValue() instanceof Number){
                switch(type){
                    case GREATER_THAN:
                        return ((Number) a.getData().getValue()).doubleValue() > ((Number) b.getData().getValue()).doubleValue();
                    case GREATER_THAN_EQUALS:
                        return ((Number) a.getData().getValue()).doubleValue() >= ((Number) b.getData().getValue()).doubleValue();
                    case LESS_THAN:
                        return ((Number) a.getData().getValue()).doubleValue() < ((Number) b.getData().getValue()).doubleValue();
                    case LESS_THAN_EQUALS:
                        return ((Number) a.getData().getValue()).doubleValue() <= ((Number) b.getData().getValue()).doubleValue();
                }
            }
        }
        return false;
    }

    public ValueBlock getA(){
        return set.getValueBlock("a");
    }

    public ValueBlock getB(){
        return set.getValueBlock("b");
    }


    public static ConditionalValueBlock deserialize(Map<String, Object> data){
        ValueBlockData data1 = (ValueBlockData) data.get("data");
        ValueSet set = (ValueSet) data.get("value_set");
        return new ConditionalValueBlock(data1, set);
    }
}
