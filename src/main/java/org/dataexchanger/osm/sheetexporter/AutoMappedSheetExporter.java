package org.dataexchanger.osm.sheetexporter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dataexchanger.osm.configuration.SheetConfiguration;
import org.dataexchanger.osm.exceptions.InvalidSheetException;
import org.dataexchanger.osm.model.ColumnMetadata;

import java.io.IOException;
import java.util.*;

public class AutoMappedSheetExporter implements SheetExporter {

    protected Workbook workbook = null;


    private Map<String, Set<String>> uniqueMap = null;

    AutoMappedSheetExporter() {
        SheetConfiguration sheetConfiguration = new SheetConfiguration();
        try {
            workbook = sheetConfiguration.getExportWorkbookObject();
            uniqueMap = new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException("Value is empty for osm.export.fileName in osm.properties");
        }
    }

    public Workbook getWorkbook() {
        return workbook;
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

        String idFieldValue = map.get(idFieldName);
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
