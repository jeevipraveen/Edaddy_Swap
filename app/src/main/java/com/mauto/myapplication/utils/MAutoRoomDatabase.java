package com.mauto.myapplication.utils;

import android.content.Context;

import com.mauto.myapplication.login.model.LoginModel;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities =
        {LoginModel.class}
        , version = 1
        , exportSchema = false)

public abstract class MAutoRoomDatabase extends RoomDatabase {
    @VisibleForTesting
    private static final String DATABASE_NAME = "MAutoSales-Database";
    private static MAutoRoomDatabase sInstance;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    private static MAutoRoomDatabase getDatabase(final Context context) {
        synchronized (MAutoRoomDatabase.class) {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MAutoRoomDatabase.class, DATABASE_NAME)
                        .allowMainThreadQueries()
                        //  for version increased, fallback to destructive migration enabled — database is cleared
                        .fallbackToDestructiveMigration()
                        .build();

            }
        }

        return sInstance;
    }

    public static MAutoRoomDatabase getInstance(final Context context) {
        synchronized (MAutoRoomDatabase.class) {
            if (sInstance == null) {
                sInstance = getDatabase(context.getApplicationContext());
                       }
        }

        return sInstance;
    }




    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

}
