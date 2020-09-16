package org.dataexchanger.osm.example;

import org.dataexchanger.osm.annotations.*;

@SheetEntity(value = "address")
public class Address {

    @Column(name = "id", idField = true)
    private Integer id;
    @Column(name = "house")
    private String house;
    @Column(name = "street")
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
