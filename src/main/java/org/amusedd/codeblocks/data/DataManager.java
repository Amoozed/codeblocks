package org.amusedd.codeblocks.data;

import org.amusedd.codeblocks.CodeBlocksPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataManager {
    private final CodeBlocksPlugin instance = CodeBlocksPlugin.getInstance();

    private final HashMap<String, ConfigurationSerializable> queuedSaves = new HashMap<>();

    public ConfigurationSerializable getDataFromConfig(String folder, String fileName) {
        FileConfiguration config = getFileConfig(folder, fileName);
        ConfigurationSerializable data = (ConfigurationSerializable) config.get("data");
        return data;
    }

    public void unloadDataToConfig(ConfigurationSerializable serializable, String folder, String fileName) {
        FileConfiguration config = getFileConfig(folder, fileName);
        config.set("data", serializable);
        try {
            config.save(instance.getDataFolder() + "/" + folder + "/" + fileName + ".yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearFile(String folder, String fileName) {
        // Get a file by the folder and file name, and delete it if it exists
        File file = new File(instance.getDataFolder() + "/" + folder + "/" + fileName + ".yml");
        if (file.exists()) {
            file.delete();
        }
    }

    FileConfiguration getFileConfig(String folder, String fileName) {
        return getFileConfig(instance.getDataFolder() + "/" + folder + "/" + fileName + ".yml");
    }

    FileConfiguration getFileConfig(String path) {
        File dataFile = new File(path);
        System.out.println(path);
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return YamlConfiguration.loadConfiguration(dataFile);
    }


    public FileConfiguration[] getAllFileConfigsInFolder(String folder) {
        File[] files = new File(instance.getDataFolder() + "/" + folder).listFiles();
        FileConfiguration[] configs = new FileConfiguration[files.length];
        for(File x : files){
            configs[files.length - 1] = YamlConfiguration.loadConfiguration(x);
        }
        for (int i = 0; i < files.length; i++) {
            configs[i] = getFileConfig(files[i].getPath());
        }
        return configs;
    }

    public void UnloadAll() {
        for (String path : queuedSaves.keySet()) {
            FileConfiguration config = getFileConfig(path);
            config.set("data", queuedSaves.get(path));
            try {
                config.save(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        queuedSaves.clear();
    }

    public void directSave(String folder, String fileName, Map<String, Object> serializable) {
        FileConfiguration config = getFileConfig(folder, fileName);
        config.set("data", serializable);
        String path = instance.getDataFolder() + "/" + folder + "/" + fileName + ".yml";
        try {
            config.save(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void queueSave(String folder, String fileName, ConfigurationSerializable data) {
        queuedSaves.put(instance.getDataFolder() + "/" + folder + "/" + fileName + ".yml", data);
    }
}
