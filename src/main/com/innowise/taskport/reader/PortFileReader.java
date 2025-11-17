package com.innowise.taskport.reader;

import com.innowise.taskport.exception.PortException;

import java.util.List;

public interface PortFileReader {
    List<String> readFile(String path) throws PortException;
}
