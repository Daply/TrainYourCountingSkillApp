package com.dariapro.traincounting.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * @author Pleshchankova Daria
 *
 */
@Entity(tableName = "record")
public class Record implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recordId")
    private long recordId;

    @ColumnInfo(name = "level")
    private int level;

    @ColumnInfo(name = "numberOfQuestions")
    private int numberOfQuestions;

    @ColumnInfo(name = "time")
    private int time;

    /**
     * type: 0 - question, 1 - expression
     */
    @ColumnInfo(name = "type")
    private int type = 0;

    public Record() {
        this.level = 0;
        this.numberOfQuestions = 0;
        this.time = 0;
    }

    @Ignore
    public Record(int level, int numberOfQuestions, int time) {
        this.level = level;
        this.numberOfQuestions = numberOfQuestions;
        this.time = time;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Type: question - 0, expression - 1
     * @return type
     */
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
