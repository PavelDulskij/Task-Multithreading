package com.innowise.taskport.entity;

import com.innowise.taskport.exception.PortException;

import java.util.concurrent.Semaphore;

public class Berth {
    private final Semaphore semaphore;

    public Berth(int berthsCount) {
        this.semaphore = new Semaphore(berthsCount, true);
    }

    public void acquireBerth() throws PortException {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new PortException(e);
        }
    }

    public void releaseBerth() {
        semaphore.release();
    }
}
