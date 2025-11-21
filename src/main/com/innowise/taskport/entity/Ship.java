package com.innowise.taskport.entity;

import com.innowise.taskport.state.ShipState;
import com.innowise.taskport.state.impl.DepartingState;
import com.innowise.taskport.state.impl.WaitingState;
import com.innowise.taskport.warehouse.Warehouse;

public class Ship implements Runnable {
    private final String name;
    private final Warehouse warehouse;
    private Berth berth;
    private int containersCount;
    private int amountToMove;
    private int shipCapacity;
    private ShipState state;

    public Ship(String name, Warehouse warehouse,
                int containersCount, int amountToMove, int shipCapacity) {

        this.name = name;
        this.warehouse = warehouse;
        this.containersCount = containersCount;
        this.amountToMove = amountToMove;
        this.shipCapacity = shipCapacity;
        this.state = new WaitingState();
    }

    public String getName() {
        return name;
    }

    public int getContainersCount() {
        return containersCount;
    }

    public void setContainersCount(int containersCount) {
        this.containersCount = containersCount;
    }

    public int getAmountToLoad() {
        return shipCapacity;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setBerth(Berth berth) {
        this.berth = berth;
    }

    public Berth getBerth() {
        return berth;
    }

    public void setState(ShipState state) {
        this.state = state;
    }

    @Override
    public void run() {
        while (!(state instanceof DepartingState)) {
            state.process(this);
        }
        state.process(this);
    }
}
