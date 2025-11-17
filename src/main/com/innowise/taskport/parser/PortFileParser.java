package com.innowise.taskport.parser;

import com.innowise.taskport.config.PortConfig;
import com.innowise.taskport.exception.PortException;

import java.util.List;

public interface PortFileParser {
    PortConfig parseFile(List<String> lines) throws PortException;
}
