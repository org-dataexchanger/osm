package org.dataexchanger.osm;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.dataexchanger.osm.annotations.Column;
import org.dataexchanger.osm.exceptions.FieldCastException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
                    try{
                        /** dynamic value casting invoking method */
                        Object val = MethodUtils.invokeExactStaticMethod(field.getType(), "valueOf", columnValue);
                        field.set(instance, val);
                    } catch (NoSuchMethodException | ClassCastException e){
                        setTypeSpecificValue(field, instance, columnValue);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void setTypeSpecificValue(Field field, Object instance, String value) throws IllegalAccessException {
        if(!field.isAccessible())
            field.setAccessible(true);
        try{
            if(field.getType() == Integer.class || field.getType() == int.class)
                field.set(instance, Integer.valueOf(String.valueOf(value)));
            else if(field.getType() == Long.class || field.getType() == long.class)
                field.set(instance, Long.valueOf(value));
            else if(field.getType() == Short.class || field.getType() == short.class)
                field.set(instance, Short.valueOf(value));
            else if(field.getType() == Double.class || field.getType() == double.class)
                field.set(instance, Double.valueOf(value));
            else if(field.getType() == Float.class | field.getType() == float.class)
                field.set(instance, Float.valueOf(value));
            else if(field.getType().equals(Byte.class) || field.getType().equals(byte.class))
                field.set(instance, Byte.valueOf(value));
            else if(field.getType() == Character.class || field.getType() == char.class)
                field.set(instance, value.charAt(0));
            else if(field.getType() == Boolean.class || field.getType() == boolean.class)
                field.set(instance, Boolean.valueOf(value));
            else
                field.set(instance, value);
        }catch (IllegalArgumentException ex){
            throw new FieldCastException(field.getName().concat(" ").concat(ex.getMessage()));
        }
    }
}
