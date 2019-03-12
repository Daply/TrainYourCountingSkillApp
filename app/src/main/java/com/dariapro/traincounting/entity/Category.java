package com.dariapro.traincounting.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Category {
    private UUID categoryId = null;
    private String title = null;
    private List<Level> levels = null;

    public Category() {
        this.categoryId = UUID.randomUUID();
        this.title = new String();
        this.levels = new ArrayList<Level>();
    }

    public Category(String title, List<Level> levels) {
        this.categoryId = UUID.randomUUID();
        this.title = title;
        this.levels = levels;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
