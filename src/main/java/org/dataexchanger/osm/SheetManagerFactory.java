package org.dataexchanger.osm;

import org.dataexchanger.osm.model.ColumnMetadata;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SheetManagerFactory {


    private SheetManager sheetManager;
    private List<Map<String,String>> exported;

    SheetManagerFactory(SheetManager sheetManager) {
        this.exported = new ArrayList<>();
        this.sheetManager = sheetManager;
    }

    public <T> void export(List<T> objects) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        String className = objects.get(0).getClass().getName();
        Map<String, List<ColumnMetadata>> columnNamesMap = sheetManager.getMappedColumnMetadata();
        List<ColumnMetadata> columnMetadataList = columnNamesMap.get(className);
        for (T object : objects) {
            Map<String, String> columnValue = new HashMap<>();
            /*for (ColumnMetadata metadata : columnMetadataList) {
                Class clazz = object.getClass();
                String value = "";
                if (metadata.contains("_")) {
                    String[] splittedPropertyName = columnName.split("_");
                    Object obj = clazz.getMethod(getMethodName(splittedPropertyName[0])).invoke(object);
                    String aggregatedClassName = obj.getClass().getName();
                    Class aggregatedClass = Class.forName(aggregatedClassName);
                    value = aggregatedClass.getMethod(getMethodName(splittedPropertyName[1])).invoke(obj).toString();
                }
                else {
                    value = clazz.getMethod(getMethodName(columnName)).invoke(object).toString();
                }
                columnValue.put(columnName, value);
            }*/
            exported.add(columnValue);
        }
    }

    private String getMethodName(String columnName) {
        StringBuilder methodNameBuilder = new StringBuilder();
        return methodNameBuilder.append("get")
                .append(columnName.substring(0, 1).toUpperCase())
                .append(columnName.substring(1))
                .toString();
    }
}
