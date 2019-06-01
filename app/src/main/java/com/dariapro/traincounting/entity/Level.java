package com.dariapro.traincounting.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * @author Pleshchankova Daria
 *
 */
@Entity(tableName = "level",
        foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "categoryId",
        childColumns = "levelCategoryId",
        onDelete = ForeignKey.CASCADE),
        indices = {@Index("title"), @Index("levelCategoryId")})
public class Level {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "levelId")
    private long levelId;

    @ColumnInfo(name = "levelCategoryId")
    @NonNull
    private long levelCategoryId;

    @ColumnInfo(name = "passed")
    private boolean passed = false;

    @ColumnInfo(name = "title")
    @NonNull
    private String title = null;

    public Level(@NonNull String title, @NonNull long levelCategoryId) {
        this.title = title;
        this.levelCategoryId = levelCategoryId;
    }

    public long getLevelId() {
        return levelId;
    }

    public void setLevelId(long levelId) {
        this.levelId = levelId;
    }

    public long getLevelCategoryId() {
        return levelCategoryId;
    }

    public void setLevelCategoryId(long levelCategoryId) {
        this.levelCategoryId = levelCategoryId;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
