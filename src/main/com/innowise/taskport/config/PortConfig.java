package com.innowise.taskport.config;

import com.innowise.taskport.entity.Berth;
import com.innowise.taskport.entity.Ship;
import com.innowise.taskport.warehouse.Warehouse;

import java.util.List;
import java.util.Queue;

public record PortConfig(Queue<Berth> berths, Warehouse warehouse, List<Ship> ships) {
}
