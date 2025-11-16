package com.innowise.taskport.reader.impl;

import com.innowise.taskport.config.PortConfig;
import com.innowise.taskport.entity.Berth;
import com.innowise.taskport.entity.Ship;
import com.innowise.taskport.exception.PortException;
import com.innowise.taskport.reader.PortFileReader;
import com.innowise.taskport.warehouse.Warehouse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PortFileReaderImpl implements PortFileReader {
    private static final Logger log = LogManager.getLogger();

    @Override
    public List<String> readFile(String path) throws PortException {
        List<String> lines;
        try {
            log.log(Level.INFO, "trying to read {} file", path);
            lines = Files.readAllLines(Path.of(path));
            log.log(Level.INFO, "{} file has been read successfully", path);
        } catch (IOException e) {
            throw new PortException(e);
        }
        return lines;
    }

}
