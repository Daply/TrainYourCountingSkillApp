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

    @ColumnInfo(name = "numberOfQuestions")
    @NonNull
    private int numberOfQuestions = 0;

    @ColumnInfo(name = "time")
    @NonNull
    private long time = 0;

    public Record() {
        this.numberOfQuestions = 0;
        this.time = 0;
    }

    @Ignore
    public Record(int numberOfQuestions, long time) {
        this.numberOfQuestions = numberOfQuestions;
        this.time = time;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
