package com.innowise.taskport.reader;

import com.innowise.taskport.config.PortConfig;
import com.innowise.taskport.exception.PortException;

import java.io.IOException;

public interface PortFileReader {
    PortConfig readFile(String path) throws PortException;
}
