package org.dataexchanger.osm;

import java.io.IOException;
import java.io.Serializable;

public interface SheetManager {
    void scanMappedPackages(String... packages) throws IOException, ClassNotFoundException;

    <T> void scanMappedClass(Class<T> aClass);
}
