package com.example.fitnessclub;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "courses",
        foreignKeys = @ForeignKey(entity = Trainer.class,
                parentColumns = "tid",
                childColumns = "trainerId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("title"), @Index("trainerId")})

public class Course {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cid")
    public int id;

    @ColumnInfo(name = "title")
    @NonNull
    public String title;

    @ColumnInfo(name = "trainerId")
    public int trainerId;

    public Course(@NonNull String title, int trainerId) {
        this.title = title;
        this.trainerId = trainerId;
    }
}