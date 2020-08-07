package org.dataexchanger.osm;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dataexchanger.osm.exceptions.InvalidSheetException;
import org.dataexchanger.osm.model.ColumnMetadata;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public final class SheetExporter {
    private Properties properties;
    protected Workbook workbook = null;
    private final String PROPERTIES_FILE_NAME = "osm.properties";
    protected String EXPORT_FILE_NAME;
    private Map<String, Set<String>> uniqueMap = null;

    SheetExporter() {
        try {
            readProperties();
             EXPORT_FILE_NAME = properties.getProperty("osm.fileName");
            if(EXPORT_FILE_NAME.endsWith("xlsx")){
                workbook = new XSSFWorkbook();
            }else if(EXPORT_FILE_NAME.endsWith("xls")){
                workbook = new HSSFWorkbook();
            }else{
                throw new InvalidSheetException("Invalid file, should be xls or xlsx");
            }
            uniqueMap = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readProperties() throws IOException {
        properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + PROPERTIES_FILE_NAME + "' not found in the classpath");
        }
    }


    public void writeExcel(String sheetName, String idFieldName, Map<String, String> map, List<ColumnMetadata> columnMetadataList) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) sheet = workbook.createSheet(sheetName);

        Set<String> set = null;
        if (uniqueMap.get(sheetName) != null) {
            set = uniqueMap.get(sheetName);
        } else {
            set = new HashSet<>();
            uniqueMap.put(sheetName, set);
        }

        int rowIndex = 0;
        Row row = null;

        // If any row exists, get the last row number and update the sheet
        // Otherwise create the column names row
        if (sheet.getLastRowNum() > 0) {
            rowIndex = sheet.getPhysicalNumberOfRows();
        } else {
            row = sheet.createRow(rowIndex++);
            int columnNameCounter = 0;
            for (ColumnMetadata columnMetadata : columnMetadataList) {
                Cell cell = row.createCell(columnNameCounter++);
                cell.setCellValue(columnMetadata.getName());
            }
        }

        String idFieldValue = map.get(idFieldName).toString();
        if (!set.contains(idFieldValue)) {
            int columnCount = columnMetadataList.size();
            row = sheet.createRow(rowIndex++);
            int columnCounter = 0;
            while (columnCounter < columnCount) {
                Cell cell = row.createCell(columnCounter);
                cell.setCellValue(map.get(columnMetadataList.get(columnCounter++).getName()));
            }
            set.add(idFieldValue);
        }
    }
}
