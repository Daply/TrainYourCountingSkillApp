package com.dariapro.traincounting.entity;

import java.util.UUID;

public class Category {
    private UUID categoryId;
    private String title = null;

    public Category() {
        this.title = new String();
    }

    public Category(String title) {
        this.title = title;
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
