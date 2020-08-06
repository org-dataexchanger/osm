package org.dataexchanger.osm;

import org.dataexchanger.osm.model.ColumnMetadata;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SheetManagerFactory {

    private SheetManager sheetManager;
    private List<Map<String,String>> exported;

    SheetManagerFactory(SheetManager sheetManager) {
        this.exported = new ArrayList<>();
        this.sheetManager = OsmContextHolder.getContext().getSheetManager();
    }

    public <T> void export(List<T> objects) throws IllegalAccessException {
        String className = objects.get(0).getClass().getName();
        Map<String, List<ColumnMetadata>> sheetColumnsMetadataMap = sheetManager.getMappedColumnMetadata();
        List<ColumnMetadata> columnMetadataList = sheetColumnsMetadataMap.get(className);
        String packageName = OsmContextHolder.getContext().getScannedPackageName();
        for (T object : objects) {
            Class clazz = object.getClass();
            for(Field field : clazz.getDeclaredFields()){
                field.setAccessible(true);
                Object value = null != field.get(object) ? field.get(object) : "";
                System.out.println(value);
                if (value.getClass().getName().contains(packageName)) {
                    String aggregatedFiledName = value.getClass().getName();
                }
            }
            Map<String, String> columnValue = new HashMap<>();
            for (ColumnMetadata metadata : columnMetadataList) {

                String value = "";
                /*if (metadata.contains("_")) {
                    String[] splittedPropertyName = columnName.split("_");
                    Object obj = clazz.getMethod(getMethodName(splittedPropertyName[0])).invoke(object);
                    String aggregatedClassName = obj.getClass().getName();
                    Class aggregatedClass = Class.forName(aggregatedClassName);
                    value = aggregatedClass.getMethod(getMethodName(splittedPropertyName[1])).invoke(obj).toString();
                }
                else {
                    value = clazz.getMethod(getMethodName(columnName)).invoke(object).toString();
                }
                columnValue.put(columnName, value);*/
            }
            exported.add(columnValue);
        }
    }

    private String getFieldValue(Field field) {
        field.getClass().getName();
        return "";
    }

    private String getMethodName(String columnName) {
        StringBuilder methodNameBuilder = new StringBuilder();
        return methodNameBuilder.append("get")
                .append(columnName.substring(0, 1).toUpperCase())
                .append(columnName.substring(1))
                .toString();
    }
}
