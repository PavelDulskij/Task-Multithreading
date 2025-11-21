package com.innowise.taskport.state.impl;

import com.innowise.taskport.entity.Berth;
import com.innowise.taskport.entity.Ship;
import com.innowise.taskport.state.ShipState;

public class WaitingState implements ShipState {
    @Override
    public void process(Ship ship) {
        try {
            log.info("{} is waiting for free birth", ship.getName());
            Berth berth = ship.getWarehouse().acquireBerth();
            ship.setBerth(berth);
            ship.setState(new DockingState());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

