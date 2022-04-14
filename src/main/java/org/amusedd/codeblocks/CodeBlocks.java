package org.amusedd.codeblocks;

import org.amusedd.codeblocks.blocks.CodeBlock;
import org.amusedd.codeblocks.blocks.value.ValueSet;
import org.amusedd.codeblocks.commands.PluginCommand;
import org.amusedd.codeblocks.events.SelfEvents;
import org.amusedd.codeblocks.util.ViewData;
import org.amusedd.codeblocks.util.storage.DataManager;
import org.amusedd.codeblocks.util.storage.EventStorage;
import org.amusedd.codeblocks.util.storage.FunctionStorage;
import org.amusedd.codeblocks.util.values.ValueBlockData;
import org.amusedd.codeblocks.util.values.ValueWrapper;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public final class CodeBlocks extends JavaPlugin {
    static CodeBlocks plugin;
    static CodeBlocksAPI api;
    private ValueWrapper valueWrapper;
    private DataManager dataManager;
    private EventStorage eventStorage;
    private FunctionStorage functionStorage;
    @Override
    public void onEnable() {
        plugin = this;
        for (Class<? extends PluginCommand> clazz : new Reflections("org.amusedd.codeblocks.commands").getSubTypesOf(PluginCommand.class)) {
            try {
                PluginCommand pluginCommand = clazz.getDeclaredConstructor().newInstance();
                Objects.requireNonNull(getCommand(pluginCommand.getCommandInfo().name())).setExecutor(pluginCommand);
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        for(Class<? extends ConfigurationSerializable> clazz : new Reflections("org.amusedd.codeblocks").getSubTypesOf(ConfigurationSerializable.class)) {
            ConfigurationSerialization.registerClass(clazz);
        }
        for(Class<? extends CodeBlock> clazz : new Reflections("org.amusedd.codeblocks.blocks").getSubTypesOf(CodeBlock.class)) ConfigurationSerialization.registerClass(clazz);
        ConfigurationSerialization.registerClass(ViewData.class);
        ConfigurationSerialization.registerClass(ValueSet.class);
        ConfigurationSerialization.registerClass(CodeBlock.class);
        ConfigurationSerialization.registerClass(ValueBlockData.class);
        valueWrapper = new ValueWrapper();
        dataManager = new DataManager();
        functionStorage = new FunctionStorage();
        eventStorage = new EventStorage();
        api = new CodeBlocksAPI();
        getServer().getPluginManager().registerEvents(new SelfEvents(), this);
    }


    @Override
    public void onDisable() {
       dataManager.unloadAll();
    }

    public static CodeBlocks getPlugin(){
        return plugin;
    }

    public static CodeBlocksAPI getAPI(){
        return api;
    }

    public ValueWrapper getValueWrapper() {
        return valueWrapper;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public EventStorage getEventStorage() {
        return eventStorage;
    }

    public FunctionStorage getFunctionStorage() {
        return functionStorage;
    }
}
