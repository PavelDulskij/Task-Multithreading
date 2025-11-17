package com.innowise.taskport.state.impl;

import com.innowise.taskport.entity.Ship;
import com.innowise.taskport.exception.PortException;
import com.innowise.taskport.state.ShipState;
import org.apache.logging.log4j.Level;

import java.util.concurrent.TimeUnit;

public class DepartingState implements ShipState {
    @Override
    public void process(Ship ship) throws InterruptedException, PortException {
        log.log(Level.INFO, "{} is leaving port", ship.getName());
        TimeUnit.SECONDS.sleep(3);
        ship.getBerth().releaseBerth(ship.getName());
    }
}
