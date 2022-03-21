package org.amusedd.codeblocks;

import org.amusedd.codeblocks.util.storage.DataManager;
import org.amusedd.codeblocks.util.storage.EventStorage;
import org.amusedd.codeblocks.util.values.ValueWrapper;

public class CodeBlocksUtility {
    ValueWrapper valueWrapper;
    DataManager dataManager;
    EventStorage eventStorage;

    protected CodeBlocksUtility() {
        valueWrapper = new ValueWrapper();
        dataManager = new DataManager();
        eventStorage = new EventStorage();
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
}

