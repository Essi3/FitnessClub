package com.example.fitnessclub;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class TrainerViewModel extends AndroidViewModel {
    private TrainerDao trainerDao;
    private LiveData<List<Trainer>> trainersLiveData;

    public TrainerViewModel(@NonNull Application application) {
        super(application);
        trainerDao = CourseAppDatabase.getDatabase(application).trainerDao();
        trainersLiveData = trainerDao.getAllTrainers();
    }

    public LiveData<List<Trainer>> getTrainersList() {
        return trainersLiveData;
    }

    public void insert(Trainer... trainers) {
        trainerDao.insert(trainers);
    }

    public void update(Trainer trainer) {
        trainerDao.update(trainer);
    }

    public void deleteAll() {
        trainerDao.deleteAll();
    }
}

