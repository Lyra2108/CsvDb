package de.cas.mse.exercise.csvdb.impl;

import de.cas.mse.exercise.csvdb.data.Address;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryStorage implements Storage<Address> {
    private HashMap<String, Address> addresses = new HashMap<>();

    @Override
    public Address loadObject(String guid) {
        return addresses.get(guid);
    }

    @Override
    public List<Address> loadAllObjects() {
        return new ArrayList<>(addresses.values());
    }

    @Override
    public void storeObject(Address address) {
        addresses.put(address.getGuid(), address);
    }
}
