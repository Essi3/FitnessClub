package com.example.fitnessclub;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {Course.class, Trainer.class}, version = 1)
public abstract class CourseAppDatabase extends RoomDatabase {

    private static CourseAppDatabase INSTANCE;
    private static final String DB_NAME = "courses.db";

    public static CourseAppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CourseAppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CourseAppDatabase.class, DB_NAME)
                            .allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Log.d("CourseAppDatabase", "populating with data...");
                                    new PopulateDbAsync(INSTANCE).execute();
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public void clearDb() {
        if (INSTANCE != null) {
            new PopulateDbAsync(INSTANCE).execute();
        }
    }
    public abstract CourseDao courseDao();
    public abstract TrainerDao trainerDao();
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final CourseDao courseDao;
        private final TrainerDao trainerDao;
        public PopulateDbAsync(CourseAppDatabase instance) {
            courseDao = instance.courseDao();
            trainerDao = instance.trainerDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            courseDao.deleteAll();
            trainerDao.deleteAll();
            Trainer trainer1 = new Trainer("Casper");
            Trainer trainer2 = new Trainer("Marcus");

            Course course1 = new Course("Soccer", (int) trainerDao.insert(trainer1));
            Course course2 = new Course("Karate", (int) trainerDao.insert(trainer2));

            courseDao.insert(course1, course2);
            return null;
        }
    }
}