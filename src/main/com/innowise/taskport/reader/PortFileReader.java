package com.innowise.taskport.reader;

import com.innowise.taskport.config.PortConfig;
import com.innowise.taskport.exception.PortException;

import java.io.IOException;
import java.util.List;

public interface PortFileReader {
    List<String> readFile(String path) throws PortException;
}
