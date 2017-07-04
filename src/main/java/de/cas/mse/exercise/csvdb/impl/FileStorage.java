package de.cas.mse.exercise.csvdb.impl;

import de.cas.mse.exercise.csvdb.data.DbObject;
import de.cas.mse.exercise.csvdb.data.DbObjectFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileStorage<T extends DbObject> implements Storage<T> {

    private static final String CSV_SEPARATOR = ",";
    private DbObjectFactory<T> objectFactory;
    private File file;

    public FileStorage(DbObjectFactory<T> objectFactory, File file) {
        this.objectFactory = objectFactory;
        this.file = file;
    }

    @Override
    public T loadObject(String guid) {
        final Optional<String> matchedObject = file.lines().filter(e -> e.startsWith(guid)).findAny();
        if (!matchedObject.isPresent())
            new RuntimeException("object with guid " + guid + "not found");

        return  objectFactory.create(matchedObject.get(), CSV_SEPARATOR);
    }

    @Override
    public List<T> loadAllObjects() {
        return file.lines().map(e -> objectFactory.create(e, CSV_SEPARATOR)).collect(Collectors.toList());
    }

    @Override
    public void storeObject(T databaseObject) {
        String csvLine = databaseObject.toCsvLine(CSV_SEPARATOR);
        file.writeLine(csvLine);
    }



}
