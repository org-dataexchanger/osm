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

        Address address1 = new Address();
        address1.setId(3);
        address1.setHouse("Forest Lodge");
        address1.setStreet("S.S. Academy Road");

        Address address2 = new Address();
        address2.setId(4);
        address2.setHouse("Avijan 9/2");
        address2.setStreet("S.S. Academy Road");

        List<Employee> employees = new ArrayList<>();
        Employee e1 = new Employee();
        e1.setId(1L);
        e1.setName("Mainul");
        e1.setAge(25);
        e1.setAddress(address1);
        employees.add(e1);

        Employee e2 = new Employee();
        e2.setId(2L);
        e2.setName("Hasan");
        e2.setAge(26);
        e2.setAddress(address2);
        employees.add(e2);

        Employee e3 = new Employee();
        e3.setId(3L);
        e3.setName("Siam");
        e3.setAge(16);
        e3.setAddress(address1);
        employees.add(e3);

        for (Employee e : employees) {
            sheetManagerFactory.prepareWorkbook(e);
        }
        sheetManagerFactory.writeWorkbookAsFile();
    }
}
