package org.dataexchanger.osm.sheetimporter;

import org.apache.poi.ss.usermodel.Workbook;
import org.dataexchanger.osm.exceptions.FieldCastException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public interface SheetImporter {

    <T> List<T> mapSheetToType(String sourceFilePath, Class<T> tClass) throws IOException;
    default void setTypeSpecificValue(Field field, Object instance, String value) throws IllegalAccessException {
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
