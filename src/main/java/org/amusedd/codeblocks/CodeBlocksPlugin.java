package org.amusedd.codeblocks;

import org.amusedd.codeblocks.blocks.BlockStorage;
import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.commands.PluginCommand;
import org.amusedd.codeblocks.data.DataManager;
import org.amusedd.codeblocks.events.SimpleEventCalls;
import org.amusedd.codeblocks.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public final class CodeBlocksPlugin extends JavaPlugin {
    static CodeBlocksPlugin plugin;
    private BlockStorage blockStorage;
    private DataManager data;

    @Override
    public void onEnable() {
        // Plugin startup logic
        ConfigurationSerialization.registerClass(CodeBlock.class);
        getServer().getPluginManager().registerEvents(new SimpleEventCalls(), this);
        plugin = this;
        data = new DataManager();
        blockStorage = new BlockStorage();
        for (Class<? extends CodeBlock> clazz : new Reflections("org.amusedd.codeblocks.blocks").getSubTypesOf(CodeBlock.class)) {
            try {
                System.out.println("Registering " + clazz.getSimpleName());
                ConfigurationSerialization.registerClass(clazz);
                ItemStack preview = new ItemBuilder((ItemStack) clazz.getMethod("getPreview").invoke(null)).setName(clazz.getSimpleName()).setTag("createtype", clazz.getSimpleName(), PersistentDataType.STRING).build();
                blockStorage.addPreviewBlock(clazz.getSimpleName(), preview);
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                ItemStack preview = new ItemBuilder(Material.STONE).setTag("createtype", clazz.getSimpleName(), PersistentDataType.STRING).setName(clazz.getSimpleName()).build();
                blockStorage.addPreviewBlock(clazz.getSimpleName(), preview);
            }
        }
        for (Class<? extends PluginCommand> clazz : new Reflections("org.amusedd.codeblocks.commands").getSubTypesOf(PluginCommand.class)) {
            try {
                PluginCommand pluginCommand = clazz.getDeclaredConstructor().newInstance();
                Objects.requireNonNull(getCommand(pluginCommand.getCommandInfo().name())).setExecutor(pluginCommand);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        blockStorage.refreshBlocks();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        blockStorage.prepareSave();
    }

    public static CodeBlocksPlugin getInstance(){
        return plugin;
    }

    public DataManager getDataManager() {
        return data;
    }

    public BlockStorage getBlockStorage() {
        return blockStorage;
    }
}
