package org.dataexchanger.osm;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelExporterTest {

    private SheetExporter sheetExporter;
    @Before
    public void setup() {
        sheetExporter = new SheetExporter();

    }

    @Test
    public void test_writeToExcel() throws IOException {
        List<String> columnNames = new ArrayList<>();
        columnNames.add("name");
        columnNames.add("age");
        columnNames.add("address_id");

        List<Map<String, String>> valueMap = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("name", "Mainul");
        map.put("age", "26");
        map.put("address_id", "1");
        valueMap.add(map);
//        System.out.println(columnNames.toString());
//        sheetExporter.writeExcel("employee", valueMap, columnNames);
    }
}
