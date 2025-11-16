package com.innowise.taskport.warehouse;

import com.innowise.taskport.entity.Ship;
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

    private static class Holder {
        private static final Warehouse INSTANCE = new Warehouse();
    }

    public static Warehouse getInstance() {
        return Holder.INSTANCE;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrent() {
        return current;
    }

    public int getCapacity() {
        return capacity;
    }

    public void unloadShip(Ship ship) {
        lock.lock();
        try {
            int amount = ship.getContainersCount();

            log.info("{} wants to UNLOAD {} containers", ship.getName(), amount);

            while (current + amount > capacity) {
                log.info("{} is waiting: not enough space in warehouse. current={}, capacity={}",
                        ship.getName(), current, capacity);
                notFull.await();
            }

            current += amount;
            log.info("{} UNLOADED {} containers. Warehouse: {}/{}",
                    ship.getName(), amount, current, capacity);

            notEmpty.signalAll();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Unload interrupted for {}", ship.getName(), e);
        } finally {
            lock.unlock();
        }
    }

    public void loadShip(Ship ship) {
        lock.lock();
        try {
            log.info("{} wants to LOAD containers", ship.getName());

            while (current < ship.getContainersCount()) {
                log.info("{} is waiting: not enough containers in warehouse. current={}",
                        ship.getName(), current);
                notEmpty.await();
            }

            current -= ship.getContainersCount();
            log.info("{} LOADED {} containers. Warehouse: {}/{}",
                    ship.getName(), ship.getContainersCount(), current, capacity);
            notFull.signalAll();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Load interrupted for {}", ship.getName(), e);
        } finally {
            lock.unlock();
        }
    }
}
