package com.courier.manager.database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

//Clasa data access object. Aici avem metodele necesare de inserare/stergere/obtinere si filtrare a curierilor
//din baza de date
@Dao
public interface CourierDao {

    @Insert(onConflict = REPLACE)
    void insert(CourierEntity courier);

    @Delete
    void delete(CourierEntity courier);

    @Query("SELECT * FROM couriers")
    List<CourierEntity> getAll();

    @Query("SELECT * FROM couriers WHERE name LIKE :filter")
    List<CourierEntity> queryUsers(String filter);

}
