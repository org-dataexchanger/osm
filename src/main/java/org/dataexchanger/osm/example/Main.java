package org.dataexchanger.osm.example;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.dataexchanger.osm.annotations.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        List<Employee> employees = new ArrayList<Employee>();
        Employee e = new Employee();
        e.setName("Mainul");
        e.setAge("25");
        Address address = new Address();
        address.setHouse("Forest Lodge");
        address.setStreet("S.S. Academy Road");
        e.setAddress(address);
        employees.add(e);

        exportData(employees);
    }

    private static <T> void exportData(List<T> list) throws ClassNotFoundException {
//        ParameterizedType listType = list.getClass().getTypeParameters()[0];
        String className = list.get(0).getClass().getName();
        String[] packageNames = className.split("\\.");
        String basePackageName = "";
        if (packageNames.length > 2) {
            basePackageName = packageNames[0] + "." + packageNames[1];
        }
        Class aClass = Class.forName(className);
        List<String> columnNames = new LinkedList<>();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().getName().startsWith(basePackageName)) {
                Class aggragatedClass = Class.forName(field.getType().getName());

                Field[] aggragatedFields = aggragatedClass.getDeclaredFields();
                for (Field aggragatedField : aggragatedFields) {
                    Annotation aggragatedFieldAnnotation = aggragatedField.getAnnotation(Id.class);
                    if (aggragatedField.getAnnotation(Id.class) != null) {
                        String value = ((Id) aggragatedFieldAnnotation).value();
                        columnNames.add(field.getName() + "_" + value);
                    }
                }
            } else {
                columnNames.add(field.getName());
            }
        }
        System.out.println(columnNames.toString());
    }
}
