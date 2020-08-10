package org.dataexchanger.osm;

import org.dataexchanger.osm.model.ColumnMetadata;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SheetManager {
    void scanMappedPackages(String... packages) throws IOException, ClassNotFoundException;

    Map<String, List<ColumnMetadata>> getMappedColumnMetadata();
}
