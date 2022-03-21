package org.amusedd.codeblocks.util.storage;

import org.amusedd.codeblocks.CodeBlocks;
import org.amusedd.codeblocks.util.storage.EventStorage;
import org.amusedd.codeblocks.util.storage.FunctionStorage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Cod;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class DataManager {
    final HashMap<String, ConfigurationSerializable> queuedSaves = new HashMap<>();

    public FileConfiguration[] getAllFileConfigsInFolder(String folder) {
        File[] files = new File(CodeBlocks.getPlugin().getDataFolder() + "\\" + folder).listFiles();
        if(files == null) {
            return new FileConfiguration[0];
        }
        FileConfiguration[] configs = new FileConfiguration[files.length];
        for(File x : files){
            configs[files.length - 1] = YamlConfiguration.loadConfiguration(x);
        }
        for (int i = 0; i < files.length; i++) {
            try {
                configs[i] = getFileConfig(files[i].getPath());
            } catch (IOException e) {
                CodeBlocks.getPlugin().getLogger().severe("Could not load file: " + files[i].getPath());
            }
        }
        return configs;
    }



    public void unloadAll() {
        for (String path : queuedSaves.keySet()) {
            try {
                FileConfiguration config = getFileConfig(path);
                config.set("data", queuedSaves.get(path));
                config.save(path);
            } catch (IOException e) {
                CodeBlocks.getPlugin().getLogger().severe("Could not save file: " + path + " due to: " + e.getCause());
                e.printStackTrace();
            }
        }
        queuedSaves.clear();
    }

    FileConfiguration getFileConfig(String path) throws IOException {
        File dataFile = new File(path);
        dataFile.getParentFile().mkdirs();
        dataFile.createNewFile();
        return YamlConfiguration.loadConfiguration(dataFile);
    }

    public void queueSave(String folder, String fileName, ConfigurationSerializable data, String extension) {
        queuedSaves.put(CodeBlocks.getPlugin().getDataFolder() + "\\" + folder + "\\" + fileName + "." + extension, data);
    }


    public void queueSave(String folder, String fileName, ConfigurationSerializable data ) {
        queueSave(folder, fileName, data, "yml");
    }
}
