package org.dataexchanger.osm;

import org.apache.commons.collections4.CollectionUtils;
import org.dataexchanger.osm.example.BankBranch;
import org.junit.Before;
import org.junit.Test;

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
    public void testExcelImport(){
        String resourcePath = "\\src\\main\\resources\\";
        String filePath = System.getProperty("user.dir").concat(resourcePath).concat("BankBranch.xlsx");
        try{
            List<BankBranch> bankBranchList = sheetImporter.mapSheetToClass(filePath, BankBranch.class);
            if(CollectionUtils.isNotEmpty(bankBranchList)){
                System.out.println("success");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
