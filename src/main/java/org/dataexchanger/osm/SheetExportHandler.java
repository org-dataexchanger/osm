package org.dataexchanger.osm;

import org.dataexchanger.osm.annotations.SheetEntity;
import org.dataexchanger.osm.configuration.SheetConfiguration;
import org.dataexchanger.osm.enums.MappingStrategyOption;
import org.dataexchanger.osm.model.ColumnMetadata;
import org.dataexchanger.osm.sheetexporter.SheetExporter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SheetExportHandler {

    private SheetExportManager sheetExportManager;
    private Map<String, Map<String,String>> exportableMap;
    private SheetExporter sheetExporter;

    SheetExportHandler() {
        this.exportableMap = new HashMap<>();
    }

    public void setExportStrategy (MappingStrategyOption exportStrategy) {
        this.sheetExportManager = new SheetExportManagerBean(exportStrategy);
        this.sheetExporter = this.sheetExportManager.getSheetExporter();
    }

    public void scanMappedPackage (String mappedPackageName) throws IOException, ClassNotFoundException {
        this.sheetExportManager.scanMappedPackages(mappedPackageName);
    }

    public <T> void prepareWorkbook(T object) throws IllegalAccessException, IOException {
        process(object);
    }

    /**
     * This method is responsible for writing workbook in filesystem.
     *
     * */
    public void writeWorkbookAsFile() throws IOException {
        SheetConfiguration sheetConfiguration = new SheetConfiguration();
        var exportFileName = sheetConfiguration.getExportFileName();
        FileOutputStream fos = new FileOutputStream(exportFileName);
        sheetExporter.getWorkbook().write(fos);
        fos.close();
    }

    /**
     * @return byte[]
     * This method is responsible for getting byte array
     * This will be helpful to send the file over the network
     * */
    public byte[] getByteContent() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        sheetExporter.getWorkbook().write(baos);
        return baos.toByteArray();
    }

    private <T>Object process(T object) throws IllegalAccessException {
        String className = object.getClass().getName();
        String sheetName = getSheetName(className);
        Map<String, String> map = new HashMap<>();
        Map<String, List<ColumnMetadata>> sheetColumnsMetadataMap = sheetExportManager.getMappedColumnMetadata();
        List<ColumnMetadata> columnMetadataList = sheetColumnsMetadataMap.get(className);
        String packageName = OsmContextHolder.getContext().getScannedPackageName();
        Class clazz = object.getClass();
        boolean isIdFound = false;
        String idFieldName = "";
        Object id = null;
        for(Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = null != field.get(object) ? field.get(object) : "";
            if (!isIdFound) {
                ColumnMetadata metadata = columnMetadataList.stream()
                        .filter(ColumnMetadata::isIdField)
                        .findFirst()
                        .get();
                if (field.getName().equals(metadata.getName())) {
                    id = value;
                }
                idFieldName = field.getName();
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
        // TODO: is exportableMap really important?
        this.exportableMap.put(className, map);
        // TODO: Write to excel
        sheetExporter.writeExcel(sheetName, idFieldName, map, columnMetadataList);
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
