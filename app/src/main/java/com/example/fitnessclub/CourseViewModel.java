package com.example.fitnessclub;

import android.arch.lifecycle.AndroidViewModel;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    private CourseDao courseDao;
    private LiveData<List<Course>> coursesLiveData;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        courseDao = CourseAppDatabase.getDatabase(application).courseDao();
        coursesLiveData = courseDao.getAllCourses();
    }

    public LiveData<List<Course>> getCourseList() {
        return coursesLiveData;
    }

    public void insert(Course... courses) {
        courseDao.insert(courses);
    }

    public void update(Course course) {
        courseDao.update(course);
    }

    public void deleteAll() {
        courseDao.deleteAll();
    }
}

