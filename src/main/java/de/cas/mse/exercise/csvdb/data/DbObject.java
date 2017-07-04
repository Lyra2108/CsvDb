package de.cas.mse.exercise.csvdb.data;

public interface DbObject {

	String getGuid();
	void setGuid(String guid);
	String toCsvLine(String separator);
}

