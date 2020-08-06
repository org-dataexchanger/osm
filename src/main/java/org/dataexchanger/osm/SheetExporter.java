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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SheetExporter {
    private Properties properties;
    private Workbook workbook = null;
    private final String PROPERTIES_FILE_NAME = "osm.properties";
    private String EXPORT_FILE_NAME;

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


    public void writeExcel(String sheetName, Map<String, String> map, List<ColumnMetadata> columnMetadataList) {
        Sheet sheet = workbook.createSheet(sheetName);

        int rowIndex = 0;
        Row row = sheet.createRow(rowIndex++);
        int columnNameCounter = 0;
        for (ColumnMetadata columnMetadata : columnMetadataList) {
            Cell cell = row.createCell(columnNameCounter++);
            cell.setCellValue(columnMetadata.getName());
        }

        int columnCount = columnMetadataList.size();
        row = sheet.createRow(rowIndex++);
        int columnCounter = 0;
        while (columnCounter < columnCount) {
            Cell cell = row.createCell(columnCounter);
            cell.setCellValue(map.get(columnMetadataList.get(columnCounter++).getName()));
        }
    }

/*    public Workbook getWorkbook() {
        return workbook;
    }*/

    public void writeWorkbookAsFile() throws IOException {
        FileOutputStream fos = new FileOutputStream(EXPORT_FILE_NAME);
        workbook.write(fos);
        fos.close();
    }
}
