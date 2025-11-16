package com.innowise.taskport.state.impl;

import com.innowise.taskport.entity.Ship;
import com.innowise.taskport.exception.PortException;
import com.innowise.taskport.state.ShipState;
import org.apache.logging.log4j.Level;

public class DepartingState implements ShipState {
    @Override
    public void process(Ship ship) throws PortException {
        log.log(Level.INFO, "{} is leaving port", ship.getName());
        ship.getBerth().releaseBerth(ship.getName());
    }
}
