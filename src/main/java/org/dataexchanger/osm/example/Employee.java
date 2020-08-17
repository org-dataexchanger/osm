package org.dataexchanger.osm.example;

import org.dataexchanger.osm.annotations.Column;
import org.dataexchanger.osm.annotations.SheetEntity;

import java.util.ArrayList;
import java.util.List;

@SheetEntity(value = "employee")
public class Employee {
    @Column(name = "id", idField = true)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;
    @Column(name = "address_id")
    private Address address;
    @Column(name = "employee_contact")
    private List<Contact> contacts;

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

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
