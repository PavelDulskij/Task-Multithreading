package com.innowise.taskport.entity;

import com.innowise.taskport.exception.PortException;
import com.innowise.taskport.state.ShipState;
import com.innowise.taskport.state.impl.DepartingState;
import com.innowise.taskport.state.impl.WaitingState;
import com.innowise.taskport.warehouse.Warehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ship implements Runnable {
    private static final Logger log = LogManager.getLogger(Ship.class);
    private final String name;
    private final Warehouse warehouse;
    private final Berth berth;
    private int containersCount;
    private ShipState state;
    private int amountToLoad = 0;
    private int capacity = 0;

    public Ship(String name, Warehouse warehouse, Berth berth,
                int containersCount, int amountToLoad, int capacity) {
        this.name = name;
        this.warehouse = warehouse;
        this.berth = berth;
        this.containersCount = containersCount;
        this.state = new WaitingState();
        this.amountToLoad = amountToLoad;
        this.capacity = capacity;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAmountToLoad() {
        return amountToLoad;
    }

    public void setAmountToLoad(int amountToLoad) {
        this.amountToLoad = amountToLoad;
    }

    public void setContainersCount(int containersCount) {
        this.containersCount = containersCount;
    }

    public void setState(ShipState state) {
        this.state = state;
    }

    public void process() throws PortException, InterruptedException {
        while (!(state instanceof DepartingState)) {
            state.process(this);
        }
        state.process(this);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Ship{");
        sb.append("name='").append(name).append('\'');
        sb.append(", containers count=").append(containersCount);
        sb.append(", amount to load/unload=").append(amountToLoad);
        sb.append(", capacity=").append(capacity);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public void run() {
        try {
            process();
        } catch (PortException e) {
            Thread.currentThread().interrupt();
        } catch (InterruptedException e) {
            log.warn("{} was interrupted", Thread.currentThread().getName());
        }
    }
}
