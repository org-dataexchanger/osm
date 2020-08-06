package org.dataexchanger.osm;

import org.dataexchanger.osm.example.Address;
import org.dataexchanger.osm.example.Employee;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SheetManagerFactoryTest {

    private SheetManagerFactory sheetManagerFactory;
    @Before
    public void setup() throws IOException, ClassNotFoundException {
        sheetManagerFactory = new SheetManagerFactory("org.dataexchanger.osm.example");
    }

    @Test
    public void test_export() throws IllegalAccessException, IOException {

        Employee e = new Employee();
        e.setName("Mainul");
        e.setAge(25);
        Address address = new Address();
        address.setId(3);
        address.setHouse("Forest Lodge");
        address.setStreet("S.S. Academy Road");
        e.setAddress(address);

        sheetManagerFactory.export(e);
    }
}
