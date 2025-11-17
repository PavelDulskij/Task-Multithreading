package com.innowise.taskport.reader.impl;

import com.innowise.taskport.exception.PortException;
import com.innowise.taskport.reader.PortFileReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PortFileReaderImpl implements PortFileReader {
    private static final Logger log = LogManager.getLogger();

    @Override
    public List<String> readFile(String path) throws PortException {
        if (path == null || path.isBlank()) {
            throw new PortException("File path cannot be null or empty");
        }
        List<String> lines;
        try {
            log.log(Level.INFO, "trying to read {} file", path);
            lines = Files.readAllLines(Path.of(path));
            log.info("Read {} lines from {}", lines.size(), path);
        } catch (IOException e) {
            throw new PortException(e);
        }
        return lines;
    }

}
