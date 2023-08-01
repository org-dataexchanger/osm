package org.dataexchanger.osm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dataexchanger.osm.example.BankBranch;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author dipanjal, mainul35
 * @since 7/16/2020
 */
public class ExcelImportTest {
    private SheetExportHandler sheetExportHandler;
    @Before
    public void init() throws IOException, ClassNotFoundException {
        sheetExportHandler = new SheetExportHandler();
    }

    @Test
    public void testExcelImport() throws IllegalAccessException, IOException, InstantiationException {
//        Double val = sheetManagerFactory.readSheet("");
//        System.out.println(val);
//        BankBranch bankBranch = sheetManagerFactory.sheetToObject("Sheet1", BankBranch.class);
//        String resourcePath = "./src/test/resources/";
//        System.out.println(getClass().getResource("/").getPath());
//        File file = new File(resourcePath.concat("BankBranch.xlsx"));
//        List<BankBranch> bankBranchList = sheetImporter.mapSheetToClass(file.getPath(), BankBranch.class);
//        Assert.assertNotNull(bankBranchList.get(0));
    }

    public <T> T readValue(Object i, Class<T> t)  {
        try {
            return (T) t.getDeclaredConstructors()[0].newInstance(i.toString());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
