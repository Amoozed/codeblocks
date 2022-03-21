package org.amusedd.codeblocks.api;

import org.amusedd.codeblocks.menu.Menu;

import java.util.HashMap;

public class CodeBlockMenuCategories {
    static HashMap<String, MenuCategory> categoryHashMap = new HashMap<String, MenuCategory>();

    public void addCategory(MenuCategory category) {
        categoryHashMap.put(category.getId(), category);
    }

    public static MenuCategory getCategoryByID(String id) {
        return categoryHashMap.get(id);
    }
}
