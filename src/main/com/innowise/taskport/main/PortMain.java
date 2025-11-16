package com.innowise.taskport.main;

import com.innowise.taskport.entity.Ship;
import com.innowise.taskport.exception.PortException;
import com.innowise.taskport.warehouse.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class PortMain {
    static void main() throws PortException {
        Warehouse warehouse = Warehouse.getInstance();
        List<Thread> ships = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            ships.add(new Ship("Ship #" + i, (int) (Math.random() * 15) ));
        }
    }
}
