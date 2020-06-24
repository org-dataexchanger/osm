package org.dataexchanger.osm;

import java.io.Serializable;

public interface SheetManager {
    void scanMappedPackages(String... packages);

    <T> void scanMappedClass(Class<T> aClass);
}
