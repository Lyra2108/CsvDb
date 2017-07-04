package de.cas.mse.exercise.csvdb.impl;

import java.util.stream.Stream;

/**
 * Created by Sarah.Blume on 04/07/2017.
 */
public interface File {
    Stream<String> lines();

    void writeLine(String csvLine);
}
