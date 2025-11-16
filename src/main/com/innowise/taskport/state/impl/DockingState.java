package com.innowise.taskport.state.impl;

import com.innowise.taskport.entity.Ship;
import com.innowise.taskport.state.ShipState;
import org.apache.logging.log4j.Level;

public class DockingState implements ShipState {
    @Override
    public void process(Ship ship) {
        log.log(Level.INFO, "{} is docking to berth", ship.getName());
        if (ship.getContainersCount() == 0) {
            ship.setState(new LoadingState());
        } else {
            ship.setState(new UnloadingState());
        }
    }
}
