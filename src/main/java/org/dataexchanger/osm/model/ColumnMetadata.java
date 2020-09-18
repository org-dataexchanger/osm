package org.dataexchanger.osm.model;

public class ColumnMetadata {
    private String name;
    private String mappedPropertyName;
    private Class type;
    private boolean isSheetEntity = false;
    private boolean idField;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getMappedPropertyName() {
        return mappedPropertyName;
    }

    public void setMappedPropertyName(String mappedPropertyName) {
        this.mappedPropertyName = mappedPropertyName;
    }

    public boolean isIdField() {
        return idField;
    }

    public void setIdField(boolean idField) {
        this.idField = idField;
    }

    public boolean isSheetEntity() {
        return isSheetEntity;
    }

    public void setSheetEntity(boolean sheetEntity) {
        isSheetEntity = sheetEntity;
    }
}
