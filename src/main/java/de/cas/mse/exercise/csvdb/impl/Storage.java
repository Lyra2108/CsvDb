package de.cas.mse.exercise.csvdb.impl;

import de.cas.mse.exercise.csvdb.data.DbObject;

import java.util.List;

public interface Storage<T extends DbObject> {
    T loadObject(String guid);

    List<T> loadAllObjects();

    void storeObject(T databaseObject);
}
