package com.example.fitnessclub;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "trainer",
        indices = {@Index(value = "full_name", unique = true)})

public class Trainer {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tid")
    public int id;

    @ColumnInfo(name = "full_name")
    @NonNull
    public String fullName;

    public Trainer(@NonNull String fullName) {
        this.fullName = fullName;
    }
}