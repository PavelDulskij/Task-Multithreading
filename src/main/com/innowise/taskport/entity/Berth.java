package com.innowise.taskport.entity;

public class Berth {
    private final String name;

    public Berth(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

        @Override
    public String toString() {
        return "Berth{" +
                "id=" + name +
                '}';
    }
}
