package com.innowise.taskport.state.impl;

import com.innowise.taskport.entity.Ship;
import com.innowise.taskport.state.ShipState;
import org.apache.logging.log4j.Level;

import java.util.concurrent.TimeUnit;

public class DepartingState implements ShipState {
    @Override
    public void process(Ship ship)  {
        try {
            log.log(Level.INFO, "{} is leaving berth", ship.getName());
            TimeUnit.SECONDS.sleep(SECONDS);
            ship.getWarehouse().releaseBerth(ship.getBerth());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
