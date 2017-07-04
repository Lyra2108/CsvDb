package de.cas.mse.exercise.csvdb.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class InMemoryFile implements File {

    private List<String> lines = new ArrayList<String>();

    @Override
    public Stream<String> lines() {
        return lines.stream();
    }

    @Override
    public void writeLine(String csvLine) {
        lines.add(csvLine);
    }
}
