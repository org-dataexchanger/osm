package org.dataexchanger.osm.example;

import org.dataexchanger.osm.SheetManager;
import org.dataexchanger.osm.SheetManagerBean;
import org.dataexchanger.osm.SheetManagerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static SheetManagerFactory sheetManagerFactory;
    public static void main(String[] args)
            throws ClassNotFoundException, IOException,
            NoSuchFieldException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {

        SheetManager sheetManager = new SheetManagerBean();
        sheetManager.scanMappedPackages("org.dataexchanger.osm.example");
        sheetManagerFactory = new SheetManagerFactory(sheetManager);

        List<Employee> employees = new ArrayList<Employee>();
        Employee e = new Employee();
        e.setName("Mainul");
        e.setAge("25");
        Address address = new Address();
        address.setId(3);
        address.setHouse("Forest Lodge");
        address.setStreet("S.S. Academy Road");
        e.setAddress(address);
        employees.add(e);

        sheetManagerFactory.export(employees);
    }
}
