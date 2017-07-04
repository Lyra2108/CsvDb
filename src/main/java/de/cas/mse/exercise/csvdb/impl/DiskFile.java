package de.cas.mse.exercise.csvdb.impl;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DiskFile implements File {
    private final Path basePath = Paths.get("data").toAbsolutePath();
    private Path determineTableFile() {
        return basePath.resolve("Address.csv");
    }

    @Override
    public Stream<String> lines(){
        final Path tableFile = determineTableFile();
        try {
            return Files.readAllLines(tableFile).stream();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeLine(String csvLine) {
        final Path tableFile = determineTableFile();
        try (final RandomAccessFile file = new RandomAccessFile(tableFile.toFile(), "rw")) {
            file.seek(file.length());
            file.writeBytes(csvLine);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
