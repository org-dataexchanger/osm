package org.dataexchanger.osm;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SheetManager {
    void scanMappedPackages(String... packages) throws IOException, ClassNotFoundException;

    Map<String, List<String>> getMappedColumnNames();

    Map<String, Class<?>> getScannedSheetEntities();

    Map<String, List<String>> getMethodNames();
}
