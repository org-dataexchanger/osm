package org.dataexchanger.osm;

import org.apache.commons.collections4.CollectionUtils;
import org.dataexchanger.osm.example.BankBranch;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author dipanjal
 * @since 7/16/2020
 */
public class ExcelImportTest {
    private SheetImporter sheetImporter;
    @Before
    public void init(){
        this.sheetImporter = new SheetImporter();
    }

    @Test
    public void testExcelImport() throws IllegalAccessException, IOException, InstantiationException {
        String resourcePath = "./src/test/resources/";
        System.out.println(getClass().getResource("/").getPath());
        File file = new File(resourcePath.concat("BankBranch.xlsx"));
        List<BankBranch> bankBranchList = sheetImporter.mapSheetToClass(file.getPath(), BankBranch.class);
        Assert.assertNotNull(bankBranchList.get(0));
    }
}
