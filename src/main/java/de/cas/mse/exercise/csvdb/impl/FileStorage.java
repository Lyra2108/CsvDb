package de.cas.mse.exercise.csvdb.impl;

import de.cas.mse.exercise.csvdb.data.Address;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileStorage implements Storage<Address> {

    private static final String CSV_SEPARATOR = ",";
    protected final Path basePath = Paths.get("data").toAbsolutePath();

    @Override
    public Address loadObject(String guid) {
        final Path tableFile = determineTableFile();
        try {
            final List<String> lines = Files.readAllLines(tableFile);
            final Optional<String> matchedAddress = lines.stream().filter(e -> e.startsWith(guid)).findAny();
            return turnToAddress(
                    matchedAddress.orElseThrow(() -> new RuntimeException("address with guid " + guid + "not found")));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Address> loadAllObjects() {
        final Path tableFile = determineTableFile();
        try {
            final List<String> lines = Files.readAllLines(tableFile);
            return lines.stream().map(e -> turnToAddress(e)).collect(Collectors.toList());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void storeObject(Address address) {
        String csvLine = toCsvLine(address);
        final Path tableFile = determineTableFile();
        try (final RandomAccessFile file = new RandomAccessFile(tableFile.toFile(), "rw")) {
            file.seek(file.length());
            file.writeBytes(csvLine);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    protected String toCsvLine(final Address address) {
        return address.getGuid() + CSV_SEPARATOR + address.getName() + CSV_SEPARATOR + address.getStreet()
                + CSV_SEPARATOR + address.getZip() + CSV_SEPARATOR + address.getTown();
    }

    protected Path determineTableFile() {
        return basePath.resolve("Address.csv");
    }

    private Address turnToAddress(final String addressLine) {
        final String[] split = addressLine.split(CSV_SEPARATOR);
        final Address addressObject = new Address();
        addressObject.setGuid(split[0]);
        addressObject.setName(split[1]);
        addressObject.setStreet(split[2]);
        addressObject.setZip(split[3]);
        addressObject.setTown(split[4]);
        return addressObject;
    }
}
