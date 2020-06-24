package org.dataexchanger.osm.example;

import org.dataexchanger.osm.annotations.*;

public class Address {

    @Id
    private Integer id;
    private String house;
    private String street;

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
