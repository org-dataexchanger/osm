package org.dataexchanger.osm;

import org.dataexchanger.osm.enums.MappingStrategyOption;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class SheetExportManagerBeanTest {

    private SheetExportManager sheetExportManagerBean;
    @Before
    public void setup() {
        sheetExportManagerBean = new SheetExportManagerBean(MappingStrategyOption.AUTO_MAPPING);
    }

    @Test
    public void test_scanMappedPackages() throws IOException, ClassNotFoundException {
        sheetExportManagerBean.scanMappedPackages("org.dataexchanger.osm.example");
    }

}
