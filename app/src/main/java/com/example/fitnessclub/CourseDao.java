package com.example.fitnessclub;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CourseDao {

    @Query("SELECT * FROM courses WHERE title = :title LIMIT 1")
    Course findCourseByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course... courses);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Course course);

    @Query("DELETE FROM courses")
    void deleteAll();

    @Query("SELECT * FROM courses ORDER BY title ASC")
    LiveData<List<Course>> getAllCourses();
}

