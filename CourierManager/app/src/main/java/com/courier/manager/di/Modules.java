package com.courier.manager.di;

import android.app.Application;

import androidx.room.Room;

import com.courier.manager.database.CourierDao;
import com.courier.manager.database.CourierDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

//clasa care "ofera" module prin Dagger Hilt
@Module
@InstallIn(SingletonComponent.class)
public class Modules {

    //modulul bazei de date Room
    @Provides
    @Singleton
    public static CourierDatabase provideCourierDatabase(Application application) {
        return Room.databaseBuilder(application, CourierDatabase.class, "Favorite Couriers")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    //modului clase DAO
    @Provides
    @Singleton
    public static CourierDao provideCourierDao(CourierDatabase courierDatabase) {
        return courierDatabase.courierDao();
    }
}
