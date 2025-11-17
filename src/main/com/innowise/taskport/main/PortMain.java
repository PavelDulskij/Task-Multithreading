package com.innowise.taskport.main;

import com.innowise.taskport.config.PortConfig;
import com.innowise.taskport.entity.Ship;
import com.innowise.taskport.exception.PortException;
import com.innowise.taskport.parser.Impl.PortFileParserImpl;
import com.innowise.taskport.parser.PortFileParser;
import com.innowise.taskport.reader.PortFileReader;
import com.innowise.taskport.reader.impl.PortFileReaderImpl;

import java.util.ArrayList;
import java.util.List;

public class PortMain {
    static void main() throws PortException, InterruptedException {
        PortFileReader reader = new PortFileReaderImpl();
        PortFileParser parser = new PortFileParserImpl();

        List<String> lines = reader.readFile("data/data.txt");
        PortConfig config = parser.parseFile(lines);

        List<Thread> threads = new ArrayList<>();
        for (Ship ship : config.ships()) {
            threads.add(new Thread(ship, ship.getName()));
        }

        threads.forEach(Thread::start);
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("====All ships are served====");
    }
}
