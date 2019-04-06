package com.example.fitnessclub;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TrainerDao {

    @Query("SELECT * FROM trainer WHERE tid = :id LIMIT 1")
    Trainer findTrainerById(int id);

    @Query("SELECT * FROM trainer WHERE full_name = :fullName LIMIT 1")
    Trainer findTrainerByName(String fullName);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Trainer trainer);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Trainer... trainers);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Trainer trainer);

    @Query("DELETE FROM trainer")
    void deleteAll();

    @Query("SELECT * FROM trainer ORDER BY full_name ASC")
    LiveData<List<Trainer>> getAllTrainers();
}
