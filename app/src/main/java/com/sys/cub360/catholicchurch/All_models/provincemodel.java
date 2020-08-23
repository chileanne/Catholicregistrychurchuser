package com.sys.cub360.catholicchurch.All_models;

public class provincemodel {
    private String Province;

    public provincemodel(String province) {
        Province = province;

    }

    public String getProvince() {
        return Province;
    }

    @Override
    public String toString() {
        return getProvince();
    }
}
