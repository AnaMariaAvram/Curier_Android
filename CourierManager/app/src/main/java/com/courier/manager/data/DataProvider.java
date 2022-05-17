package com.courier.manager.data;

import com.courier.manager.database.CourierEntity;

import java.util.ArrayList;

//clasa care umple lista curierilor
public class DataProvider {
    public ArrayList<CourierEntity> getData() {
        ArrayList<CourierEntity> couriers = new ArrayList<CourierEntity>();
        couriers.add(new CourierEntity(1, "John Williams", 3, 150));
        couriers.add(new CourierEntity(2, "Mihai Daniel", 1, 100));
        couriers.add(new CourierEntity(3, "Ion Gica", 2, 10));
        couriers.add(new CourierEntity(4, "Blanche Brownlow", 6, 50));
        couriers.add(new CourierEntity(5, "Mihaela Letitia", 10, 221));
        couriers.add(new CourierEntity(6, "Angelia Sharlene", 5, 301));
        couriers.add(new CourierEntity(7, "Becci Carolyn", 1, 15));
        couriers.add(new CourierEntity(8, "Aureole Mondy", 1, 22));
        couriers.add(new CourierEntity(9, "Sorina Renie", 0, 43));
        couriers.add(new CourierEntity(10, "Jeannine Pope", 3, 67));
        couriers.add(new CourierEntity(11, "Seth Richards", 10, 56));
        couriers.add(new CourierEntity(12, "Shawn Richards", 5, 98));
        couriers.add(new CourierEntity(13, "Alice Carolyn", 1, 3));
        couriers.add(new CourierEntity(14, "Aureole Zoe", 2, 40));
        couriers.add(new CourierEntity(15, "Sorina Ayla", 0, 5));
        couriers.add(new CourierEntity(16, "Katy Pope", 3, 180));
        return couriers;
    }
}
