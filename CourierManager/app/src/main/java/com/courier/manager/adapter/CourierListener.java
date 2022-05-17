package com.courier.manager.adapter;

import com.courier.manager.database.CourierEntity;

//interfata care defineste tipurile de listener pentru butoanele de pe recyclerview
public interface CourierListener {
    void saveCourier(CourierEntity courier);

    void deleteCourier(CourierEntity courier);

    void shareCourier(CourierEntity courier);
}