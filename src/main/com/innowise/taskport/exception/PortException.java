package com.innowise.taskport.exception;

public class PortException extends Exception {
    public PortException() {
        super();
    }
    public PortException(String message) {
        super(message);
    }
    public PortException(Throwable e) {
        super(e);
    }
    public PortException(String message, Throwable e) {
        super(message, e);
    }
}
