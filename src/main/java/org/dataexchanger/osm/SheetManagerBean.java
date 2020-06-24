package org.dataexchanger.osm;

import java.util.HashMap;
import java.util.Map;

public class SheetManagerBean implements SheetManager {

    private static final Map<String, String[]> mappedColumnNames;

    static {
        mappedColumnNames = new HashMap<String, String[]>();
    }

    @Override
    public void scanMappedPackages(String... packages) {

    }

    @Override
    public <T> void scanMappedClass(Class<T> aClass) {

    }
}
