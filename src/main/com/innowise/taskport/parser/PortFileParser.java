package com.innowise.taskport.parser;

import com.innowise.taskport.config.PortConfig;

import java.util.List;

public interface PortFileParser {
    PortConfig parseFile(List<String> lines);
}
