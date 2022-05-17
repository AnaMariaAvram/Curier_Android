package com.courier.manager.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//Clasa necesara bazei de date Room
@Database(entities = {CourierEntity.class}, version = 1, exportSchema = false)
public abstract class CourierDatabase extends RoomDatabase {
    public abstract CourierDao courierDao();
}
