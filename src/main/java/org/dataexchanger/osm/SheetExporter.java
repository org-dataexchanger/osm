package org.dataexchanger.osm;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dataexchanger.osm.exceptions.InvalidSheetException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class SheetExporter {
    private Properties properties;

    SheetExporter() {
        try {
            readProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readProperties() throws IOException {
        properties = new Properties();
        String propFileName = "osm.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            properties.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
    }

    public void writeExcel(String sheetName, List<Map<String, String>> maps, List<String> columnNames) throws IOException {
        Workbook workbook = null;

        String fileName = properties.getProperty("osm.fileName");
        if(fileName.endsWith("xlsx")){
            workbook = new XSSFWorkbook();
        }else if(fileName.endsWith("xls")){
            workbook = new HSSFWorkbook();
        }else{
            throw new InvalidSheetException("Invalid file, should be xls or xlsx");
        }

        Sheet sheet = workbook.createSheet(sheetName);


        int rowIndex = 0;
        Row row = sheet.createRow(rowIndex++);
        int columnNameCounter = 0;
        for (String columnName : columnNames) {
            Cell cell = row.createCell(columnNameCounter++);
            cell.setCellValue(columnName);
        }

        int columnCount = columnNames.size();
        Iterator<Map<String, String>> iterator = maps.iterator();
        for (Map<String, String> map : maps){
            row = sheet.createRow(rowIndex++);
            int columnCounter = 0;
            while (columnCounter < columnCount) {
                Cell cell = row.createCell(columnCounter);
                cell.setCellValue(map.get(columnNames.get(columnCounter++)));
            }
        }

        //lets write the excel data to file now
        writeWorkbookAsFile(workbook, fileName);
    }

    private void writeWorkbookAsFile(Workbook workbook, String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
    }
}
