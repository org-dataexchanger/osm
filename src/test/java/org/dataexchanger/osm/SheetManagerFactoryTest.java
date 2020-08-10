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
        SheetManager sheetManagerBean = new SheetManagerBean();
        sheetManagerBean.scanMappedPackages("org.dataexchanger.osm.example");
        sheetManagerFactory = new SheetManagerFactory(sheetManagerBean);
    }

    @Test
    public void test_export() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Employee> employees = new ArrayList<Employee>();
        Employee e = new Employee();
        e.setName("Mainul");
        e.setAge(25);
        Address address = new Address();
        address.setId(3);
        address.setHouse("Forest Lodge");
        address.setStreet("S.S. Academy Road");
        e.setAddress(address);
        employees.add(e);

        sheetManagerFactory.export(employees);
    }
}
