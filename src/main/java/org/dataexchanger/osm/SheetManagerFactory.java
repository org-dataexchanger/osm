package org.dataexchanger.osm;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class SheetManagerFactory {

    private SheetManager sheetManager;

    public SheetManagerFactory(SheetManager sheetManager) {
        this.sheetManager = sheetManager;
    }

    public <T>void export(List<T> objects) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String className = objects.get(0).getClass().getName();
        Map<String, List<String>> columnNamesMap = sheetManager.getMappedColumnNames();
        List<String> columnNames = columnNamesMap.get(className);
        StringBuilder methodNameBuilder = new StringBuilder();
        for (T object: objects) {
            for (String columnName: columnNames) {
                Class clazz = object.getClass();
                String value = "";
                if (columnName.contains("_")) {
                    columnName = columnName.split("_")[0];
                    String methodName = methodNameBuilder.append("get")
                            .append(columnName.substring(0, 1))
                            .append(columnName.substring(1))
                            .toString();
                    value = clazz.getMethod("getName").invoke(object).toString();
                }
            }
        }
    }
}
