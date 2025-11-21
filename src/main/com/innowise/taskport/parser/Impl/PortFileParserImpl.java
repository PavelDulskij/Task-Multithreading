package com.innowise.taskport.parser.Impl;

import com.innowise.taskport.config.PortConfig;
import com.innowise.taskport.entity.Berth;
import com.innowise.taskport.entity.Ship;
import com.innowise.taskport.exception.PortException;
import com.innowise.taskport.parser.PortFileParser;
import com.innowise.taskport.warehouse.Warehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PortFileParserImpl implements PortFileParser {
    private static final String SEMICOLON_REGEX = ";";
    private static final Logger log = LogManager.getLogger();
    @Override
    public PortConfig parseFile(List<String> lines) throws PortException {
        log.info("Trying to parse file...");
        if(lines.isEmpty()) {
            throw new PortException("File is empty");
        }
        Warehouse warehouse = Warehouse.getInstance();
        List<Ship> ships =  new ArrayList<>();
        int berthsCount = Integer.parseInt(lines.getFirst());
        int warehouseCapacity = Integer.parseInt(lines.get(1));
        warehouse.setCapacity(warehouseCapacity);
        Queue<Berth> berths = new LinkedList<>();
        for (int i = 1; i <= berthsCount; i++) {
            berths.add(new Berth("Berth-" + i));
        }
        warehouse.setBerths(berths);

        for (int i = 2; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(SEMICOLON_REGEX);
            ships.add(new Ship(parts[0], warehouse, Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]), Integer.parseInt(parts[3])));
        }
        log.info("Parsed {} ships, berthCount={}, warehouseCapacity={}",
                ships.size(), berthsCount, warehouseCapacity);
        return new PortConfig(berths, warehouse, ships);
    }
}
