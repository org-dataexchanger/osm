package org.dataexchanger.osm.example;

import org.dataexchanger.osm.annotations.Column;
import org.dataexchanger.osm.annotations.SheetEntity;

@SheetEntity(value = "employee")
public class Employee {
    @Column(name = "id", getterMethodName = "getId", idField = true)
    private Long id;
    @Column(name = "name", getterMethodName = "getName")
    private String name;
    @Column(name = "age", getterMethodName = "getAge")
    private Integer age;
    @Column(name = "address", getterMethodName = "getAddress")
    private Address address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
