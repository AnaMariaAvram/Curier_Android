package com.courier.manager.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//Clasa care defineste entitatea curier
@Entity(tableName = "couriers")
public class CourierEntity implements Serializable {

    @PrimaryKey
    private int id;
    private String name;
    private int yearsOfExperience;
    private int orders;

    public CourierEntity(int id, String name, int yearsOfExperience, int orders) {
        this.id = id;
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }
}
