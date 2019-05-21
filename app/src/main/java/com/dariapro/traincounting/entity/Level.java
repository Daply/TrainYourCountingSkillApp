package com.dariapro.traincounting.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Level {
    private UUID levelId  = null;
    private boolean passed = false;
    private String title = null;
    private List<Question> examples = null;

    public Level() {
        this.levelId = UUID.randomUUID();
        this.title = new String("Level"+this.levelId);
        this.examples = new ArrayList<Question>();
    }

    public Level(boolean passed, String title, List<Question> examples) {
        this.passed = passed;
        this.title = title;
        this.examples = examples;
    }

    public UUID getLevelId() {
        return levelId;
    }

    public void setLevelId(UUID levelId) {
        this.levelId = levelId;
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
