package de.cas.mse.exercise.csvdb.impl;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import de.cas.mse.exercise.csvdb.data.AddressFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.cas.mse.exercise.csvdb.data.Address;

public class AddressDbTest {
	private InMemoryFile file;
	private AddressDb addressDb;

	@Before
	public void setup() {
		file = new InMemoryFile();
		addressDb = new AddressDb(new FileStorage<Address>(new AddressFactory(), file));
	}

	@Test
	public void insert_shouldContainOneLineInFile() throws Exception{
		final Address a = new Address();
		a.setName("test");
		a.setStreet("str");
		a.setTown("ort");
		a.setZip("23553");

		addressDb.insert(a);

		assertEquals(1, file.lines().count());
		assertEquals(a.toCsvLine(","), file.lines().findFirst());
	}

}
