package org.dataexchanger.osm.sheetexporter;

import org.dataexchanger.osm.enums.MappingStrategyOption;

public class SheetExporterFactory {
    public static <T> T getInstanceForStrategy(MappingStrategyOption mappingStrategyOption) {
        switch (mappingStrategyOption) {
            case AUTO_MAPPING -> {
                return (T) new AutoMappedSheetExporter();
            }
            case RANGE_BASED_MAPPING -> {
                return (T) new RangeBasedSheetExporter();
            }
            default -> throw new IllegalStateException("Unexpected value: " + mappingStrategyOption);
        }
    }
}
