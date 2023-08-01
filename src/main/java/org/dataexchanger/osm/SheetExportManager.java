package org.dataexchanger.osm;

import org.dataexchanger.osm.model.ColumnMetadata;
import org.dataexchanger.osm.sheetexporter.SheetExporter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SheetExportManager {
    void scanMappedPackages(String packages) throws IOException, ClassNotFoundException;

    Map<String, List<ColumnMetadata>> getMappedColumnMetadata();

    SheetExporter getSheetExporter();
}
