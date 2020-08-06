package org.dataexchanger.osm;

import org.dataexchanger.osm.annotations.SheetEntity;
import org.dataexchanger.osm.model.ColumnMetadata;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SheetManagerFactory {

    private SheetManager sheetManager;
    private Map<String, Map<String,String>> exportableMap;
    private SheetExporter sheetExporter;

    SheetManagerFactory(String mappedPackageName) throws IOException, ClassNotFoundException {
        this.exportableMap = new HashMap<>();
        this.sheetManager = new SheetManagerBean();
        this.sheetManager.scanMappedPackages(mappedPackageName);
        this.sheetExporter = this.sheetManager.getSheetExporter();
    }

    public <T> void export(T object) throws IllegalAccessException, IOException {
        process(object);
        sheetExporter.writeWorkbookAsFile();
    }

    public <T>Object process(T object) throws IllegalAccessException {
        String className = object.getClass().getName();
        String sheetName = getSheetName(className);
        Map<String, String> map = new HashMap<>();
        Map<String, List<ColumnMetadata>> sheetColumnsMetadataMap = sheetManager.getMappedColumnMetadata();
        List<ColumnMetadata> columnMetadataList = sheetColumnsMetadataMap.get(className);
        String packageName = OsmContextHolder.getContext().getScannedPackageName();
        Class clazz = object.getClass();
        boolean isIdFound = false;
        Object id = null;
        for(Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null != field.get(object) ? field.get(object) : "";
            if (!isIdFound) {
                ColumnMetadata metadata = columnMetadataList.stream()
                        .filter(columnMetadata -> columnMetadata.isIdField())
                        .findFirst()
                        .get();
                if (field.getName().equals(metadata.getName())) {
                    id = value;
                }
                isIdFound = true;
            }
            // TODO: Processing list of nested sheet entities
            if (value.getClass().getName().contains(packageName)) {
                // TODO: Add mapped column metadata checking
                id = process(value);
            }
            for (ColumnMetadata metadata : columnMetadataList) {
                if (field.getName().equals(metadata.getMappedPropertyName())) {
                    if (metadata.getType().getName().contains(packageName)) {
                        map.put(metadata.getName(), id.toString());
                    } else {
                        map.put(metadata.getName(), value.toString());
                    }
                }
            }

        }
        this.exportableMap.put(className, map);
        // TODO: Write to excel
        sheetExporter.writeExcel(sheetName, map, columnMetadataList);
        return id;
    }

    private String getSheetName(String className) {
        try {
            Class clazz = Class.forName(className);
            Annotation annotation = clazz.getAnnotation(SheetEntity.class);
            if (annotation instanceof SheetEntity) {
                SheetEntity sheetEntity = (SheetEntity) annotation;
                return sheetEntity.value();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
