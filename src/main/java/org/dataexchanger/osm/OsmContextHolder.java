package org.dataexchanger.osm;

public final class OsmContextHolder {
    private static OsmContext OSM_CONTEXT;

    public static OsmContext getContext() {
        return OSM_CONTEXT;
    }

    OsmContextHolder(OsmContext osmContext) {
        OSM_CONTEXT = osmContext;
    }
}
