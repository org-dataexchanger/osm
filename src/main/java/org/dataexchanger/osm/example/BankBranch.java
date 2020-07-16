package org.dataexchanger.osm.example;

import org.dataexchanger.osm.annotations.Column;
import org.dataexchanger.osm.annotations.SheetEntity;

import java.io.Serializable;

/**
 * @author dipanjal
 * @since 7/16/2020
 */
@SheetEntity(value = "BankBranch")
public class BankBranch implements Serializable {
    @Column(name = "Branch Name")
    private String branchName;
    @Column(name = "Branch Code")
    private String branchCode;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
}
