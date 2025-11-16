package com.innowise.taskport.reader.impl;

import com.innowise.taskport.config.PortConfig;
import com.innowise.taskport.entity.Berth;
import com.innowise.taskport.entity.Ship;
import com.innowise.taskport.exception.PortException;
import com.innowise.taskport.reader.PortFileReader;
import com.innowise.taskport.warehouse.Warehouse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PortFileReaderImpl implements PortFileReader {
    private static final String SEMICOLON_REGEX = ";";

    @Override
    public PortConfig readFile(String path) throws PortException {
        List<String> lines;
        Berth berth;
        Warehouse warehouse = Warehouse.getInstance();
        List<Ship> ships;
        try {
            lines = Files.readAllLines(Path.of(path));
            int berthsCount = Integer.parseInt(lines.getFirst());
            int warehouseCapacity = Integer.parseInt(lines.get(1));
            berth = new Berth(berthsCount);
            warehouse.setCapacity(warehouseCapacity);
            ships = new ArrayList<>();

            for (int i = 2; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(SEMICOLON_REGEX);
                ships.add(new Ship(parts[0], warehouse, berth, Integer.parseInt(parts[1])));
            }
        } catch (IOException e) {
            throw new PortException(e);
        }


        return new PortConfig(berth, warehouse, ships);
    }


}
