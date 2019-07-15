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
    @NonNull
    private int level = 0;

    @ColumnInfo(name = "numberOfQuestions")
    @NonNull
    private int numberOfQuestions = 0;

    @ColumnInfo(name = "time")
    @NonNull
    private int time = 0;

    /**
     * type: 0 - question, 1 - expression
     */
    @ColumnInfo(name = "type")
    @NonNull
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

    @NonNull
    public int getLevel() {
        return level;
    }

    public void setLevel(@NonNull int level) {
        this.level = level;
    }

    @NonNull
    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(@NonNull int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    @NonNull
    public int getTime() {
        return time;
    }

    public void setTime(@NonNull int time) {
        this.time = time;
    }

    /**
     * Type: question - 0, expression - 1
     * @return
     */
    @NonNull
    public int getType() {
        return type;
    }

    public void setType(@NonNull int type) {
        this.type = type;
    }
}
