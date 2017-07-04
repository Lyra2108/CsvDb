package de.cas.mse.exercise.csvdb.data;

public interface DbObjectFactory<T extends DbObject>{
	T create(String csvString, String separator);
}
