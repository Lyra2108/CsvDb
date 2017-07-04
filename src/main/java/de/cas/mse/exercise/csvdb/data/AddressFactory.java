package de.cas.mse.exercise.csvdb.data;

public class AddressFactory implements DbObjectFactory<Address> {
    @Override
    public Address create(String csvString, String separator) {
        final String[] split = csvString.split(separator);
        final Address address = new Address();
        address.setGuid(split[0]);
        address.setName(split[1]);
        address.setStreet(split[2]);
        address.setZip(split[3]);
        address.setTown(split[4]);
        return address;
    }
}
