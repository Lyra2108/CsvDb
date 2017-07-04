package de.cas.mse.exercise.csvdb.impl;

import de.cas.mse.exercise.csvdb.CsvDB;
import de.cas.mse.exercise.csvdb.data.Address;

import java.util.List;
import java.util.UUID;

public class AddressDb implements CsvDB<Address> {

	private Storage<Address> storage;

	public AddressDb(Storage<Address> storage) {
		this.storage = storage;
	}

	@Override
	public Address loadObject(final String guid, final Class<Address> type) {
		return storage.loadObject(guid);
	}


	@Override
	public List<Address> loadAllObjects(final Class<Address> type) {
		return storage.loadAllObjects();
	}

	@Override
	public Address insert(final Address address) {
		setGuidIfNeeded(address);
		storage.storeObject(address);
		return address;
	}


	private void setGuidIfNeeded(final Address address) {
		if (address.getGuid() == null) {
			address.setGuid(createGuid());
		}
	}

	private String createGuid() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
}
