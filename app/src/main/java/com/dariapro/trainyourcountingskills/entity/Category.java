package com.dariapro.trainyourcountingskills.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author Pleshchankova Daria
 *
 */
@Entity(tableName = "category",
        indices = {@Index(value = "title", unique = true)})
public class Category {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoryId")
    private long categoryId;

    @ColumnInfo(name = "categoryNumber")
    private long categoryNumber = 0;

    @ColumnInfo(name = "passed")
    private boolean passed = false;

    @ColumnInfo(name = "title")
    @NonNull
    private String title;

    public Category(@NonNull String title, long categoryNumber) {
        this.title = title;
        this.categoryNumber = categoryNumber;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getCategoryNumber() {
        return categoryNumber;
    }

    public void setCategoryNumber(long categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }
}
