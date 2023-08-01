package org.dataexchanger.osm.configuration;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dataexchanger.osm.exceptions.InvalidSheetException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class SheetConfiguration {

    private Properties properties;

    private final String PROPERTIES_FILE_NAME = "osm.properties";

    public Properties readProperties() throws IOException {
        properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + PROPERTIES_FILE_NAME + "' not found in the classpath");
        }
        return properties;
    }

    public Workbook getExportWorkbookObject () throws IOException {
        var properties = this.readProperties();
        var EXPORT_FILE_NAME = properties.getProperty("osm.export.fileName");
        if(EXPORT_FILE_NAME.endsWith("xlsx")){
            return new XSSFWorkbook();
        }else if(EXPORT_FILE_NAME.endsWith("xls")){
            return new HSSFWorkbook();
        }else{
            throw new InvalidSheetException("Invalid file, should be xls or xlsx");
        }
    }

    public String getExportFileName() throws IOException {
        var properties = this.readProperties();
        return properties.getProperty("osm.export.fileName");
    }
}
