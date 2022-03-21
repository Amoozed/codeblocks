package org.amusedd.codeblocks.blocks.executables;

import org.amusedd.codeblocks.blocks.Viewable;
import org.amusedd.codeblocks.blocks.executables.containers.CodeBlockContainer;
import org.amusedd.codeblocks.blocks.value.ValueBlock;
import org.amusedd.codeblocks.blocks.value.ValueSetBlock;
import org.amusedd.codeblocks.commands.input.communication.Receiver;
import org.amusedd.codeblocks.menu.InitializeBlockMenu;
import org.bukkit.entity.Player;

public interface ValueHolder {
    ValueSetBlock getValueSet();

    default void addValue(String name, ValueBlock value) {
        getValueSet().add(name, value);
    }

    default boolean isRunnable() {
        for (ValueBlock value : getValueSet().getValues()) {
            if (value.getData().getValue() == null) return false;
        }
        return true;
    }

    default ValueBlock getValueBlock(String name) {
        return getValueSet().get(name);
    }

    default void setValue(String name, Object value) {
        getValueSet().get(name).setValue(value);
        if (this instanceof Viewable) {
            ((Viewable) this).updateItemLore();
        }
    }

    default void create(Player player, CodeBlockContainer container) {
        if (!getValueSet().canRun()) {
            new InitializeBlockMenu(player, getValueSet(), container, this).open();
        }
    }

    default void onCreate(CodeBlockContainer container) {

    }
}
