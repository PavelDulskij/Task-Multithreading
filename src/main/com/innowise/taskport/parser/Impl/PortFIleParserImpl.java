package com.innowise.taskport.parser.Impl;

import com.innowise.taskport.config.PortConfig;
import com.innowise.taskport.entity.Berth;
import com.innowise.taskport.entity.Ship;
import com.innowise.taskport.parser.PortFileParser;
import com.innowise.taskport.warehouse.Warehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PortFIleParserImpl implements PortFileParser {
    private static final String SEMICOLON_REGEX = ";";
    private static final Logger log = LogManager.getLogger();
    @Override
    public PortConfig parseFile(List<String> lines) {
        Warehouse warehouse = Warehouse.getInstance();
        List<Ship> ships =  new ArrayList<>();;
        int berthsCount = Integer.parseInt(lines.getFirst());
        int warehouseCapacity = Integer.parseInt(lines.get(1));
        Berth berth = new Berth(berthsCount);
        warehouse.setCapacity(warehouseCapacity);
        for (int i = 2; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(SEMICOLON_REGEX);
            ships.add(new Ship(parts[0], warehouse, berth, Integer.parseInt(parts[1])));
        }
        return new PortConfig(berth, warehouse, ships);
    }
}
