package com.innowise.taskport.entity;

import com.innowise.taskport.exception.PortException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Semaphore;

public class Berth {
    private static final Logger log = LogManager.getLogger();
    private final Semaphore semaphore;

    public Berth(int berthsCount) {
        this.semaphore = new Semaphore(berthsCount, true);
    }

    public void acquireBerth(String shipName) throws PortException {
        try {
            semaphore.acquire();
            log.info("{} occupied the berth.", shipName);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new PortException(e);
        }
    }

    public void releaseBerth(String shipName) throws PortException{
        semaphore.release();
        log.info("{} released the berth.", shipName);
    }
}
