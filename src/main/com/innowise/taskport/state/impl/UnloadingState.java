package com.innowise.taskport.state.impl;

import com.innowise.taskport.entity.Ship;
import com.innowise.taskport.state.ShipState;
import org.apache.logging.log4j.Level;

import java.util.concurrent.TimeUnit;

public class UnloadingState implements ShipState {
    @Override
    public void process(Ship ship) throws InterruptedException {
        log.log(Level.INFO, "{} is unloading", ship.getName());
        TimeUnit.SECONDS.sleep(SECONDS);
        ship.getWarehouse().unloadShip(ship);
        ship.setState(new DepartingState());
    }
}
