package com.innowise.taskport.warehouse;

import com.innowise.taskport.entity.Ship;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Warehouse {
    private static final Logger log = LogManager.getLogger();
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    private int capacity;
    private int current = 0;

    private Warehouse() {}

    private static class WarehouseHolder {
        private static final Warehouse INSTANCE = new Warehouse();
    }

    public static Warehouse getInstance() {
        return WarehouseHolder.INSTANCE;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void loadShip(Ship ship) {
        try {
            lock.lock();
            while (current < ship.getContainersCount()) {
                notEmpty.await();
                log.log(Level.INFO, "{} is waiting for containers", ship.getName());
            }
            current -= ship.getContainersCount();
            notFull.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
    public void unloadShip(Ship ship) {
        try {
            lock.lock();
            while (current + ship.getContainersCount() > capacity) {
                log.log(Level.INFO, "{} is waiting for space", ship.getName());
                notFull.await();
            }
            current += ship.getContainersCount();
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
