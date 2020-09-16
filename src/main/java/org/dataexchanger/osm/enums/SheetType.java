package org.dataexchanger.osm.enums;

public enum SheetType {
    XLS(".xls");

    private String type;
    SheetType (String type) {
        this.type = type;
    }
}
