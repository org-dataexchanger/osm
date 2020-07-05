package org.dataexchanger.osm.model;

public class ColumnMetadata {
    private String name;
    private String mappedPropertyName;
    private String getterMethodName;
    private Class type;
    private boolean idField;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGetterMethodName() {
        return getterMethodName;
    }

    public void setGetterMethodName(String getterMethodName) {
        this.getterMethodName = getterMethodName;
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
}
