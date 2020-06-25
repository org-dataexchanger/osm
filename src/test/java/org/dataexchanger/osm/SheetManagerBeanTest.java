package org.dataexchanger.osm;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class SheetManagerBeanTest {

    private SheetManager sheetManagerBean;
    @Before
    public void setup() {
        sheetManagerBean = new SheetManagerBean();
    }

    @Test
    public void test_scanMappedPackages() throws IOException, ClassNotFoundException {
        sheetManagerBean.scanMappedPackages("org.dataexchanger.osm.example");
    }

}
