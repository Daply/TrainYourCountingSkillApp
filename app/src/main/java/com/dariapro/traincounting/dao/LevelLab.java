package com.dariapro.traincounting.dao;

import android.content.Context;

import com.dariapro.traincounting.entity.Category;
import com.dariapro.traincounting.entity.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LevelLab {

    private Context appContext = null;
    private static LevelLab levelLab;

    private List<Level> levels;

    public static LevelLab get(Context context) {
        if (levelLab == null) {
            levelLab = new LevelLab(context);
        }
        return levelLab;
    }

    private LevelLab(Context context) {
        this.appContext = context;
        this.levels = new ArrayList<Level>();
        for (int i = 0; i < 10; i++) {
            Level level = new Level();
            level.setTitle("Level " + i);
            this.levels.add(level);
        }
    }

    public List<Level> getLevels() {
        return levels;
    }

    public Level getLevel(UUID id) {
        for (Level level : levels) {
            if (level.getLevelId().equals(id)) {
                return level;
            }
        }
        return null;
    }
}
