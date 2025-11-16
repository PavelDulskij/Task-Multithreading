package com.innowise.taskport.entity;

import com.innowise.taskport.exception.PortException;
import com.innowise.taskport.state.ShipState;
import com.innowise.taskport.warehouse.Warehouse;

import java.util.concurrent.Callable;

public class Ship extends Thread {
    private final String name;
    private final Warehouse warehouse;
    private int containersCount;
    private ShipState state;

    public Ship(String name, Warehouse warehouse, int containersCount) {
        this.name = name;
        this.warehouse = warehouse;
        this.containersCount = containersCount;
    }

    public String getShipName() {
        return name;
    }

    public int getContainersCount() {
        return containersCount;
    }

    public void setContainersCount(int containersCount) {
        this.containersCount = containersCount;
    }

    public void setState(ShipState state) {
        this.state = state;
    }

    public void process() {
        state.process(this);
    }

    @Override
    public void run() {
        process();
        if (containersCount == 0) {
            warehouse.loadShip(this);
        } else {
            warehouse.unloadShip(this);
        }
    }
}
