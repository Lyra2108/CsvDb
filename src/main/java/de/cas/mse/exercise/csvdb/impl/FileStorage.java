package de.cas.mse.exercise.csvdb.impl;

import de.cas.mse.exercise.csvdb.data.Address;
import de.cas.mse.exercise.csvdb.data.DbObject;
import de.cas.mse.exercise.csvdb.data.DbObjectFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileStorage<T extends DbObject> implements Storage<T> {

    private static final String CSV_SEPARATOR = ",";
    protected final Path basePath = Paths.get("data").toAbsolutePath();
    private DbObjectFactory<T> objectFactory;

    public FileStorage(DbObjectFactory<T> objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public T loadObject(String guid) {
        final Path tableFile = determineTableFile();
        try {
            final List<String> lines = Files.readAllLines(tableFile);
            final Optional<String> matchedObject = lines.stream().filter(e -> e.startsWith(guid)).findAny();
            return  objectFactory.create(
                    matchedObject.orElseThrow(() -> new RuntimeException("object with guid " + guid + "not found")),
                            CSV_SEPARATOR);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> loadAllObjects() {
        final Path tableFile = determineTableFile();
        try {
            final List<String> lines = Files.readAllLines(tableFile);
            return lines.stream().map(e -> objectFactory.create(e, CSV_SEPARATOR)).collect(Collectors.toList());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void storeObject(T databaseObject) {
        String csvLine = databaseObject.toCsvLine(CSV_SEPARATOR);
        final Path tableFile = determineTableFile();
        try (final RandomAccessFile file = new RandomAccessFile(tableFile.toFile(), "rw")) {
            file.seek(file.length());
            file.writeBytes(csvLine);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    protected Path determineTableFile() {
        return basePath.resolve("Address.csv");
    }


}
