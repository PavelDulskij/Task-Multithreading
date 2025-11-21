package com.innowise.taskport.warehouse;

import com.innowise.taskport.entity.Berth;
import com.innowise.taskport.entity.Ship;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Warehouse {
    private static final Logger log = LogManager.getLogger();
    private final Lock lock = new ReentrantLock();
    private final Condition berthAvailable = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    private Queue<Berth> berths = new ArrayDeque<>();
    private AtomicInteger warehouseCapacity;
    private AtomicInteger current = new AtomicInteger(0);

    private Warehouse() {}

    private static class WarehouseHolder {
        private static final Warehouse INSTANCE = new Warehouse();
    }

    public static Warehouse getInstance() {
        return WarehouseHolder.INSTANCE;
    }

    public void setBerths(Queue<Berth> berths) {
        this.berths = berths;
    }

    public void setCapacity(int warehouseCapacity) {
        this.warehouseCapacity = new AtomicInteger(warehouseCapacity);
    }

    public Berth acquireBerth() throws InterruptedException {
        lock.lock();
        try {
            while (berths.isEmpty()) {
                log.info("{} is waiting for free berth", Thread.currentThread().getName());
                berthAvailable.await();
            }
            Berth berth = berths.poll();
            log.info("{} acquired {}", Thread.currentThread().getName(), berth.getName());
            return berth;
        } finally {
            lock.unlock();
        }
    }

    public void releaseBerth(Berth berth) {
        lock.lock();
        try {
            berths.add(berth);
            log.info("{} released {}. Available berths: {}", Thread.currentThread().getName(),
                    berth.getName(), berths.size());
            berthAvailable.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void unloadShip(Ship ship) {
        lock.lock();
        try {
            int amount = ship.getContainersCount();
            log.info("{} wants to unload {} containers", ship.getName(), amount);

            while (current.get() + amount > warehouseCapacity.get()) {
                log.info("{} is waiting: not enough space in warehouse. current={}, capacity={}",
                        ship.getName(), current.get(), warehouseCapacity.get());
                notFull.await();
            }

            current.getAndAdd(amount);
            ship.setContainersCount(0);

            log.info("{} unloaded {} containers. Warehouse = {}/{}",
                    ship.getName(), amount, current.get(), warehouseCapacity.get());

            notEmpty.signalAll();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Unload interrupted for {}", ship.getName());
        } finally {
            lock.unlock();
        }
    }

    public void loadShip(Ship ship) {
        lock.lock();
        try {
            int amount = ship.getAmountToMove();
            log.info("{} wants to load {} containers", ship.getName(), amount);

            while (current.get() < amount) {
                log.info("{} is waiting: not enough containers in warehouse. current={}",
                        ship.getName(), current.get());
                notEmpty.await();
            }

            current.addAndGet(-amount);
            ship.setContainersCount(ship.getContainersCount() + amount);

            log.info("{} loaded {} containers. Warehouse = {}/{}",
                    ship.getName(), amount, current.get(), warehouseCapacity.get());

            notFull.signalAll();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Load interrupted for {}", ship.getName());
        } finally {
            lock.unlock();
        }
    }
}
