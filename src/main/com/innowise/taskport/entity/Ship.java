package com.innowise.taskport.entity;

import com.innowise.taskport.exception.PortException;
import com.innowise.taskport.state.ShipState;
import com.innowise.taskport.state.impl.DepartingState;
import com.innowise.taskport.state.impl.WaitingState;
import com.innowise.taskport.warehouse.Warehouse;

public class Ship implements Runnable {
    private final String name;
    private final Warehouse warehouse;
    private final Berth berth;
    private int containersCount;
    private ShipState state;

    public Ship(String name, Warehouse warehouse, Berth berth, int containersCount) {
        this.name = name;
        this.warehouse = warehouse;
        this.berth = berth;
        this.containersCount = containersCount;
        this.state = new WaitingState();
    }

    public String getName() {
        return name;
    }

    public int getContainersCount() {
        return containersCount;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public Berth getBerth() {
        return berth;
    }

    public ShipState getState() {
        return state;
    }

    public void setContainersCount(int containersCount) {
        this.containersCount = containersCount;
    }

    public void setState(ShipState state) {
        this.state = state;
    }

    public void process() throws PortException {
        while (!(state instanceof DepartingState)) {
            state.process(this);
        }
        state.process(this);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Ship{");
        sb.append("name='").append(name).append('\'');
        sb.append(", containersCount=").append(containersCount);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public void run() {
        try {
            process();
        } catch (PortException e) {
            Thread.currentThread().interrupt();
        }
    }
}
