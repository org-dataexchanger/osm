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
    @Column(name = "Lat")
    private Double longitude;
    @Column(name = "Lon")
    private double latitude;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

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
