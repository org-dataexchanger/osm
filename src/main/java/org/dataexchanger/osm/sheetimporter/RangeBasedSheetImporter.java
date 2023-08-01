package org.dataexchanger.osm.sheetimporter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dataexchanger.osm.exceptions.InvalidSheetException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class RangeBasedSheetImporter implements SheetImporter {

    public RangeBasedSheetImporter () {
//        try {
//            readProperties();
//            EXPORT_FILE_NAME = properties.getProperty("osm.export.fileName");
//            if(EXPORT_FILE_NAME.endsWith("xlsx")){
//                workbook = new XSSFWorkbook();
//            }else if(EXPORT_FILE_NAME.endsWith("xls")){
//                workbook = new HSSFWorkbook();
//            }else{
//                throw new InvalidSheetException("Invalid file, should be xls or xlsx");
//            }
//            uniqueMap = new HashMap<>();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    @Override public <T> List<T> mapSheetToType(String sourceFilePath, Class<T> tClass) throws IOException {
//        File sourceFile = new File(sourceFilePath);
//        Workbook workbook = WorkbookFactory.create(sourceFile);
//        return this.mapSheetToType(workbook, tClass);
        return null;
    }
}
