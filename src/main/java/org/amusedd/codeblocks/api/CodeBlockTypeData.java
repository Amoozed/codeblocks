package org.amusedd.codeblocks.api;

import org.amusedd.codeblocks.util.ViewData;

import java.util.List;

public class CodeBlockTypeData {
    ViewData data;
    MenuCategory category;

    public CodeBlockTypeData(ViewData data, MenuCategory category) {
        this.data = data;
        this.category = category;
    }

    public ViewData getData() {
        return data;
    }

    public MenuCategory getCategory() {
        return category;
    }
}
