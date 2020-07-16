package org.dataexchanger.osm;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.dataexchanger.osm.annotations.Column;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author dipanjal
 * @since 7/16/2020
 */
public class SheetImporter {

    public <T> List<T> mapSheetToClass(String sourceFilePath, Class<T> tClass) throws IllegalAccessException, InstantiationException, IOException {
        File sourceFile = new File(sourceFilePath);
        Workbook workbook = WorkbookFactory.create(sourceFile);
        return this.mapSheetToClass(workbook, tClass);
    }

    public <T> List<T> mapSheetToClass(Workbook workbook, Class<T> tClass) throws IllegalAccessException, InstantiationException {
        List<T> models = new ArrayList<>();
        List<String> columnNames = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();

        workbook.forEach(sheet -> {
            sheet.forEach(row -> {
                int rowNumber = row.getRowNum();
                AtomicReference<Object> newInstanceRef = new AtomicReference<>();
                try{
                    newInstanceRef.set(tClass.newInstance());
                }catch (Exception e){
                    e.printStackTrace();
                }
                row.forEach(cell -> {
                    String columnValue = dataFormatter.formatCellValue(cell);
                    if(rowNumber == 0){
                        columnNames.add(columnValue);
                    }else if(CollectionUtils.isNotEmpty(columnNames)){
                        int columnIndex = cell.getColumnIndex();
                        String columnName = columnNames.get(columnIndex);
                        try{
                            setFieldValue(newInstanceRef.get(), columnName, columnValue);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                if(newInstanceRef.get()!=null && rowNumber >  0){
                    models.add(tClass.cast(newInstanceRef.get()));
                }
            });

        });
        return models;
    }

    private void setFieldValue(Object instance, String columnName, String columnValue) throws IllegalAccessException {
        for(Field field : instance.getClass().getDeclaredFields()){
            field.setAccessible(true);
            if(field.isAnnotationPresent(Column.class)){
                Column annotation = field.getAnnotation(Column.class);
                String colMappingName = StringUtils.isNoneBlank(annotation.name()) ? annotation.name() : field.getName();
                if(columnName.equalsIgnoreCase(colMappingName)){
                    field.set(instance, columnValue);
                }
            }
        }
    }

    /*private Map<String, String> mapColumnAndFieldName(Class clazz) throws IllegalAccessException, InstantiationException {
        Map<String, String> fieldMap = new HashMap<>();
        for(Field field : clazz.getDeclaredFields()){
            if(field.isAnnotationPresent(Column.class)){
                Column annotation = field.getAnnotation(Column.class);
                String columnName = StringUtils.isNoneBlank(annotation.name()) ? annotation.name() : field.getName();
                fieldMap.put(columnName, field.getName());
            }
        }
        return fieldMap;
    }*/
}
