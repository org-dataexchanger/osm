package org.dataexchanger.osm;

public interface OsmContext {

    default String getScannedPackageName() {
        return "";
    }

    SheetManager getSheetManager();
}
