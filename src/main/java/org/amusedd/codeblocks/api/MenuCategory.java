package org.amusedd.codeblocks.api;

import org.amusedd.codeblocks.blocks.Viewable;
import org.amusedd.codeblocks.util.ViewData;

public class MenuCategory {
    ViewData viewData;
    String id;
    static CodeBlockMenuCategories categories = new CodeBlockMenuCategories();

    public MenuCategory(String id, ViewData viewData) {
        this.viewData = viewData;
        this.id = id;
        categories.addCategory(this);
    }

    public String getId() {
        return id;
    }

    public ViewData getViewData() {
        return viewData;
    }
}
