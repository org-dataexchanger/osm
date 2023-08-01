package org.dataexchanger.osm.sheetexporter;

import org.apache.poi.ss.usermodel.Workbook;
import org.dataexchanger.osm.model.ColumnMetadata;

import java.util.List;
import java.util.Map;

public interface SheetExporter {

    Workbook getWorkbook();
    void writeExcel(String sheetName, String idFieldName, Map<String, String> map, List<ColumnMetadata> columnMetadataList);
}
