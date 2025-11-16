package com.innowise.taskport.config;

import com.innowise.taskport.entity.Berth;
import com.innowise.taskport.entity.Ship;
import com.innowise.taskport.warehouse.Warehouse;

import java.util.List;

public record PortConfig(Berth berths, Warehouse warehouse, List<Ship> ships) {
}
