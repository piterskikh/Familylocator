package com.exubit.familylocator.core;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.exubit.familylocator.dao.AppDatabase;

public class App extends Application {

    private static AppDatabase db;

    public static AppDatabase getDb() {
        return db;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "familyLocator").build();
    }


}
