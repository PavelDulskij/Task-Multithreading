package com.innowise.taskport.warehouse;

import com.innowise.taskport.entity.Ship;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Warehouse {
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    private final int CAPACITY = 100;
    private int current = 0;

    private Warehouse() {}

    private static class WarehouseHolder {
        private static final Warehouse INSTANCE = new Warehouse();
    }

    public static Warehouse getInstance() {
        return WarehouseHolder.INSTANCE;
    }

    public void loadShip(Ship ship) {
        try {
            lock.lock();
            while (current < ship.getContainersCount()) {
                notEmpty.await();
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
            while (current + ship.getContainersCount() > CAPACITY) {
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
